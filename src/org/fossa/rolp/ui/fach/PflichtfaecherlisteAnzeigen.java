/**
 * Copyright (c) 2013, 2014 Frank Kaddereit, Anne Lachnitt, http://www.fossa.de/
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */

package org.fossa.rolp.ui.fach;

import java.util.Date;

import org.fossa.rolp.RolpApplication;
import org.fossa.rolp.data.fach.FachContainer;
import org.fossa.rolp.data.fach.FachLaso;
import org.fossa.rolp.data.fach.fachtyp.FachtypPojoContainer;
import org.fossa.rolp.data.klasse.KlasseContainer;
import org.fossa.rolp.data.lehrer.LehrerBlogContainer;
import org.fossa.rolp.data.lehrer.LehrerBlogLaso;
import org.fossa.rolp.data.lehrer.LehrerPojo;
import org.fossa.rolp.data.schueler.SchuelerContainer;
import org.fossa.rolp.data.schueler.SchuelerLaso;
import org.fossa.rolp.data.schueler.SchuelerPojo;
import org.fossa.rolp.data.zuordnung.fachschueler.ZuordnungFachSchuelerContainer;
import org.fossa.rolp.data.zuordnung.fachschueler.ZuordnungFachSchuelerHandler;
import org.fossa.rolp.data.zuordnung.fachschueler.ZuordnungFachSchuelerLaso;
import org.fossa.rolp.ui.zuordnung.fachschueler.FachSchuelerZuordnen;
import org.fossa.rolp.ui.zuordnung.fachschueler.FachSchuelerZuordnenList;
import org.fossa.vaadin.ui.FossaBooleanDialog;
import org.fossa.vaadin.ui.FossaWindow;
import org.fossa.vaadin.ui.exception.FossaLasoLockedException;

import com.vaadin.data.Property;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.event.ItemClickEvent.ItemClickListener;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CustomLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.Window.CloseListener;

public class PflichtfaecherlisteAnzeigen extends FossaWindow implements Button.ClickListener, ItemClickListener, CloseListener {

	private static final long serialVersionUID = -6514418795515349474L;
	
	private Button windowCloseButton = new Button ("Schließen", (ClickListener) this);
	private Button faecherHinzufuegenButton = new Button ("Hinzufügen", (ClickListener) this);
	private Button faecherEntfernenButton = new Button ("Entfernen", (ClickListener) this);
	private Button faecherBearbeitenButton = new Button("Bearbeiten", (ClickListener) this);
	private Button schuelerZuordnenButton = new Button("Schüler zuordnen", (ClickListener) this);
	private RolpApplication app;
	private PflichtfaecherList faecherList;
	private FaecherAnlegen faecherAnlegen;
	private FachSchuelerZuordnen schuelerZuordnen;

	private FossaBooleanDialog confirmDeleteFach;	

	public PflichtfaecherlisteAnzeigen(RolpApplication app) {
		super(app);
		this.app = app;
		setCaption(" - Ansicht der Pflichtfächer - ");
		setWidth("800px");
		center();
		
		VerticalLayout layoutVertical = new VerticalLayout();
		setContent(layoutVertical);
		
		CustomLayout layout = new CustomLayout("./listeAnzeigen/listeAnzeigen");
		HorizontalLayout buttonBattery = new HorizontalLayout();
		buttonBattery.setSpacing(true);
		HorizontalLayout buttonBatteryBearbeiten = new HorizontalLayout();
		buttonBatteryBearbeiten.setSpacing(true);
		
		
		buttonBatteryBearbeiten.addComponent(faecherHinzufuegenButton);
		faecherHinzufuegenButton.setWidth("150px");
		buttonBatteryBearbeiten.addComponent(faecherBearbeitenButton);
		faecherBearbeitenButton.setWidth("150px");
		buttonBatteryBearbeiten.addComponent(faecherEntfernenButton);
		faecherEntfernenButton.setWidth("150px");
		
		buttonBattery.addComponent(schuelerZuordnenButton);
		schuelerZuordnenButton.setWidth("250px");
		schuelerZuordnenButton.setEnabled(SchuelerContainer.getAllSchuelerOfKlasse(KlasseContainer.getKlasseByLehrer(app.getLoginLehrer()).getPojo()).size() > 0);

		layout.addComponent(buttonBatteryBearbeiten, "buttonBatteryBearbeiten");
		layout.addComponent(buttonBattery, "buttonBattery");

		layout.addComponent(windowCloseButton, "windowCloseButton");
		windowCloseButton.setWidth("100%");
		
		faecherList = getFaecherList();
		
		layoutVertical.addComponent(faecherList);
		layoutVertical.addComponent(layout);
	}

	@Override
	public void buttonClick(ClickEvent event) {
		final Button source = event.getButton();
		refreshPage();
		if (source == windowCloseButton) {
			close();
		}
		else if (source == faecherHinzufuegenButton){
			openSubwindow(getFaecherAnlegen(null));
		}
		else if (source == faecherBearbeitenButton) {
			FachLaso fach = (FachLaso) faecherList.getValue();
			if (fach == null) {
				app.getMainWindow().showNotification("kein Fach ausgewählt");
				return;
			}
			faecherBearbeiten(fach);
		} else if (source == faecherEntfernenButton) {
			FachLaso fach = (FachLaso) faecherList.getValue();
			if (fach != null) {
				if (dependingLasoIsLocked(fach)) {
					getWindow().showNotification("LOCKED");
					return;
				}
				confirmDeleteFach = new FossaBooleanDialog(app, " - Bestätigung - ", "Möchten Sie das Fach '" + fach.getFachbezeichnung() + "' wirklich entgültig entfernen?", "Ja", "Nein");
				confirmDeleteFach.addListener((CloseListener) this); 
				getApplication().getMainWindow().addWindow(confirmDeleteFach);
			} 
		} else if (source == schuelerZuordnenButton) {
			FachLaso fach = (FachLaso) faecherList.getValue();
			if (fach == null) {
				app.getMainWindow().showNotification("kein Fach ausgewählt");
				return;
			}
			try {
				openSubwindow(getSchuelerZuordnen(fach));
			} catch (FossaLasoLockedException e) {
				getWindow().showNotification("LOCKED");
				return;
			}
		}
	}
	
	private boolean dependingLasoIsLocked(FachLaso fach) {
		if (fach.isLocked()) {
			return true;
		}
		for (SchuelerLaso schueler : ZuordnungFachSchuelerContainer.getAllSchuelerLasoOfFach(fach.getPojo()).getItemIds()) {
			if (schueler.isLocked()) {
				return true;
			}
			ZuordnungFachSchuelerLaso zuordnungFS = ZuordnungFachSchuelerContainer.getZuordnung(schueler.getPojo(), fach.getPojo());
			if (zuordnungFS.isLocked()) {
				return true;
			}
			if (zuordnungFS.getFacheinschaetzung() != null && zuordnungFS.getFacheinschaetzung().isLocked()) {
				return true;
			}
		}
		return false;
	}
	
	private FachSchuelerZuordnen getSchuelerZuordnen(FachLaso fach) throws FossaLasoLockedException {
		schuelerZuordnen = new FachSchuelerZuordnen(app, getFachSchuelerZuordnenList(fach), "Schüler zum Fach hinzufügen");
		return schuelerZuordnen;
	}
	
	private FachSchuelerZuordnenList getFachSchuelerZuordnenList(FachLaso kurs) throws FossaLasoLockedException {
		return new FachSchuelerZuordnenList(app, kurs);
	}

	private FaecherAnlegen getFaecherAnlegen(FachLaso fach) {
		if (fach == null) {
			fach = new FachLaso();
		}
		faecherAnlegen = new FaecherAnlegen(app, fach, FachtypPojoContainer.getInstance().getPflichtfach());
		return faecherAnlegen;
	}
	
	private PflichtfaecherList getFaecherList() {
		if (faecherList == null) {
			faecherList = new PflichtfaecherList(app, this);
		}
		return faecherList;
	}
	
	@Override
	public void itemClick(ItemClickEvent event) {
		Property property = (Property) event.getSource();
		if (property == faecherList && event.isDoubleClick()) {
			FachLaso fach = (FachLaso) event.getItemId();
			faecherBearbeiten(fach);
		}
	}
	
	private void faecherBearbeiten(FachLaso fach) {
		for (FachLaso aFach : FachContainer.getInstance().getItemIds()) {
			if (fach.getId().equals(aFach.getId())) {
				faecherAnlegen = getFaecherAnlegen(aFach);
				openSubwindow(faecherAnlegen);
			}
		}	
	}


	protected void openSubwindow(FossaWindow window) {
		getApplication().getMainWindow().addWindow(window);
	}
	
	@Override
	public void unlockLaso() {
	}
	
	public void refreshPage() {
		getFaecherList().refresh();
		getFaecherList().requestRepaintAll();
		getFaecherList().refreshRowCache();	
	}
	
	@Override
	public void windowClose(CloseEvent event) {
		Window source = event.getWindow();
		if (source == confirmDeleteFach) {
			if (confirmDeleteFach.getDecision()) {
				FachLaso fach = (FachLaso) faecherList.getValue();
				if (dependingLasoIsLocked(fach)) {
					getWindow().showNotification("LOCKED");
					return;
				}
				for (SchuelerPojo schueler: ZuordnungFachSchuelerContainer.getAllSchuelerOfFach(fach.getPojo()).getItemIds()) {
					try {
						new ZuordnungFachSchuelerHandler(schueler, fach.getPojo(), true, this).setZugeordnet(false);
					} catch (Exception e) {						
						return;
					}
				}
				LehrerBlogContainer lehrerBlogContainer = LehrerBlogContainer.getInstance();
				LehrerPojo fachlehrer1 = fach.getFachlehrerEins();
				if (fachlehrer1 != null) {
					LehrerBlogLaso lehrerblog = new LehrerBlogLaso();
					lehrerblog.setLehrer(fachlehrer1);
					lehrerblog.setTimestamp(new Date());
					lehrerblog.setEreignis("Das von Ihnen unterrichtete Fach '" + fach.getFachbezeichnung() + "' wurde gelöscht.");
					lehrerBlogContainer.addBean(lehrerblog);
				}
				LehrerPojo fachlehrer2 = fach.getFachlehrerZwei();
				if (fachlehrer2 != null) {
					LehrerBlogLaso lehrerblog = new LehrerBlogLaso();
					lehrerblog.setLehrer(fachlehrer2);
					lehrerblog.setTimestamp(new Date());
					lehrerblog.setEreignis("Das von Ihnen unterrichtete Fach '" + fach.getFachbezeichnung() + "' wurde gelöscht.");
					lehrerBlogContainer.addBean(lehrerblog);
				}
				FachContainer.getInstance().deleteFach(fach);
				refreshPage();
			}
		}
	}

}

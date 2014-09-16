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

public class KurseZuordnen extends FossaWindow implements Button.ClickListener, ItemClickListener, CloseListener {

	private static final long serialVersionUID = -8644281114832548496L;
	
	private Button windowCloseButton = new Button ("Schlie�en", (ClickListener) this);
	private Button kursHinzufuegenButton = new Button ("Hinzuf�gen", (ClickListener) this);
	private Button kursEntfernenButton = new Button ("Entfernen", (ClickListener) this);
	private Button kursBearbeitenButton = new Button("Bearbeiten", (ClickListener) this);
	private Button schuelerZuordnenButton = new Button("Sch�ler zuordnen", (ClickListener) this);
	public RolpApplication app;
	private KursList faecherList;
	private FaecherAnlegen faecherAnlegen;
	private FachSchuelerZuordnen schuelerZuordnen;

	private FossaBooleanDialog confirmDeleteFach;

	public KurseZuordnen(RolpApplication app) {
		super(app);
		this.app = app;
		setCaption(" - Zuordnung der Kurse - ");
		setWidth("950px");
		center();

		VerticalLayout layoutVertical = new VerticalLayout();
		setContent(layoutVertical);

		CustomLayout layout = new CustomLayout("./listeAnzeigen/listeAnzeigen");
		HorizontalLayout buttonBattery = new HorizontalLayout();
		buttonBattery.setSpacing(true);
		HorizontalLayout buttonBatteryBearbeiten = new HorizontalLayout();
		buttonBatteryBearbeiten.setSpacing(true);

		buttonBatteryBearbeiten.addComponent(kursHinzufuegenButton);
		kursHinzufuegenButton.setWidth("150px");
		buttonBatteryBearbeiten.addComponent(kursBearbeitenButton);
		kursBearbeitenButton.setWidth("150px");
		buttonBatteryBearbeiten.addComponent(kursEntfernenButton);
		kursEntfernenButton.setWidth("150px");

		buttonBattery.addComponent(schuelerZuordnenButton);
		schuelerZuordnenButton.setWidth("250px");
		schuelerZuordnenButton.setEnabled(SchuelerContainer.getAllSchuelerOfKlasse(KlasseContainer.getKlasseByLehrer(app.getLoginLehrer()).getPojo()).size() > 0);

		layout.addComponent(buttonBatteryBearbeiten, "buttonBatteryBearbeiten");
		layout.addComponent(buttonBattery, "buttonBattery");

		layout.addComponent(windowCloseButton, "windowCloseButton");
		windowCloseButton.setWidth("100%");

		faecherList = getKursList();

		layoutVertical.addComponent(faecherList);
		layoutVertical.addComponent(layout);
	}
	
	private KursList getKursList() {
		if (faecherList == null) {
			faecherList = new KursList(this);
		}
		return faecherList;
	}
	
	private FaecherAnlegen getFaecherAnlegen(FachLaso fach) {
		if (fach == null) {
			fach = new FachLaso();
		}
		faecherAnlegen = new FaecherAnlegen(app, fach, FachtypPojoContainer.getInstance().getKurs());
		return faecherAnlegen;
	}	
	
	private FachSchuelerZuordnen getSchuelerZuordnen(FachLaso fach) throws FossaLasoLockedException {
		schuelerZuordnen = new FachSchuelerZuordnen(app, getFachSchuelerZuordnenList(fach), "Sch�ler zum Kurs hinzuf�gen");
		return schuelerZuordnen;
	}
	
	private FachSchuelerZuordnenList getFachSchuelerZuordnenList(FachLaso kurs) throws FossaLasoLockedException {
		return new FachSchuelerZuordnenList(app, kurs);
	}
	
	private void faecherBearbeiten(FachLaso fach) {
		for (FachLaso aFach : FachContainer.getInstance().getItemIds()) {
			if (fach.getId().equals(aFach.getId())) {
				faecherAnlegen = getFaecherAnlegen(aFach);
				openSubwindow(faecherAnlegen);
			}
		}	
	}
	
	@Override
	public void unlockLaso() {
	}
	
	public void refreshPage() {
		getKursList().refresh();
		getKursList().requestRepaintAll();
		getKursList().refreshRowCache();	
	}

	@Override
	public void itemClick(ItemClickEvent event) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void buttonClick(ClickEvent event) {
		final Button source = event.getButton();
		refreshPage();
		if (source == windowCloseButton) {
			close();
		}
		else if (source == kursHinzufuegenButton){
			openSubwindow(getFaecherAnlegen(null));
		}
		else if (source == kursBearbeitenButton) {
			FachLaso fach = (FachLaso) faecherList.getValue();
			if (fach == null) {
				app.getMainWindow().showNotification("kein Fach ausgew�hlt");
				return;
			}
			faecherBearbeiten(fach);
		} else if (source == schuelerZuordnenButton) {
			FachLaso fach = (FachLaso) faecherList.getValue();
			if (fach == null) {
				app.getMainWindow().showNotification("kein Fach ausgew�hlt");
				return;
			}
			try {
				openSubwindow(getSchuelerZuordnen(fach));
			} catch (FossaLasoLockedException e) {
				getWindow().showNotification("LOCKED");
				return;
			}
		} else if (source == kursEntfernenButton) {
			FachLaso fach = (FachLaso) faecherList.getValue();
			if (fach != null) {
				if (ZuordnungFachSchuelerContainer.getAllSchuelerOfFach(fach.getPojo()).size() > 0) {
					getWindow().showNotification("Der Kurs kann nicht gel�scht werden, da ihm bereits Sch�ler zugeordnet sind.");
					return;
				}
				if (dependingLasoIsLocked(fach)) {
					getWindow().showNotification("LOCKED");
					return;
				}
				confirmDeleteFach = new FossaBooleanDialog(app, " - Best�tigung - ", "M�chten Sie den Kurs '" + fach.getFachbezeichnung() + "' wirklich entg�ltig entfernen?", "Ja", "Nein");
				confirmDeleteFach.addListener((CloseListener) this); 
				getApplication().getMainWindow().addWindow(confirmDeleteFach);
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

	protected void openSubwindow(FossaWindow window) {
		getApplication().getMainWindow().addWindow(window);
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
					lehrerblog.setEreignis("Der von Ihnen unterrichtete Kurs '" + fach.getFachbezeichnung() + "' wurde gel�scht.");
					lehrerBlogContainer.addBean(lehrerblog);
				}
				LehrerPojo fachlehrer2 = fach.getFachlehrerZwei();
				if (fachlehrer2 != null) {
					LehrerBlogLaso lehrerblog = new LehrerBlogLaso();
					lehrerblog.setLehrer(fachlehrer2);
					lehrerblog.setTimestamp(new Date());
					lehrerblog.setEreignis("Der von Ihnen unterrichtete Kurs '" + fach.getFachbezeichnung() + "' wurde gel�scht.");
					lehrerBlogContainer.addBean(lehrerblog);
				}
				FachContainer.getInstance().deleteFach(fach);
				refreshPage();
			}
		}
	}

}

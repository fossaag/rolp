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

package org.fossa.rolp.ui.schueler;

import java.util.Date;

import org.fossa.rolp.RolpApplication;
import org.fossa.rolp.data.fach.FachLaso;
import org.fossa.rolp.data.fach.FachPojo;
import org.fossa.rolp.data.klasse.KlasseContainer;
import org.fossa.rolp.data.klasse.KlasseLaso;
import org.fossa.rolp.data.lehrer.LehrerBlogContainer;
import org.fossa.rolp.data.lehrer.LehrerBlogLaso;
import org.fossa.rolp.data.lehrer.LehrerPojo;
import org.fossa.rolp.data.schueler.SchuelerContainer;
import org.fossa.rolp.data.schueler.SchuelerLaso;
import org.fossa.rolp.data.zuordnung.fachschueler.ZuordnungFachSchuelerContainer;
import org.fossa.rolp.data.zuordnung.fachschueler.ZuordnungFachSchuelerHandler;
import org.fossa.rolp.data.zuordnung.fachschueler.ZuordnungFachSchuelerLaso;
import org.fossa.vaadin.ui.FossaBooleanDialog;
import org.fossa.vaadin.ui.FossaWindow;

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

public class SchuelerlisteAnzeigen extends FossaWindow implements Button.ClickListener, ItemClickListener, CloseListener {

	private static final long serialVersionUID = -6514418795515349474L;
	
	private Button windowCloseButton = new Button ("Schließen", (ClickListener) this);
	private Button schuelerHinzufuegenButton = new Button ("Hinzufügen", (ClickListener) this);
	private Button schuelerEntfernenButton = new Button ("Entfernen", (ClickListener) this);
	private Button schuelerBearbeitenButton = new Button("Bearbeiten", (ClickListener) this);
	private RolpApplication app;
	private SchuelerList schuelerList = null;
	private SchuelerAnlegen schuelerAnlegen;
	private KlasseLaso klasseLaso;

	private FossaBooleanDialog confirmDeleteSchueler;

	
	public SchuelerlisteAnzeigen(RolpApplication app) {
		super(app);
		this.app = app;
		this.klasseLaso = KlasseContainer.getKlasseByLehrer(app.getLoginLehrer());

		setCaption(" - Anzeige der Schülerliste - ");
		setWidth("800px");
		center();
		
		VerticalLayout layoutVertical = new VerticalLayout();
		setContent(layoutVertical);
		
		CustomLayout layout = new CustomLayout("./listeAnzeigen/listeAnzeigen");
		HorizontalLayout buttonBattery = new HorizontalLayout();
		buttonBattery.setSpacing(true);
		HorizontalLayout buttonBatteryBearbeiten = new HorizontalLayout();
		buttonBatteryBearbeiten.setSpacing(true);
		
		buttonBatteryBearbeiten.addComponent(schuelerHinzufuegenButton);
		schuelerHinzufuegenButton.setWidth("150px");
		buttonBatteryBearbeiten.addComponent(schuelerBearbeitenButton);
		schuelerBearbeitenButton.setWidth("150px");
		buttonBatteryBearbeiten.addComponent(schuelerEntfernenButton);
		schuelerEntfernenButton.setWidth("150px");

		layout.addComponent(buttonBatteryBearbeiten, "buttonBatteryBearbeiten");
		layout.addComponent(buttonBattery, "buttonBattery");

		layout.addComponent(windowCloseButton, "windowCloseButton");
		windowCloseButton.setWidth("100%");
		
		schuelerList = getSchuelerList();
		schuelerList.addStyleName("schuelerList");
		
		layoutVertical.addComponent(schuelerList);
		layoutVertical.addComponent(layout);
	}

	@Override
	public void buttonClick(ClickEvent event) {
		final Button source = event.getButton();
		refreshPage();
		if (source == windowCloseButton) {
			close();
		}
		else if (source == schuelerHinzufuegenButton){
			openSubwindow(getSchuelerAnlegen(null));
		}
		else if (source == schuelerBearbeitenButton){
			SchuelerLaso schueler = (SchuelerLaso) schuelerList.getValue();
			if (schueler == null) {
				app.getMainWindow().showNotification("kein Schüler ausgewählt");
				return;
			}
			schuelerBearbeiten(schueler);
		} else if (source == schuelerEntfernenButton) {
			SchuelerLaso schueler = (SchuelerLaso) schuelerList.getValue();
			if (schueler != null) {
				if (dependingLasoIsLocked(schueler)) {
					getWindow().showNotification("LOCKED");
					return;
				}
				for (FachPojo fach : ZuordnungFachSchuelerContainer.getAllFaecherOfSchueler(schueler.getPojo()).getItemIds()) {
					if (fach.getFachtyp().isPflichtfach()) {
						if (ZuordnungFachSchuelerContainer.getAllSchuelerOfFach(fach).size() == 1) {
							getWindow().showNotification(schueler.getVorname() + " ist der letzte Schüler im Fach '" + fach.getFachbezeichnung() + "' und kann daher nicht gelöscht werden.");
							return;
						}
					}
				}
				confirmDeleteSchueler = new FossaBooleanDialog(app, " - Bestätigung - ", "Möchten Sie den Schüler '" + schueler.getVorname() + " " + schueler.getName() + "' wirklich entgültig entfernen?", "Ja", "Nein");
				confirmDeleteSchueler.addListener((CloseListener) this); 
				getApplication().getMainWindow().addWindow(confirmDeleteSchueler);
			} 
		}
	}

	private boolean dependingLasoIsLocked(SchuelerLaso schueler) {
		if (schueler.isLocked()) {
			return true;
		}
		for (FachLaso fach : ZuordnungFachSchuelerContainer.getAllFachLasoOfSchueler(schueler.getPojo()).getItemIds()) {
			if (fach.isLocked()) {
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

	private SchuelerAnlegen getSchuelerAnlegen(SchuelerLaso schueler) {
		if (schueler == null) {
			schueler = new SchuelerLaso(klasseLaso.getPojo());	
		}
		schuelerAnlegen = new SchuelerAnlegen(app, schueler);
		return schuelerAnlegen;
	}
	
	private SchuelerList getSchuelerList() {
		if (schuelerList == null) {
			schuelerList = new SchuelerList(app, this, SchuelerContainer.NATURAL_COL_ORDER, SchuelerContainer.COL_HEADERS);
		}
		return schuelerList;
	}
	

	@Override
	public void itemClick(ItemClickEvent event) {
		Property property = (Property) event.getSource();
		if (property == schuelerList && event.isDoubleClick()) {
			SchuelerLaso schueler = (SchuelerLaso) event.getItemId();
			schuelerBearbeiten(schueler);
		}
	}
	
	private void schuelerBearbeiten(SchuelerLaso schueler) {
		for (SchuelerLaso aSchueler : SchuelerContainer.getInstance().getItemIds()) {
			if (schueler.getId().equals(aSchueler.getId())) {
				schuelerAnlegen = getSchuelerAnlegen(aSchueler);
				openSubwindow(schuelerAnlegen);
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
		getSchuelerList().refresh(klasseLaso.getPojo());
		getSchuelerList().requestRepaintAll();
		getSchuelerList().refreshRowCache();		
	}
	
	@Override
	public void windowClose(CloseEvent event) {
		Window source = event.getWindow();
		if (source == confirmDeleteSchueler) {
			if (confirmDeleteSchueler.getDecision()) {
				SchuelerLaso schueler = (SchuelerLaso) schuelerList.getValue();
				if (dependingLasoIsLocked(schueler)) {
					getWindow().showNotification("LOCKED");
					return;
				}
				for (FachPojo fach: ZuordnungFachSchuelerContainer.getAllFaecherOfSchueler(schueler.getPojo()).getItemIds()) {
					try {
						new ZuordnungFachSchuelerHandler(schueler.getPojo(), fach, true, this).setZugeordnet(false);
					} catch (Exception e) {						
						return;
					}
					LehrerBlogContainer lehrerBlogContainer = LehrerBlogContainer.getInstance();
					LehrerPojo fachlehrer1 = fach.getFachlehrerEins();
					if (fachlehrer1 != null) {
						LehrerBlogLaso lehrerblog = new LehrerBlogLaso();
						lehrerblog.setLehrer(fachlehrer1);
						lehrerblog.setTimestamp(new Date());
						lehrerblog.setEreignis("Der von Ihnen unterrichtete Schüler '" + schueler.getVorname() + " " + schueler.getName() + "' im Fach '" + fach.getFachbezeichnung() + "' wurde gelöscht.");
						lehrerBlogContainer.addBean(lehrerblog);
					}
					LehrerPojo fachlehrer2 = fach.getFachlehrerZwei();
					if (fachlehrer2 != null) {
						LehrerBlogLaso lehrerblog = new LehrerBlogLaso();
						lehrerblog.setLehrer(fachlehrer2);
						lehrerblog.setTimestamp(new Date());
						lehrerblog.setEreignis("Der von Ihnen unterrichtete Schüler '" + schueler.getVorname() + " " + schueler.getName() + "' im Fach '" + fach.getFachbezeichnung() + "' wurde gelöscht.");
						lehrerBlogContainer.addBean(lehrerblog);
					}
				}
				SchuelerContainer.getInstance().deleteSchueler(schueler);
				refreshPage();
			}
		}
	}
}

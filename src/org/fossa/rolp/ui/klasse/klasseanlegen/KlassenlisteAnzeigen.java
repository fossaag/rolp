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

package org.fossa.rolp.ui.klasse.klasseanlegen;

import org.fossa.rolp.RolpApplication;
import org.fossa.rolp.data.klasse.KlasseContainer;
import org.fossa.rolp.data.klasse.KlasseLaso;
import org.fossa.rolp.data.schueler.SchuelerContainer;
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

public class KlassenlisteAnzeigen extends FossaWindow implements Button.ClickListener, ItemClickListener, CloseListener {

	private static final long serialVersionUID = -2743590112908126023L;
	
	private Button windowCloseButton = new Button ("Schließen", (ClickListener) this);
	private Button klasseHinzufuegenButton = new Button ("Hinzufügen", (ClickListener) this);
	private Button klasseEntfernenButton = new Button ("Entfernen", (ClickListener) this);
	private Button klasseBearbeitenButton = new Button("Bearbeiten", (ClickListener) this);
	private RolpApplication app;
	private KlassenList klassenList;
	private KlasseAnlegen klasseAnlegen;

	private FossaBooleanDialog confirmDeleteKlasse;

	
	public KlassenlisteAnzeigen(RolpApplication app) {
		super(app);
		this.app = app;

		setCaption(" - Anzeige der Klassenliste - ");
		setWidth("800px");
		center();
		
		VerticalLayout layoutVertical = new VerticalLayout();
		setContent(layoutVertical);
		
		CustomLayout layout = new CustomLayout("./listeAnzeigen/listeAnzeigen");
		HorizontalLayout buttonBattery = new HorizontalLayout();
		buttonBattery.setSpacing(true);
		HorizontalLayout buttonBatteryBearbeiten = new HorizontalLayout();
		buttonBatteryBearbeiten.setSpacing(true);
		
		buttonBatteryBearbeiten.addComponent(klasseHinzufuegenButton);
		klasseHinzufuegenButton.setWidth("150px");
		buttonBatteryBearbeiten.addComponent(klasseBearbeitenButton);
		klasseBearbeitenButton.setWidth("150px");
		buttonBatteryBearbeiten.addComponent(klasseEntfernenButton);
		klasseEntfernenButton.setWidth("150px");

		layout.addComponent(buttonBatteryBearbeiten, "buttonBatteryBearbeiten");
		layout.addComponent(buttonBattery, "buttonBattery");

		layout.addComponent(windowCloseButton, "windowCloseButton");
		windowCloseButton.setWidth("100%");
		
		klassenList = getKlassenList();
		klassenList.addStyleName("klassenList");
		
		layoutVertical.addComponent(klassenList);
		layoutVertical.addComponent(layout);
	}

	@Override
	public void buttonClick(ClickEvent event) {
		final Button source = event.getButton();
		refreshPage();
		if (source == windowCloseButton) {
			close();
		}
		else if (source == klasseHinzufuegenButton){
			openSubwindow(getKlasseAnlegen(null));
		}
		else if (source == klasseBearbeitenButton){
			KlasseLaso klasse = (KlasseLaso) klassenList.getValue();
			if (klasse == null) {
				app.getMainWindow().showNotification("keine Klasse ausgewählt");
				return;
			}
			klasseBearbeiten(klasse);
		} else if (source == klasseEntfernenButton) {
			KlasseLaso klasse = (KlasseLaso) klassenList.getValue();
			if (klasse != null) {
				if (dependingLasoIsLocked(klasse)) {
					getWindow().showNotification("LOCKED");
					return;
				}
				if (!SchuelerContainer.getAllSchuelerOfKlasse(klasse.getPojo()).getItemIds().isEmpty()) {
					showNotification("Die Klasse hat noch Schüler und kann daher nicht gelöscht werden", Notification.TYPE_ERROR_MESSAGE);
					return;
				}
				confirmDeleteKlasse = new FossaBooleanDialog(app, " - Bestätigung - ", "Möchten Sie die Klasse '" + klasse.getKlassenname() + "' wirklich entgültig entfernen?", "Ja", "Nein");
				confirmDeleteKlasse.addListener((CloseListener) this); 
				getApplication().getMainWindow().addWindow(confirmDeleteKlasse);
			} 
		}
	}

	private boolean dependingLasoIsLocked(KlasseLaso klasse) {
		return klasse.isLocked();
	}
	
	private KlasseAnlegen getKlasseAnlegen(KlasseLaso klasse) {
		if (klasse == null) {
			klasse = new KlasseLaso();
		}
		return new KlasseAnlegen(app, klasse);
	}
	
	private KlassenList getKlassenList() {
		if (klassenList == null) {
			klassenList = new KlassenList(app, this, KlasseContainer.NATURAL_COL_ORDER, KlasseContainer.COL_HEADERS);
		}
		return klassenList;
	}
	

	@Override
	public void itemClick(ItemClickEvent event) {
		Property property = (Property) event.getSource();
		if (property == klassenList && event.isDoubleClick()) {
			KlasseLaso klasse = (KlasseLaso) event.getItemId();
			klasseBearbeiten(klasse);
		}
	}
	
	private void klasseBearbeiten(KlasseLaso klasse) {
		for (KlasseLaso aKlasse : KlasseContainer.getInstance().getItemIds()) {
			if (klasse.getId().equals(aKlasse.getId())) {
				klasseAnlegen = getKlasseAnlegen(aKlasse);
				openSubwindow(klasseAnlegen);
			}
		}
	}

	protected void openSubwindow(FossaWindow window) {
		getApplication().getMainWindow().addWindow(window);
	}

	public void refreshPage() {
		getKlassenList().refresh();
		getKlassenList().requestRepaintAll();
		getKlassenList().refreshRowCache();		
	}
	
	@Override
	public void windowClose(CloseEvent event) {
		Window source = event.getWindow();
		if (source == confirmDeleteKlasse) {
			if (confirmDeleteKlasse.getDecision()) {
				KlasseLaso klasse = (KlasseLaso) klassenList.getValue();
				if (dependingLasoIsLocked(klasse)) {
					getWindow().showNotification("LOCKED");
					return;
				}
				KlasseContainer.getInstance().deleteKlasse(klasse);
				refreshPage();
			}
		}
	}

	@Override
	public void unlockLaso() {
		// TODO Auto-generated method stub
		
	}
}

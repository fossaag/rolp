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

package org.fossa.rolp.ui.fach.fachdefinition;

import org.fossa.rolp.RolpApplication;
import org.fossa.rolp.data.fach.FachContainer;
import org.fossa.rolp.data.fach.FachLaso;
import org.fossa.rolp.data.fach.fachdefinition.FachdefinitionContainer;
import org.fossa.rolp.data.fach.fachdefinition.FachdefinitionLaso;
import org.fossa.rolp.ui.fach.fachdefinition.leb.FachbezeichnungenLeblisteAnzeigen;
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

public class FachdefinitionlisteAnzeigen extends FossaWindow implements Button.ClickListener, ItemClickListener, CloseListener {

	private static final long serialVersionUID = -3678090702784055985L;
	
	private Button windowCloseButton = new Button ("Schließen", (ClickListener) this);
	private Button fachdefinitionenHinzufuegenButton = new Button ("Hinzufügen", (ClickListener) this);
	private Button fachdefinitionenEntfernenButton = new Button ("Entfernen", (ClickListener) this);
	private Button fachdefinitionenBearbeitenButton = new Button("Bearbeiten", (ClickListener) this);
	private Button bezeichnungenZuordnenButton = new Button("alternative Fachbezeichnungen zuordnen", (ClickListener) this);
	private RolpApplication app;
	private FachdefinitionList fachdefinitionList;
	private FachdefinitionenVerwalten fachdefinitionenAnlegen;

	private FossaBooleanDialog confirmDeleteFachdefinition;

	private FachbezeichnungenLeblisteAnzeigen fachbezeichnungenLebListeZuordnen;	

	public FachdefinitionlisteAnzeigen(RolpApplication app) {
		super(app);
		this.app = app;
		setCaption(" - Ansicht der Fachdefinitionen - ");
		setWidth("800px");
		center();
		
		VerticalLayout layoutVertical = new VerticalLayout();
		setContent(layoutVertical);
		
		CustomLayout layout = new CustomLayout("./listeAnzeigen/listeAnzeigen");
		HorizontalLayout buttonBattery = new HorizontalLayout();
		buttonBattery.setSpacing(true);
		HorizontalLayout buttonBatteryBearbeiten = new HorizontalLayout();
		buttonBatteryBearbeiten.setSpacing(true);
		
		
		buttonBatteryBearbeiten.addComponent(fachdefinitionenHinzufuegenButton);
		fachdefinitionenHinzufuegenButton.setWidth("150px");
		buttonBatteryBearbeiten.addComponent(fachdefinitionenBearbeitenButton);
		fachdefinitionenBearbeitenButton.setWidth("150px");
		buttonBatteryBearbeiten.addComponent(fachdefinitionenEntfernenButton);
		fachdefinitionenEntfernenButton.setWidth("150px");
		
		buttonBattery.addComponent(bezeichnungenZuordnenButton);
		bezeichnungenZuordnenButton.setWidth("250px");
		
		layout.addComponent(buttonBatteryBearbeiten, "buttonBatteryBearbeiten");
		layout.addComponent(buttonBattery, "buttonBattery");

		layout.addComponent(windowCloseButton, "windowCloseButton");
		windowCloseButton.setWidth("100%");
		
		fachdefinitionList = getFachdefinitionList();
		
		layoutVertical.addComponent(fachdefinitionList);
		layoutVertical.addComponent(layout);
	}

	@Override
	public void buttonClick(ClickEvent event) {
		final Button source = event.getButton();
		refreshPage();
		if (source == windowCloseButton) {
			close();
		}
		else if (source == fachdefinitionenHinzufuegenButton){
			openSubwindow(getFachdefinitionenAnlegen(null));
		}
		else if (source == fachdefinitionenBearbeitenButton) {
			FachdefinitionLaso fachdefinition = (FachdefinitionLaso) fachdefinitionList.getValue();
			if (fachdefinition == null) {
				app.getMainWindow().showNotification("keine Fachdefinition ausgewählt");
				return;
			}
			fachdefinitionenBearbeiten(fachdefinition);
		} else if (source == fachdefinitionenEntfernenButton) {
			FachdefinitionLaso fachdefinition = (FachdefinitionLaso) fachdefinitionList.getValue();
			if (fachdefinition != null) {
				if (dependingLasoIsLocked(fachdefinition)) {
					getWindow().showNotification("LOCKED");
					return;
				}
				for (FachLaso fach : FachContainer.getInstance().getItemIds()) {
					if (fachdefinition.getId().equals(fach.getFachdefinition().getId())) {
						getWindow().showNotification("Die Fachdefinition wird bereits verwendet und kann daher nicht gelöscht werden.");
						return;
					}
				}
				confirmDeleteFachdefinition = new FossaBooleanDialog(app, " - Bestätigung - ", "Möchten Sie die Fachdefinition zu '" + fachdefinition.getFachbezeichnung() + "' wirklich entgültig entfernen?", "Ja", "Nein");
				confirmDeleteFachdefinition.addListener((CloseListener) this); 
				getApplication().getMainWindow().addWindow(confirmDeleteFachdefinition);
			} 
		} else if (source == bezeichnungenZuordnenButton) {
			FachdefinitionLaso fachdefinition = (FachdefinitionLaso) getFachdefinitionList().getValue();
			if (fachdefinition == null) {
				app.getMainWindow().showNotification("keine Fachdefinition ausgewählt");
				return;
			}
			try {
				openSubwindow(getFachbezeichnungenLebZuordnen(fachdefinition));
			} catch (FossaLasoLockedException e) {
				getWindow().showNotification("LOCKED");
				return;
			}
		}
	}	
	
	private FachbezeichnungenLeblisteAnzeigen getFachbezeichnungenLebZuordnen(FachdefinitionLaso fachdefinition) throws FossaLasoLockedException {
		fachbezeichnungenLebListeZuordnen = new FachbezeichnungenLeblisteAnzeigen(app, fachdefinition);
		return fachbezeichnungenLebListeZuordnen;
	}

	private boolean dependingLasoIsLocked(FachdefinitionLaso fachdefinition) {
		if (fachdefinition.isLocked()) {
			return true;
		}
		return false;
	}
	
	private FachdefinitionenVerwalten getFachdefinitionenAnlegen(FachdefinitionLaso fachdefinition) {
		if (fachdefinition == null) {
			fachdefinition = new FachdefinitionLaso();
		}
		fachdefinitionenAnlegen = new FachdefinitionenVerwalten(app, fachdefinition);
		return fachdefinitionenAnlegen;
	}
	
	private FachdefinitionList getFachdefinitionList() {
		if (fachdefinitionList == null) {
			fachdefinitionList = new FachdefinitionList();
		}
		return fachdefinitionList;
	}
	
	@Override
	public void itemClick(ItemClickEvent event) {
		Property property = (Property) event.getSource();
		if (property == fachdefinitionList && event.isDoubleClick()) {
			FachdefinitionLaso fachdefinition = (FachdefinitionLaso) event.getItemId();
			fachdefinitionenBearbeiten(fachdefinition);
		}
	}
	
	private void fachdefinitionenBearbeiten(FachdefinitionLaso fachdefinition) {
		for (FachdefinitionLaso aFachdefinition : FachdefinitionContainer.getInstance().getItemIds()) {
			if (fachdefinition.getId().equals(aFachdefinition.getId())) {
				fachdefinitionenAnlegen = getFachdefinitionenAnlegen(aFachdefinition);
				openSubwindow(fachdefinitionenAnlegen);
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
		getFachdefinitionList().refresh();
		getFachdefinitionList().requestRepaintAll();
		getFachdefinitionList().refreshRowCache();
	}
	
	@Override
	public void windowClose(CloseEvent event) {
		Window source = event.getWindow();
		if (source == confirmDeleteFachdefinition) {
			if (confirmDeleteFachdefinition.getDecision()) {
				FachdefinitionLaso fachdefinition = (FachdefinitionLaso) fachdefinitionList.getValue();
				if (dependingLasoIsLocked(fachdefinition)) {
					getWindow().showNotification("LOCKED");
					return;
				}
				FachdefinitionContainer.getInstance().deleteFachdefinition(fachdefinition);
				refreshPage();
			}
		}
	}

}

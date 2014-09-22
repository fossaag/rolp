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

package org.fossa.rolp.ui.fach.fachdefinition.leb;

import org.fossa.rolp.RolpApplication;
import org.fossa.rolp.data.fach.fachdefinition.FachdefinitionLaso;
import org.fossa.rolp.data.fach.fachdefinition.leb.FachbezeichnungLebContainer;
import org.fossa.rolp.data.fach.fachdefinition.leb.FachbezeichnungLebLaso;
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

public class FachbezeichnungenLeblisteAnzeigen extends FossaWindow implements Button.ClickListener, ItemClickListener, CloseListener {

	private static final long serialVersionUID = -3678090702784055985L;
	
	private Button windowCloseButton = new Button ("Schließen", (ClickListener) this);
	private Button fachbezeichnungenLebHinzufuegenButton = new Button ("Hinzufügen", (ClickListener) this);
	private Button fachbezeichnungenLebEntfernenButton = new Button ("Entfernen", (ClickListener) this);
	private Button fachbezeichnungenLebBearbeitenButton = new Button("Bearbeiten", (ClickListener) this);
	private RolpApplication app;
	private FachbezeichnungenLebList fachbezeichnungenLebList;
	private FachbezeichnungLebVerwalten fachbezeichnungenLebAnlegen;

	private FossaBooleanDialog confirmDeleteFachbezeichnungenLeb;

	private FachdefinitionLaso fachdefinition;	

	public FachbezeichnungenLeblisteAnzeigen(RolpApplication app, FachdefinitionLaso fachdefinition) {
		super(app);
		this.app = app;
		this.fachdefinition = fachdefinition;
		setCaption(" - Ansicht der alternativen Fachbezeichnungen für " + fachdefinition.getFachbezeichnung() + ", die im LEB markiert werden - ");
		setWidth("560px");
		center();
		
		VerticalLayout layoutVertical = new VerticalLayout();
		setContent(layoutVertical);
		
		CustomLayout layout = new CustomLayout("./listeAnzeigen/listeAnzeigen");
		HorizontalLayout buttonBattery = new HorizontalLayout();
		buttonBattery.setSpacing(true);
		HorizontalLayout buttonBatteryBearbeiten = new HorizontalLayout();
		buttonBatteryBearbeiten.setSpacing(true);
		
		
		buttonBatteryBearbeiten.addComponent(fachbezeichnungenLebHinzufuegenButton);
		fachbezeichnungenLebHinzufuegenButton.setWidth("150px");
		buttonBatteryBearbeiten.addComponent(fachbezeichnungenLebBearbeitenButton);
		fachbezeichnungenLebBearbeitenButton.setWidth("150px");
		buttonBatteryBearbeiten.addComponent(fachbezeichnungenLebEntfernenButton);
		fachbezeichnungenLebEntfernenButton.setWidth("150px");
		
		layout.addComponent(buttonBatteryBearbeiten, "buttonBatteryBearbeiten");
		layout.addComponent(buttonBattery, "buttonBattery");

		layout.addComponent(windowCloseButton, "windowCloseButton");
		windowCloseButton.setWidth("100%");
		
		fachbezeichnungenLebList = getFachbezeichnungenLebList(this);
		
		layoutVertical.addComponent(fachbezeichnungenLebList);
		layoutVertical.addComponent(layout);
	}

	@Override
	public void buttonClick(ClickEvent event) {
		final Button source = event.getButton();
		refreshPage();
		if (source == windowCloseButton) {
			close();
		}
		else if (source == fachbezeichnungenLebHinzufuegenButton){
			openSubwindow(getFachbezeichnungenLebAnlegen(null));
		}
		else if (source == fachbezeichnungenLebBearbeitenButton) {
			FachbezeichnungLebLaso fachbezeichnungenLeb = (FachbezeichnungLebLaso) fachbezeichnungenLebList.getValue();
			if (fachbezeichnungenLeb == null) {
				app.getMainWindow().showNotification("keine FachbezeichnungenLeb ausgewählt");
				return;
			}
			fachbezeichnungenLebBearbeiten(fachbezeichnungenLeb);
		} else if (source == fachbezeichnungenLebEntfernenButton) {
			FachbezeichnungLebLaso fachbezeichnungenLeb = (FachbezeichnungLebLaso) fachbezeichnungenLebList.getValue();
			if (fachbezeichnungenLeb != null) {
				if (dependingLasoIsLocked(fachbezeichnungenLeb)) {
					getWindow().showNotification("LOCKED");
					return;
				}
				confirmDeleteFachbezeichnungenLeb = new FossaBooleanDialog(app, " - Bestätigung - ", "Möchten Sie den Fachbezeichnung '" + fachbezeichnungenLeb.getBezeichnung() + "' wirklich entgültig entfernen?", "Ja", "Nein");
				confirmDeleteFachbezeichnungenLeb.addListener((CloseListener) this); 
				getApplication().getMainWindow().addWindow(confirmDeleteFachbezeichnungenLeb);
			}
		}
	}
	
	private boolean dependingLasoIsLocked(FachbezeichnungLebLaso fachbezeichnungenLeb) {
		if (fachbezeichnungenLeb.isLocked()) {
			return true;
		}
		return false;
	}
	
	private FachbezeichnungLebVerwalten getFachbezeichnungenLebAnlegen(FachbezeichnungLebLaso fachbezeichnungenLeb) {
		if (fachbezeichnungenLeb == null) {
			fachbezeichnungenLeb = new FachbezeichnungLebLaso();
		}
		fachbezeichnungenLebAnlegen = new FachbezeichnungLebVerwalten(app, fachbezeichnungenLeb, fachdefinition.getPojo());
		return fachbezeichnungenLebAnlegen;
	}
	
	private FachbezeichnungenLebList getFachbezeichnungenLebList(FachbezeichnungenLeblisteAnzeigen fachbezeichnungenLeblisteAnzeigen) {
		if (fachbezeichnungenLebList == null) {
			fachbezeichnungenLebList = new FachbezeichnungenLebList(fachbezeichnungenLeblisteAnzeigen);
		}
		return fachbezeichnungenLebList;
	}
	
	@Override
	public void itemClick(ItemClickEvent event) {
		Property property = (Property) event.getSource();
		if (property == fachbezeichnungenLebList && event.isDoubleClick()) {
			FachbezeichnungLebLaso fachbezeichnungenLeb = (FachbezeichnungLebLaso) event.getItemId();
			fachbezeichnungenLebBearbeiten(fachbezeichnungenLeb);
		}
	}
	
	private void fachbezeichnungenLebBearbeiten(FachbezeichnungLebLaso fachbezeichnungenLeb) {
		for (FachbezeichnungLebLaso aFachbezeichnungenLeb : FachbezeichnungLebContainer.getInstance().getItemIds()) {
			if (fachbezeichnungenLeb.getId().equals(aFachbezeichnungenLeb.getId())) {
				fachbezeichnungenLebAnlegen = getFachbezeichnungenLebAnlegen(aFachbezeichnungenLeb);
				openSubwindow(fachbezeichnungenLebAnlegen);
			}
		}	
	}


	protected void openSubwindow(FossaWindow window) {
		getApplication().getMainWindow().addWindow(window);
	}
	
	public FachdefinitionLaso getFachdefinition() {
		return fachdefinition;
	}
	
	@Override
	public void unlockLaso() {
	}
	
	public void refreshPage() {
		getFachbezeichnungenLebList(this).refresh(this);
		getFachbezeichnungenLebList(this).requestRepaintAll();
		getFachbezeichnungenLebList(this).refreshRowCache();	
	}
	
	@Override
	public void windowClose(CloseEvent event) {
		Window source = event.getWindow();
		if (source == confirmDeleteFachbezeichnungenLeb) {
			if (confirmDeleteFachbezeichnungenLeb.getDecision()) {
				FachbezeichnungLebLaso fachbezeichnungenLeb = (FachbezeichnungLebLaso) fachbezeichnungenLebList.getValue();
				if (dependingLasoIsLocked(fachbezeichnungenLeb)) {
					getWindow().showNotification("LOCKED");
					return;
				}
				FachbezeichnungLebContainer.getInstance().deleteFachbezeichnungLeb(fachbezeichnungenLeb);
				refreshPage();
			}
		}
	}
}

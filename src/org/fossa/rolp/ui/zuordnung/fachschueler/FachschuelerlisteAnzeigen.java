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

package org.fossa.rolp.ui.zuordnung.fachschueler;

import org.fossa.rolp.RolpApplication;
import org.fossa.rolp.data.einschaetzung.EinschaetzungLaso;
import org.fossa.rolp.data.fach.FachPojo;
import org.fossa.rolp.data.zuordnung.fachschueler.ZuordnungFachSchuelerContainer;
import org.fossa.rolp.data.zuordnung.fachschueler.ZuordnungFachSchuelerLaso;
import org.fossa.rolp.ui.einschaetzung.EinschaetzungAnlegen;
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

public class FachschuelerlisteAnzeigen extends FossaWindow implements ClickListener, ItemClickListener {

	private static final long serialVersionUID = 1689927725927270794L;
	
	private RolpApplication app;
	private FachschuelerList fachschuelerList;
	private Button facheinschaetzungSchreibenButton = new Button("Facheinschätzung schreiben", this);
	private Button windowCloseButton = new Button("Schließen", (Button.ClickListener) this);

	private EinschaetzungAnlegen einschaetzungAnlegen;
	private FachPojo fach;

	public FachschuelerlisteAnzeigen(RolpApplication app, FachPojo fach, FachschuelerList fachschuelerListe) {
		super(app);
		this.app = app;
		this.fach = fach;
		this.fachschuelerList = fachschuelerListe;
		setWidth("550px");
		
		VerticalLayout layoutVertical = new VerticalLayout();
		setContent(layoutVertical);
		
		CustomLayout layout = new CustomLayout("./listeAnzeigen/listeAnzeigen");
		HorizontalLayout buttonBatteryBearbeiten = new HorizontalLayout();
		buttonBatteryBearbeiten.setSpacing(true);
		
		buttonBatteryBearbeiten.addComponent(facheinschaetzungSchreibenButton);
		facheinschaetzungSchreibenButton.setWidth("250px");
		buttonBatteryBearbeiten.addComponent(windowCloseButton);
		windowCloseButton.setWidth("250px");
	
		layout.addComponent(buttonBatteryBearbeiten, "buttonBatteryBearbeiten");
		
		fachschuelerList.addStyleName("schuelerList");
		
		layoutVertical.addComponent(fachschuelerList);
		layoutVertical.addComponent(layout);
	}

	@Override
	public void unlockLaso() {
	}
	
	public void refreshPage() {
		fachschuelerList.refresh();
		fachschuelerList.requestRepaintAll();
		fachschuelerList.refreshRowCache();	
	}

	@Override
	public void buttonClick(ClickEvent event) {
		final Button source = event.getButton();
		refreshPage();
		if (source == windowCloseButton) {
			close();
		} else if (source == facheinschaetzungSchreibenButton) {
			ZuordnungFachSchuelerLaso zuordnungFS = (ZuordnungFachSchuelerLaso) fachschuelerList.getValue();
			if (zuordnungFS == null) {
				app.getMainWindow().showNotification("kein Schüler ausgewählt");
				return;
			}
			facheinschaetzungBearbeiten(ZuordnungFachSchuelerContainer.getZuordnung(zuordnungFS.getSchueler(), fach));
		}
	}

	private void facheinschaetzungBearbeiten(ZuordnungFachSchuelerLaso zuordnungFS) {
		EinschaetzungLaso einschaetzung = zuordnungFS.getFacheinschaetzung();
		if (einschaetzung == null){
			einschaetzung = new EinschaetzungLaso();
			zuordnungFS.setFacheinschaetzung(einschaetzung);
		}
		try {
			getApplication().getMainWindow().addWindow(getEinschaetzungAnlegen(zuordnungFS));
		} catch (FossaLasoLockedException e) {
			getWindow().showNotification("LOCKED");
			return;
		}
	}
	
	private EinschaetzungAnlegen getEinschaetzungAnlegen(ZuordnungFachSchuelerLaso zuordnungFS) throws FossaLasoLockedException {		
		einschaetzungAnlegen = new EinschaetzungAnlegen(app, zuordnungFS.getFacheinschaetzung(), "Facheinschätzung für " + zuordnungFS.getVorname() + " " + zuordnungFS.getName() + " im Fach " + zuordnungFS.getFachbezeichnung(), zuordnungFS);
		return einschaetzungAnlegen;
	}

	@Override
	public void itemClick(ItemClickEvent event) {
		Property property = (Property) event.getSource();
		if (property == fachschuelerList && event.isDoubleClick()) {
			ZuordnungFachSchuelerLaso zuordnungFS = (ZuordnungFachSchuelerLaso) event.getItemId();
			facheinschaetzungBearbeiten(ZuordnungFachSchuelerContainer.getZuordnung(zuordnungFS.getSchueler(), fach));
		}
	}

}

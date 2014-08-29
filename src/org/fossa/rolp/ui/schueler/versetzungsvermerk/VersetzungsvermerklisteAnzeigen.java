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

package org.fossa.rolp.ui.schueler.versetzungsvermerk;

import org.fossa.rolp.RolpApplication;
import org.fossa.rolp.data.klasse.KlasseContainer;
import org.fossa.rolp.data.klasse.KlasseLaso;
import org.fossa.rolp.data.schueler.SchuelerContainer;
import org.fossa.rolp.data.schueler.SchuelerLaso;
import org.fossa.rolp.ui.schueler.SchuelerList;
import org.fossa.vaadin.ui.FossaWindow;

import com.vaadin.event.ItemClickEvent;
import com.vaadin.event.ItemClickEvent.ItemClickListener;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CustomLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window.CloseListener;

public class VersetzungsvermerklisteAnzeigen extends FossaWindow implements Button.ClickListener, ItemClickListener, CloseListener {

	private static final long serialVersionUID = -6514418795515349474L;
	
	private Button windowCloseButton = new Button ("Schlieﬂen", (ClickListener) this);
	private Button versetzungsvermerkBearbeitennButton = new Button("Versetzungsvermerk bearbeiten", (ClickListener) this);
	private RolpApplication app;
	private KlasseLaso klasseLaso;
	private SchuelerList schuelerList = null;
	private VersetzungsvermerkBearbeiten versetzungsvermerkBearbeiten;
	
	public VersetzungsvermerklisteAnzeigen(RolpApplication app) {
		super(app);
		this.app = app;
		this.klasseLaso = KlasseContainer.getKlasseByLehrer(app.getLoginLehrer());

		setCaption(" - Anzeige der Versetzungsvermerkliste - ");
		setWidth("800px");
		center();
		
		VerticalLayout layoutVertical = new VerticalLayout();
		setContent(layoutVertical);
		
		CustomLayout layout = new CustomLayout("./listeAnzeigen/listeAnzeigen");
		HorizontalLayout buttonBattery = new HorizontalLayout();
		buttonBattery.setSpacing(true);
		HorizontalLayout buttonBatteryBearbeiten = new HorizontalLayout();
		buttonBatteryBearbeiten.setSpacing(true);
		
		buttonBatteryBearbeiten.addComponent(versetzungsvermerkBearbeitennButton);
		versetzungsvermerkBearbeitennButton.setWidth("250px");

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
		}else if (source == versetzungsvermerkBearbeitennButton){
			SchuelerLaso schueler = (SchuelerLaso) schuelerList.getValue();
			if (schueler != null) {
				versetzungsvermerkBearbeiten(schueler);
			} 
		}
	}
	
	private void versetzungsvermerkBearbeiten(SchuelerLaso schueler) {
		for (SchuelerLaso aSchueler : SchuelerContainer.getInstance().getItemIds()) {
			if (schueler.getId().equals(aSchueler.getId())) {
				versetzungsvermerkBearbeiten = getVersetzungsvermerkBearbeiten(aSchueler);
				openSubwindow(versetzungsvermerkBearbeiten);
			}
		}
	}

	private VersetzungsvermerkBearbeiten getVersetzungsvermerkBearbeiten(SchuelerLaso schueler) {
		if (schueler == null) {
			schueler = new SchuelerLaso(klasseLaso.getPojo());	
		}
		versetzungsvermerkBearbeiten = new VersetzungsvermerkBearbeiten(app, schueler);
		return versetzungsvermerkBearbeiten;
	}

	private SchuelerList getSchuelerList() {
		if (schuelerList == null) {
			schuelerList = new SchuelerList(app, this, SchuelerContainer.VERSETZUNGSVERMERK_COL_ORDER, SchuelerContainer.VERSETZUNGSVERMERK_COL_HEADERS);
		}
		return schuelerList;
	}

	@Override
	public void unlockLaso() {
	}
	
	protected void openSubwindow(FossaWindow window) {
		getApplication().getMainWindow().addWindow(window);
	}

	@Override
	public void windowClose(CloseEvent e) {
		refreshPage();
	}
	
	public void refreshPage() {
		getSchuelerList().refresh(klasseLaso.getPojo());
		getSchuelerList().requestRepaintAll();
		getSchuelerList().refreshRowCache();		
	}

	@Override
	public void itemClick(ItemClickEvent event) {
		// TODO Auto-generated method stub
	}

}

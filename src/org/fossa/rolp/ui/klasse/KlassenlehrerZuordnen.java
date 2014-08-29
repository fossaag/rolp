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

package org.fossa.rolp.ui.klasse;

import org.fossa.vaadin.FossaApplication;
import org.fossa.vaadin.ui.FossaWindow;
import org.fossa.vaadin.ui.exception.FossaLasoLockedException;

import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CustomLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;

public class KlassenlehrerZuordnen extends FossaWindow implements Button.ClickListener {

	private static final long serialVersionUID = 2897118056345029184L;
	private Button windowCloseButton = new Button ("Speichern & Schlieﬂen", (ClickListener) this);
	private KlassenlehrerZuordnenList klassenlehrerZuordnenList;

	public KlassenlehrerZuordnen(FossaApplication app) throws FossaLasoLockedException {
		super(app);
		setWidth("600px");
		setHeight("600px");
		center();
		
		VerticalLayout layoutVertical = new VerticalLayout();
		setContent(layoutVertical);
		
		CustomLayout layout = new CustomLayout("./listeAnzeigen/listeAnzeigen");
		HorizontalLayout buttonBattery = new HorizontalLayout();
		buttonBattery.setSpacing(true);
		HorizontalLayout buttonBatteryBearbeiten = new HorizontalLayout();
		buttonBatteryBearbeiten.setSpacing(true);
		
		layout.addComponent(buttonBatteryBearbeiten, "buttonBatteryBearbeiten");
		layout.addComponent(buttonBattery, "buttonBattery");

		layout.addComponent(windowCloseButton, "windowCloseButton");
		windowCloseButton.setWidth("100%");
		
		layoutVertical.addComponent(getKlassenlehrerZuordnenList());
		layoutVertical.addComponent(layout);	
	}

	private KlassenlehrerZuordnenList getKlassenlehrerZuordnenList() throws FossaLasoLockedException {
		if (klassenlehrerZuordnenList == null) {
			klassenlehrerZuordnenList = new KlassenlehrerZuordnenList();
		}
		return klassenlehrerZuordnenList;
	}

	@Override
	public void unlockLaso() {
		klassenlehrerZuordnenList.unlockLasos();
	}

	@Override
	public void buttonClick(ClickEvent event) {
		final Button source = event.getButton();
		if (source == windowCloseButton) {
			close();
		}
	}

}

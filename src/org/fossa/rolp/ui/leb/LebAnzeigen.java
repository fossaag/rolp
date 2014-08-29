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

package org.fossa.rolp.ui.leb;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.fossa.rolp.data.klasse.KlasseLaso;
import org.fossa.rolp.data.schueler.SchuelerLaso;
import org.fossa.rolp.util.LebCreator;
import org.fossa.vaadin.FossaApplication;
import org.fossa.vaadin.ui.FossaWindow;

import com.itextpdf.text.DocumentException;
import com.vaadin.terminal.Sizeable;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.VerticalLayout;

public class LebAnzeigen extends FossaWindow implements Button.ClickListener {

	private static final long serialVersionUID = -2257671299149157475L;
	
	Button windowCloseButton = new Button ("Fenster schlieﬂen", (ClickListener) this);

	public LebAnzeigen(FossaApplication app, SchuelerLaso schueler, KlasseLaso klasse) throws DocumentException, IOException {
		super(app);
		setCaption(" - Lernentwicklungsbericht anzeigen - ");
		VerticalLayout layout = new VerticalLayout();
		setWidth("800px");
		setContent(layout);
		layout.setSpacing(true);
		
		Embedded embeddedPdf = new Embedded();
		embeddedPdf.setType(Embedded.TYPE_BROWSER);
		DateFormat dateFormat = new SimpleDateFormat("ddMMyy-HHmmss");
		String dateiname = "Klasse" + klasse.getKlassenname() + "_" + schueler.getVorname() + "_" + schueler.getName() + "_" + dateFormat.format(new Date()) + ".pdf";
		embeddedPdf.setSource(new LebCreator(app, schueler, klasse, dateiname).getLebResource());
		embeddedPdf.setWidth(100, Sizeable.UNITS_PERCENTAGE);
		embeddedPdf.setHeight("580px");
		windowCloseButton.setWidth(100, Sizeable.UNITS_PERCENTAGE);
		layout.addComponent(embeddedPdf);
		layout.addComponent(windowCloseButton);
	}

	@Override
	public void unlockLaso() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void buttonClick(ClickEvent event) {
		final Button source = event.getButton();
		if (source == windowCloseButton) {
			close();
			}
		}

}

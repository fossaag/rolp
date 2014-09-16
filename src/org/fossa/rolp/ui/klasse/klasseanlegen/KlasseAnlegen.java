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

import org.fossa.rolp.data.klasse.KlasseLaso;
import org.fossa.vaadin.FossaApplication;
import org.fossa.vaadin.laso.FossaLaso;
import org.fossa.vaadin.ui.FossaWindow;

import com.vaadin.ui.CustomLayout;

public class KlasseAnlegen extends FossaWindow {

	private static final long serialVersionUID = -6514418795515349474L;
	
	private KlasseAnlegenForm formKlasseAnlegen;
	private KlasseLaso klasseLaso;

	public KlasseAnlegen(FossaApplication app, KlasseLaso klasse) {
		super(app);
		this.klasseLaso = klasse;
		setCaption(" - Klasse - ");
		setWidth("500px");
		center();
		CustomLayout layout = new CustomLayout("./subWindows/formAnlegen");
		setContent(layout);
		
		getKlasseAnlegenForm();
		layout.addComponent(formKlasseAnlegen, "form");
	}

	private KlasseAnlegenForm getKlasseAnlegenForm() {
		if (formKlasseAnlegen == null) {
			formKlasseAnlegen = new KlasseAnlegenForm();
			if (klasseLaso.getId() == null) {
				klasseLaso = new KlasseLaso();
				formKlasseAnlegen.addTemporaryItem(klasseLaso);
			} else {
				formKlasseAnlegen.setKlasse(klasseLaso);
			}
		}
		return formKlasseAnlegen;
	}
	
	@Override
	public void unlockLaso() {	
		if (klasseLaso != null && !formKlasseAnlegen.isReadOnly()) {
			((FossaLaso) klasseLaso).unlock();
		}		
	}

}

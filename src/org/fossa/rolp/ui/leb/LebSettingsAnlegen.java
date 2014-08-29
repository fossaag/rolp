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

import org.fossa.rolp.data.leb.LebSettingsLaso;
import org.fossa.vaadin.FossaApplication;
import org.fossa.vaadin.laso.FossaLaso;
import org.fossa.vaadin.ui.FossaWindow;

import com.vaadin.ui.CustomLayout;

public class LebSettingsAnlegen extends FossaWindow {

	private static final long serialVersionUID = -4658658818720223090L;
	
	private LebSettingsAnlegenForm formLebSettingsAnlegen;
	private LebSettingsLaso lebsettingsLaso;

	public LebSettingsAnlegen(FossaApplication app, LebSettingsLaso lebSettings) {
		super(app);
		this.lebsettingsLaso = lebSettings;
		setCaption(" - Einstellungen - ");
		setWidth("500px");
		center();
		CustomLayout layout = new CustomLayout("./subWindows/formAnlegen");
		setContent(layout);
		
		getLebSettingsAnlegenForm();
		layout.addComponent(formLebSettingsAnlegen, "form");
	}

	private LebSettingsAnlegenForm getLebSettingsAnlegenForm() {
		if (formLebSettingsAnlegen == null) {
			formLebSettingsAnlegen = new LebSettingsAnlegenForm();
			if (lebsettingsLaso.getId() == null) {
				formLebSettingsAnlegen.addTemporaryItem(lebsettingsLaso);
			} else {
				formLebSettingsAnlegen.setFossaLaso(lebsettingsLaso);
			}
		}
		return formLebSettingsAnlegen;
	}

	@Override
	public void unlockLaso() {
		if (lebsettingsLaso != null && !formLebSettingsAnlegen.isReadOnly()) {
			((FossaLaso) lebsettingsLaso).unlock();
		}
	}

}

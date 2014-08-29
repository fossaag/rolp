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

package org.fossa.rolp.ui.fach;

import org.fossa.rolp.data.fach.FachLaso;
import org.fossa.rolp.data.fach.fachtyp.FachtypPojo;
import org.fossa.vaadin.FossaApplication;
import org.fossa.vaadin.laso.FossaLaso;
import org.fossa.vaadin.ui.FossaWindow;

import com.vaadin.ui.CustomLayout;

public class FaecherAnlegen extends FossaWindow {
	
	private static final long serialVersionUID = 8926780094364017007L;
	
	private FaecherAnlegenForm formFaecherAnlegen;
	private FachLaso fachLaso;

	public FaecherAnlegen(FossaApplication app, FachLaso fach, FachtypPojo fachtypPojo) {
		super(app);
		this.fachLaso = fach;
		setCaption(" - Fächerdaten- ");
		setWidth("500px");
		center();
		CustomLayout layout = new CustomLayout("./subWindows/formAnlegen");
		setContent(layout);
		
		getFaecherAnlegenForm(fachtypPojo);
		layout.addComponent(formFaecherAnlegen, "form");
	}
	
	private FaecherAnlegenForm getFaecherAnlegenForm(FachtypPojo fachtypPojo) {
		if (formFaecherAnlegen == null) {
			formFaecherAnlegen = new FaecherAnlegenForm(fachtypPojo);
			if (fachLaso.getId() == null) {
				formFaecherAnlegen.addTemporaryItem(fachLaso);
			} else {
				formFaecherAnlegen.setFaecherAnlegen(fachLaso);
			}
		}
		return formFaecherAnlegen;
	}
	
	@Override
	public void unlockLaso() {
		if (fachLaso != null && !formFaecherAnlegen.isReadOnly()) {
			((FossaLaso) fachLaso).unlock();
		}
	}
}

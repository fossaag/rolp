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

package org.fossa.rolp.ui.lehrer;

import org.fossa.rolp.data.lehrer.LehrerLaso;
import org.fossa.vaadin.FossaApplication;
import org.fossa.vaadin.laso.FossaLaso;
import org.fossa.vaadin.ui.FossaWindow;

import com.vaadin.ui.CustomLayout;

public class LehrerAnlegen extends FossaWindow {

	private static final long serialVersionUID = 4822532202966417337L;
	
	private LehrerAnlegenForm formLehrerAnlegen;
	private LehrerLaso lehrerLaso;

	public LehrerAnlegen(FossaApplication app, LehrerLaso lehrer) {
		super(app);
		this.lehrerLaso = lehrer;
		setCaption(" - Lehrerdaten - ");
		setWidth("500px");
		center();
		CustomLayout layout = new CustomLayout("./subWindows/formAnlegen");
		setContent(layout);
		
		getLehrerAnlegenForm();
		layout.addComponent(formLehrerAnlegen, "form");
	}

	private LehrerAnlegenForm getLehrerAnlegenForm() {
		if (formLehrerAnlegen == null) {
			formLehrerAnlegen = new LehrerAnlegenForm();
			if (lehrerLaso.getId() == null) {
				formLehrerAnlegen.addTemporaryItem(lehrerLaso);
			} else {
				formLehrerAnlegen.setLehrerAnlegen(lehrerLaso);
			}
		}
		return formLehrerAnlegen;
	}

	@Override
	public void unlockLaso() {
		if (lehrerLaso != null && !formLehrerAnlegen.isReadOnly()) {
			((FossaLaso) lehrerLaso).unlock();
		}
	}

}

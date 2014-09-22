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

package org.fossa.rolp.ui.schueler;

import org.fossa.rolp.data.schueler.SchuelerLaso;
import org.fossa.vaadin.FossaApplication;
import org.fossa.vaadin.laso.FossaLaso;
import org.fossa.vaadin.ui.FossaWindow;

import com.vaadin.ui.CustomLayout;

public class SchuelerVerwalten extends FossaWindow {

	private static final long serialVersionUID = -6514418795515349474L;

	private SchuelerVerwaltenForm formSchuelerAnlegen;
	private SchuelerLaso schuelerLaso;

	public SchuelerVerwalten(FossaApplication app, SchuelerLaso schueler) {
		super(app);
		this.schuelerLaso = schueler;
		setCaption(" - Schülerdaten - ");
		setWidth("500px");
		center();
		CustomLayout layout = new CustomLayout("./subWindows/formAnlegen");
		setContent(layout);
		
		getSchuelerAnlegenForm();
		layout.addComponent(formSchuelerAnlegen, "form");
	}

	private SchuelerVerwaltenForm getSchuelerAnlegenForm() {
		if (formSchuelerAnlegen == null) {
			formSchuelerAnlegen = new SchuelerVerwaltenForm();
			if (schuelerLaso.getId() == null) {
				formSchuelerAnlegen.addTemporaryItem(schuelerLaso);
			} else {
				formSchuelerAnlegen.setSchuelerAnlegen(schuelerLaso);
			}
		}
		return formSchuelerAnlegen;
	}

	@Override
	public void unlockLaso() {
		if (schuelerLaso != null && !formSchuelerAnlegen.isReadOnly()) {
			((FossaLaso) schuelerLaso).unlock();
		}
	}

}

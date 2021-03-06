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

import org.fossa.rolp.data.schueler.SchuelerLaso;
import org.fossa.vaadin.FossaApplication;
import org.fossa.vaadin.laso.FossaLaso;
import org.fossa.vaadin.ui.FossaWindow;

import com.vaadin.ui.CustomLayout;

public class VersetzungsvermerkBearbeiten extends FossaWindow {

	private static final long serialVersionUID = -2270937782070392437L;
	
	private VersetzungsvermerkBearbeitenForm formVersetzungsvermerkBearbeiten;
	private SchuelerLaso schuelerLaso;

	public VersetzungsvermerkBearbeiten(FossaApplication app, SchuelerLaso schueler) {
		super(app);
		this.schuelerLaso = schueler;
		setCaption(" - Versetzungsvermerk - ");
		setWidth("500px");
		center();
		CustomLayout layout = new CustomLayout("./subWindows/formAnlegen");
		setContent(layout);
		
		getVersetzungsvermerkBearbeitenForm();
		layout.addComponent(formVersetzungsvermerkBearbeiten, "form");
	}

	private VersetzungsvermerkBearbeitenForm getVersetzungsvermerkBearbeitenForm() {
		if (formVersetzungsvermerkBearbeiten == null) {
			formVersetzungsvermerkBearbeiten = new VersetzungsvermerkBearbeitenForm();
			if (schuelerLaso.getId() == null) {
				formVersetzungsvermerkBearbeiten.addTemporaryItem(schuelerLaso);
			} else {
				formVersetzungsvermerkBearbeiten.setVersetzungsvermerkBearbeiten(schuelerLaso);
			}
		}
		return formVersetzungsvermerkBearbeiten;
	}

	@Override
	public void unlockLaso() {
		if (schuelerLaso != null && !formVersetzungsvermerkBearbeiten.isReadOnly()) {
			((FossaLaso) schuelerLaso).unlock();
		}
	}

}

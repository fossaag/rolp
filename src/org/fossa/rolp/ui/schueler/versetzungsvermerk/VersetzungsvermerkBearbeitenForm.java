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

import org.fossa.rolp.data.schueler.SchuelerContainer;
import org.fossa.rolp.data.schueler.SchuelerLaso;
import org.fossa.rolp.ui.schueler.SchuelerFormFields;
import org.fossa.vaadin.laso.FossaLaso;
import org.fossa.vaadin.ui.FossaForm;
import org.fossa.vaadin.ui.exception.FossaFormInvalidException;

import com.vaadin.data.Item;
import com.vaadin.ui.Button.ClickListener;

public class VersetzungsvermerkBearbeitenForm extends FossaForm implements ClickListener {

	private static final long serialVersionUID = 1807469910828502256L;

	public VersetzungsvermerkBearbeitenForm() {
		super();
		setFormFieldFactory(new SchuelerFormFields());
	}
	
	@Override
	public void setItemDataSource(Item newDataSource) {
		super.setItemDataSource(newDataSource,SchuelerContainer.VERSETZUNGSVERMERK_FORM_ORDER );
	}
	
	@Override
	public void saveFossaForm() throws FossaFormInvalidException {
		super.saveFossaForm();
		closeWindow();
	}

	public void addTemporaryItem(SchuelerLaso schuelerLaso) {
		super.addTemporaryItem(schuelerLaso);
	}
	
	public void setVersetzungsvermerkBearbeiten(SchuelerLaso schuelerLaso){
		super.setFossaLaso(schuelerLaso);
	}

	@Override
	public Item addItemToContainer(FossaLaso fossaLaso) {
		return SchuelerContainer.getInstance().addItem(fossaLaso);
	}
}
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

package org.fossa.rolp.ui.fach.fachdefinition;

import org.fossa.rolp.data.fach.FachContainer;
import org.fossa.rolp.data.fach.FachLaso;
import org.fossa.rolp.data.fach.fachdefinition.FachdefinitionContainer;
import org.fossa.rolp.data.fach.fachdefinition.FachdefinitionLaso;
import org.fossa.rolp.data.fach.fachdefinition.FachdefinitionPojo;
import org.fossa.rolp.data.fach.fachtyp.FachtypPojo;
import org.fossa.vaadin.laso.FossaLaso;
import org.fossa.vaadin.ui.FossaForm;
import org.fossa.vaadin.ui.exception.FossaFormInvalidException;

import com.vaadin.data.Item;
import com.vaadin.ui.Button.ClickListener;

public class FachdefinitionenVerwaltenForm extends FossaForm implements ClickListener {

	private static final long serialVersionUID = -8258009901116696640L;

	public FachdefinitionenVerwaltenForm() {
		super();
		setFormFieldFactory(new FachdefinitionenVerwaltenFormFields());
	}
	
	@Override
	public void setItemDataSource(Item newDataSource) {
		super.setItemDataSource(newDataSource,FachdefinitionContainer.NATURAL_FORM_ORDER );
	}
	
	@Override
	public void saveFossaForm() throws FossaFormInvalidException {
		boolean neueFachdefinition = (fossaLaso.getId() == null);
		FachdefinitionLaso fachdefinition = (FachdefinitionLaso) fossaLaso;
		String fachbezeichnung = (String) getField(FachdefinitionPojo.FACHBEZEICHNUNG_COLUMN).getValue();
		if (!neueFachdefinition && changeNotAllowed()) {
			throw new FossaFormInvalidException("Fachtyp kann nicht geändert werden. Die Fachdefinition wird bereits verwendet.");
		}
		for (FachdefinitionLaso aFachdefinition : FachdefinitionContainer.getInstance().getItemIds()) {
			if (aFachdefinition.getFachbezeichnung().equals(fachbezeichnung)) {
				if (neueFachdefinition || (!neueFachdefinition && !fachdefinition.getId().equals(aFachdefinition.getId()))) {
					throw new FossaFormInvalidException("Eine Fachdefinition mit gleicher Bezeichnung ist bereits vorhanden.");
				}
			}
		}
		super.saveFossaForm();
		closeWindow();
	}
	
	private boolean changeNotAllowed() {
		FachdefinitionLaso fachdefinitionLaso = (FachdefinitionLaso) fossaLaso;
		FachtypPojo fachtyp = (FachtypPojo) getField(FachdefinitionPojo.FACHTYP_COLUMN).getValue();
		if (!fachdefinitionLaso.getFachtyp().getId().equals(fachtyp.getId())) {
			for (FachLaso fach : FachContainer.getInstance().getItemIds()) {
				if (fachdefinitionLaso.getId().equals(fach.getFachdefinition().getId())) {
					return true;
				}
			}
		}
		return false;
	}

	public void addTemporaryItem(FachdefinitionLaso fachdefinitionLaso) {
		super.addTemporaryItem(fachdefinitionLaso);
	}
	
	public void setFachdefinition(FachdefinitionLaso fachdefinitionLaso){
		super.setFossaLaso(fachdefinitionLaso);
	}

	@Override
	public Item addItemToContainer(FossaLaso fossaLaso) {
		return FachdefinitionContainer.getInstance().addItem(fossaLaso);
	}
}
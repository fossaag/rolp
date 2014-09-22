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

package org.fossa.rolp.ui.fach.fachdefinition.leb;

import org.fossa.rolp.data.fach.fachdefinition.FachdefinitionPojo;
import org.fossa.rolp.data.fach.fachdefinition.leb.FachbezeichnungLebContainer;
import org.fossa.rolp.data.fach.fachdefinition.leb.FachbezeichnungLebLaso;
import org.fossa.vaadin.laso.FossaLaso;
import org.fossa.vaadin.ui.FossaForm;
import org.fossa.vaadin.ui.exception.FossaFormInvalidException;

import com.vaadin.data.Item;
import com.vaadin.ui.Button.ClickListener;

public class FachbezeichnungLebVerwaltenForm extends FossaForm implements ClickListener {

	private static final long serialVersionUID = 5613132081324311142L;
	private FachdefinitionPojo fachdefinition;

	public FachbezeichnungLebVerwaltenForm(FachdefinitionPojo fachdefinition) {
		super();
		this.fachdefinition = fachdefinition;
		setFormFieldFactory(new FachbezeichnungLebVerwaltenFormFields());
	}
	
	@Override
	public void setItemDataSource(Item newDataSource) {
		super.setItemDataSource(newDataSource,FachbezeichnungLebContainer.NATURAL_FORM_ORDER );
	}
	
	@Override
	public void saveFossaForm() throws FossaFormInvalidException {
		super.saveFossaForm();
		FachbezeichnungLebLaso fachdefinitionLebLaso = (FachbezeichnungLebLaso) fossaLaso;
		fachdefinitionLebLaso.setFachdefinition(fachdefinition);
		closeWindow();
	}

	public void addTemporaryItem(FachbezeichnungLebLaso fachdefinitionLebLaso) {
		super.addTemporaryItem(fachdefinitionLebLaso);
	}
	
	public void setFachdefinitionLeb(FachbezeichnungLebLaso fachdefinitionLebLaso){
		super.setFossaLaso(fachdefinitionLebLaso);
	}

	@Override
	public Item addItemToContainer(FossaLaso fossaLaso) {
		return FachbezeichnungLebContainer.getInstance().addItem(fossaLaso);
	}
}
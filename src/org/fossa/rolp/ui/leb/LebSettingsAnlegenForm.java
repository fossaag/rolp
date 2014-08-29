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

import java.util.Date;

import org.fossa.rolp.data.leb.LebSettingsContainer;
import org.fossa.rolp.data.leb.LebSettingsLaso;
import org.fossa.rolp.data.leb.LebSettingsPojo;
import org.fossa.rolp.util.DateUtil;
import org.fossa.vaadin.laso.FossaLaso;
import org.fossa.vaadin.ui.FossaForm;
import org.fossa.vaadin.ui.exception.FossaFormInvalidException;

import com.vaadin.data.Item;
import com.vaadin.ui.Button.ClickListener;

public class LebSettingsAnlegenForm extends FossaForm implements ClickListener {

	private static final long serialVersionUID = -3211906348302043211L;

	public LebSettingsAnlegenForm() {
		super();
		setFormFieldFactory(new LebSettingsAnlegenFormFields());
	}
	
	@Override
	public void setItemDataSource(Item newDataSource) {
		super.setItemDataSource(newDataSource, LebSettingsContainer.NATURAL_FORM_ORDER );
	}
	
	@Override
	public void saveFossaForm() throws FossaFormInvalidException {
		if (DateUtil.dateIsInvalid((Date) getField(LebSettingsPojo.ZEUGNISAUSGABEDATUM_COLUMN).getValue())) {
			throw new FossaFormInvalidException("Das 'Zeugnisausgabedatum' enthält kein gültiges Datum");
		}
		super.saveFossaForm();
		closeWindow();
	}

	public void addTemporaryItem(LebSettingsLaso lebsettingsLaso) {
		super.addTemporaryItem(lebsettingsLaso);
	}
	
	public void setLebSettingsAnlegen(LebSettingsLaso lebsettingsLaso){
		super.setFossaLaso(lebsettingsLaso);
	}

	@Override
	public Item addItemToContainer(FossaLaso fossaLaso) {
		return LebSettingsContainer.getInstance().addItem(fossaLaso);
	}
}
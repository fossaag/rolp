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

import org.fossa.rolp.data.lehrer.LehrerContainer;
import org.fossa.rolp.data.lehrer.LehrerLaso;
import org.fossa.rolp.util.PasswortEncryptUtil;
import org.fossa.vaadin.auth.data.FossaUserLaso;
import org.fossa.vaadin.auth.data.FossaUserPojo;
import org.fossa.vaadin.laso.FossaLaso;
import org.fossa.vaadin.ui.FossaForm;
import org.fossa.vaadin.ui.exception.FossaFormInvalidException;

import com.vaadin.data.Item;
import com.vaadin.ui.Button.ClickListener;

public class LehrerAnlegenForm extends FossaForm implements ClickListener {

	private static final long serialVersionUID = -2166176399738468588L;

	public LehrerAnlegenForm() {
		super();
		setFormFieldFactory(new LehrerAnlegenFormFields());
	}
	
	@Override
	public void setItemDataSource(Item newDataSource) {
		super.setItemDataSource(newDataSource,LehrerContainer.NATURAL_FORM_ORDER );
	}
	
	@Override
	public void saveFossaForm() throws FossaFormInvalidException {
		String username = ((String) getField(FossaUserPojo.USERNAME_COLUMN).getValue());	
		String password1 = (String) getField(FossaUserLaso.FORM_PW_ONE_COLUMN).getValue();
		String password2 = (String) getField(FossaUserLaso.FORM_PW_TWO_COLUMN).getValue();
		for(LehrerLaso lehrer : LehrerContainer.getInstance().getItemIds()) {
			if (lehrer.getUsername().equals(username) && lehrer.getId() != fossaLaso.getId()) {
				throw new FossaFormInvalidException("Es gibt bereits einen Lehrer mit diesem Benutzernamen.");
			}
		}
		if(!password1.equals(password2)){
			throw new FossaFormInvalidException(" Passwörter stimmen nicht überein");
		}		
		super.saveFossaForm();
		((LehrerLaso) fossaLaso).setPassword(PasswortEncryptUtil.encryptPassword(password1));
		closeWindow();
	}

	public void addTemporaryItem(LehrerLaso lehrerLaso) {
		super.addTemporaryItem(lehrerLaso);
	}
	
	public void setLehrerAnlegen(LehrerLaso lehrerLaso){
		super.setFossaLaso(lehrerLaso);
	}

	@Override
	public Item addItemToContainer(FossaLaso fossaLaso) {
		return LehrerContainer.getInstance().addItem(fossaLaso);
	}
}
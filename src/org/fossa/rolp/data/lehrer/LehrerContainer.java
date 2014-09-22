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

package org.fossa.rolp.data.lehrer;

import java.io.Serializable;

import org.fossa.vaadin.auth.data.FossaUserLaso;
import org.fossa.vaadin.auth.data.FossaUserPojo;
import org.fossa.vaadin.laso.FossaLaso;

import com.vaadin.data.util.BeanItemContainer;

public class LehrerContainer extends BeanItemContainer<LehrerLaso> implements Serializable {
		
	private static final long serialVersionUID = 2898013158025955616L;
	
	
	public static final String[] NATURAL_FORM_ORDER = new String[] {
		FossaUserPojo.FIRSTNAME_COLUMN,
		FossaUserPojo.LASTNAME_COLUMN,
		FossaUserPojo.USERNAME_COLUMN,
		FossaUserLaso.FORM_PW_ONE_COLUMN,
		FossaUserLaso.FORM_PW_TWO_COLUMN,
	};
	
	public static final String[] NATURAL_COL_ORDER = new String[] {
		FossaUserPojo.FIRSTNAME_COLUMN,
		FossaUserPojo.LASTNAME_COLUMN,
		FossaUserPojo.USERNAME_COLUMN,
	};
	
	public static final String[] COL_HEADERS = new String[] {
		"Vorname",
		"Name",
		"Benutzername",
	};
	
	private static LehrerContainer lehrerContainer;

	private LehrerContainer() {
		super(LehrerLaso.class);
	}
	
	public static LehrerContainer getInstance() {
		if (lehrerContainer == null) {
			lehrerContainer = new LehrerContainer();
			for (LehrerPojo lehrerPojo: LehrerLaso.getAll()) {
				LehrerLaso lehrerLaso = new LehrerLaso(lehrerPojo);
				lehrerContainer.addBean(lehrerLaso);
			}
			lehrerContainer.sort(new Object[] {FossaUserPojo.FIRSTNAME_COLUMN}, new boolean[] {true});
		}
		return lehrerContainer;
	}
		
	public static LehrerLaso getLehrerByUser(FossaUserLaso fossaUserLaso) {
		for (LehrerLaso aLehrer: getInstance().getItemIds()) {
			if (aLehrer.getPojo().getUser().getId().equals(fossaUserLaso.getId())) {
				return aLehrer;
			}
		}
		return null;
	}
	
	public void deleteLehrer(LehrerLaso lehrer) {
		FossaLaso.deleteIfExists(lehrer.getPojo());
		for (LehrerLaso aLehrer : getInstance().getItemIds()) {
			if (lehrer.getId().equals(aLehrer.getId())) {
				lehrerContainer.removeItem(aLehrer);
				return;
			}
		}
	}
}

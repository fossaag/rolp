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

package org.fossa.vaadin.auth.data;

import java.io.Serializable;

import org.fossa.rolp.data.lehrer.LehrerContainer;
import org.fossa.rolp.data.lehrer.LehrerLaso;

import com.vaadin.data.util.BeanItemContainer;

public class FossaUserContainer extends BeanItemContainer<FossaUserLaso> implements Serializable {
		
	private static final long serialVersionUID = -6434482851749615707L;
	
	private static FossaUserContainer userContainer;

	private FossaUserContainer() {
		super(FossaUserLaso.class);
	}
	
	public static FossaUserContainer getInstance() {
		if (userContainer == null) {
			userContainer = new FossaUserContainer();
		}
		userContainer.removeAllItems();
		for (LehrerLaso lehrer : LehrerContainer.getInstance().getItemIds()) {
			userContainer.addBean(lehrer.getUser());
		}
		return userContainer;
	}
}

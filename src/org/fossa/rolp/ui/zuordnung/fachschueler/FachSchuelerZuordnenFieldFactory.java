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

package org.fossa.rolp.ui.zuordnung.fachschueler;

import org.fossa.rolp.data.zuordnung.fachschueler.ZuordnungFachSchuelerContainer;
import org.fossa.rolp.data.zuordnung.fachschueler.ZuordnungFachSchuelerHandler;

import com.vaadin.data.Container;
import com.vaadin.ui.Component;
import com.vaadin.ui.DefaultFieldFactory;
import com.vaadin.ui.Field;

public class FachSchuelerZuordnenFieldFactory extends DefaultFieldFactory {

	private static final long serialVersionUID = -7593565853397459981L;
	
	@Override
	public Field createField(Container container, Object itemId, Object propertyId, Component uiContext) {
		Field field = super.createField(container.getItem(itemId), propertyId, uiContext);
		if (propertyId.equals(ZuordnungFachSchuelerHandler.ZUGEORDNET_COLUMN)) {
			field.setCaption(ZuordnungFachSchuelerContainer.ZUGEORDNET_CAPTION);
		}
		return field;
	}
}

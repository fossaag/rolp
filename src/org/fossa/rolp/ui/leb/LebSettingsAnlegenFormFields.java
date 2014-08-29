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

import org.fossa.rolp.data.klasse.halbjahr.HalbjahrPojo;
import org.fossa.rolp.data.klasse.halbjahr.HalbjahrPojoContainer;
import org.fossa.rolp.data.leb.LebSettingsPojo;

import com.vaadin.data.Item;
import com.vaadin.ui.Component;
import com.vaadin.ui.DefaultFieldFactory;
import com.vaadin.ui.Field;
import com.vaadin.ui.Select;

public class LebSettingsAnlegenFormFields extends DefaultFieldFactory {

	private static final long serialVersionUID = -7125663607032486479L;

	@Override
	public Field createField(Item item, Object propertyId, Component uiContext) {
		Field field = super.createField(item, propertyId, uiContext);
		if (propertyId.equals(LebSettingsPojo.HALBJAHR_COLUMN)) {
			Select select = new Select("Halbjahr: ");
			HalbjahrPojoContainer halbjahr = HalbjahrPojoContainer.getInstance();
			select.setContainerDataSource(halbjahr);
			select.setNullSelectionAllowed(false);
			select.setRequired(true);
			HalbjahrPojo currentHalbjahr = (HalbjahrPojo) item.getItemProperty(LebSettingsPojo.HALBJAHR_COLUMN).getValue();
			select.setPropertyDataSource(item.getItemProperty(propertyId));
			for (HalbjahrPojo halbjahrPojo : halbjahr.getItemIds()) {
				select.setItemCaption(halbjahrPojo, halbjahrPojo.getHalbjahr());
				if (currentHalbjahr != null && halbjahrPojo.getId().equals(currentHalbjahr.getId())) {
					select.select(halbjahrPojo);
				}
			}
			return select;
		}
		return field;
	}
}

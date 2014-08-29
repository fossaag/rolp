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

package org.fossa.rolp.ui.klasse;

import org.fossa.rolp.data.klasse.KlasseContainer;
import org.fossa.rolp.data.klasse.KlassePojo;
import org.fossa.rolp.data.lehrer.LehrerContainer;
import org.fossa.rolp.data.lehrer.LehrerLaso;
import org.fossa.rolp.data.lehrer.LehrerPojo;

import com.vaadin.data.Container;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.Component;
import com.vaadin.ui.DefaultFieldFactory;
import com.vaadin.ui.Field;
import com.vaadin.ui.Select;

public class KlassenlehrerZuordnenFieldFactory extends DefaultFieldFactory {

	private static final long serialVersionUID = -7593565853397459981L;
	
	@Override
	public Field createField(Container container, Object itemId, Object propertyId, Component uiContext) {
		Field field = super.createField(container.getItem(itemId), propertyId, uiContext);
		if (propertyId.equals(KlassePojo.KLASSENLEHRER_COLUMN)) {
			Select select = new Select(KlassePojo.KLASSENLEHRER_COLUMN);
			select.setNullSelectionAllowed(true);
			select.setRequired(false);
			BeanItemContainer<LehrerPojo> lehrerContainer = new BeanItemContainer<LehrerPojo>(LehrerPojo.class);
			LehrerPojo currentLehrer = (LehrerPojo) container.getItem(itemId).getItemProperty(KlassePojo.KLASSENLEHRER_COLUMN).getValue();
			if (currentLehrer != null) {
				lehrerContainer.addItem(currentLehrer);
			}
			for (LehrerLaso lehrer : LehrerContainer.getInstance().getItemIds()) {
				if (!lehrer.getPojo().getIsAdmin() && KlasseContainer.getKlasseByLehrer(lehrer.getPojo()) == null) {
					lehrerContainer.addItem(lehrer.getPojo());
				}
			}
			select.setContainerDataSource(lehrerContainer);			
			select.setPropertyDataSource(container.getItem(itemId).getItemProperty(propertyId));
			for (LehrerPojo lehrer : lehrerContainer.getItemIds()) {
				select.setItemCaption(lehrer, lehrer.getUser().getFirstname() + " " + lehrer.getUser().getLastname());
				if (currentLehrer != null && lehrer.getId().equals(currentLehrer.getId())) {
					select.select(lehrer);
				}
			}
			select.setImmediate(true);
			select.addListener((ValueChangeListener) uiContext);			
			return select;
			
		} else if (propertyId.equals(KlassePojo.KLASSENNAME_COLUMN)) {
			field.setReadOnly(true);
		}
		return field;
	}
}

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

import org.fossa.rolp.data.fach.fachdefinition.FachdefinitionPojo;
import org.fossa.rolp.data.fach.fachtyp.FachtypPojo;
import org.fossa.rolp.data.fach.fachtyp.FachtypPojoContainer;
import org.fossa.vaadin.ui.FossaSelect;

import com.vaadin.data.Item;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.Component;
import com.vaadin.ui.DefaultFieldFactory;
import com.vaadin.ui.Field;

public class FachdefinitionenVerwaltenFormFields extends DefaultFieldFactory {

	private static final long serialVersionUID = -3290849402977345024L;

	@Override
	public Field createField(Item item, Object propertyId, Component uiContext) {
		Field field = super.createField(item, propertyId, uiContext);
		if (propertyId.equals(FachdefinitionPojo.FACHBEZEICHNUNG_COLUMN)) {
			field.setRequired(true);
		} else if (propertyId.equals(FachdefinitionPojo.FACHTYP_COLUMN)) {
			FossaSelect select = new FossaSelect("Fachtyp: ");
			select.setNullSelectionAllowed(false);
			select.setRequired(true);
			BeanItemContainer<FachtypPojo> fachtypContainer = FachtypPojoContainer.getInstance();
			select.setContainerDataSource(fachtypContainer);
			FachtypPojo currentFachtyp = (FachtypPojo) item.getItemProperty(FachdefinitionPojo.FACHTYP_COLUMN).getValue();
			select.setPropertyDataSource(item.getItemProperty(propertyId));
			for (FachtypPojo fachtyp : fachtypContainer.getItemIds()) {
				select.setItemCaption(fachtyp, fachtyp.getFachtyp());
				if (currentFachtyp != null && fachtyp.getId().equals(currentFachtyp.getId())) {
					select.select(fachtyp);
				}
			}
			return select;
		}
		return field;
	}
}

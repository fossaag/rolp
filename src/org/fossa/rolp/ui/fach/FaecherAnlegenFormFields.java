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

package org.fossa.rolp.ui.fach;

import org.fossa.rolp.data.fach.FachContainer;
import org.fossa.rolp.data.fach.FachPojo;
import org.fossa.rolp.data.fach.fachdefinition.FachdefinitionContainer;
import org.fossa.rolp.data.fach.fachdefinition.FachdefinitionPojo;
import org.fossa.rolp.data.fach.fachtyp.FachtypPojo;
import org.fossa.rolp.data.lehrer.LehrerContainer;
import org.fossa.rolp.data.lehrer.LehrerLaso;
import org.fossa.rolp.data.lehrer.LehrerPojo;
import org.fossa.vaadin.ui.FossaSelect;

import com.vaadin.data.Item;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.Component;
import com.vaadin.ui.DefaultFieldFactory;
import com.vaadin.ui.Field;
import com.vaadin.ui.Select;

public class FaecherAnlegenFormFields extends DefaultFieldFactory {

	private static final long serialVersionUID = -7125663607032486479L;
	
	private FachtypPojo fachtyp;

	public FaecherAnlegenFormFields(FachtypPojo fachtyp) {
		super();
		this.fachtyp = fachtyp;
	}

	@Override
	public Field createField(Item item, Object propertyId, Component uiContext) {
		Field field = super.createField(item, propertyId, uiContext);
		if (propertyId.equals(FachPojo.FACHDEFINITION_COLUMN)) {
			FossaSelect select = new FossaSelect("Fachdefinition: ");
			select.setNullSelectionAllowed(false);
			select.setRequired(true);
			BeanItemContainer<FachdefinitionPojo> fachdefinitionenContainer = FachdefinitionContainer.getAllFaecherOfFachtyp(FachdefinitionContainer.getInstance(), fachtyp);
			select.setContainerDataSource(fachdefinitionenContainer);
			FachdefinitionPojo currentFachdefinition = (FachdefinitionPojo) item.getItemProperty(FachPojo.FACHDEFINITION_COLUMN).getValue();
			select.setPropertyDataSource(item.getItemProperty(propertyId));
			for (FachdefinitionPojo fachdefinition : fachdefinitionenContainer.getItemIds()) {
				select.setItemCaption(fachdefinition, fachdefinition.getFachbezeichnung());
				if (currentFachdefinition != null && fachdefinition.getId().equals(currentFachdefinition.getId())) {
					select.select(fachdefinition);
				}
			}
			return select;
		} else if (propertyId.equals(FachPojo.FACHLEHRER_EINS_COLUMN)) {
			Select select = new Select(FachContainer.FACHLEHRER_EINS_COLUMN_LABEL);
			select.setNullSelectionAllowed(false);
			select.setRequired(true);
			BeanItemContainer<LehrerPojo> lehrerContainer = new BeanItemContainer<LehrerPojo>(LehrerPojo.class);
			for (LehrerLaso lehrer : LehrerContainer.getInstance().getItemIds()) {
				if (!lehrer.getPojo().getIsAdmin()) {
					lehrerContainer.addItem(lehrer.getPojo());
				}
			}
			select.setContainerDataSource(lehrerContainer);
			LehrerPojo currentLehrer = (LehrerPojo) item.getItemProperty(FachPojo.FACHLEHRER_EINS_COLUMN).getValue();
			select.setPropertyDataSource(item.getItemProperty(propertyId));
			for (LehrerPojo lehrer : lehrerContainer.getItemIds()) {
				select.setItemCaption(lehrer, lehrer.getUser().getFirstname() + " " + lehrer.getUser().getLastname());
				if (currentLehrer != null && lehrer.getId().equals(currentLehrer.getId())) {
					select.select(lehrer);
				}
			}
			return select;
		} else if (propertyId.equals(FachPojo.FACHLEHRER_ZWEI_COLUMN)) {
			Select select = new Select(FachContainer.FACHLEHRER_ZWEI_COLUMN_LABEL);
			select.setNullSelectionAllowed(true);
			BeanItemContainer<LehrerPojo> lehrerContainer = new BeanItemContainer<LehrerPojo>(LehrerPojo.class);
			for (LehrerLaso lehrer : LehrerContainer.getInstance().getItemIds()) {
				if (!lehrer.getPojo().getIsAdmin()) {
					lehrerContainer.addItem(lehrer.getPojo());
				}
			}
			select.setContainerDataSource(lehrerContainer);
			LehrerPojo currentLehrer = (LehrerPojo) item.getItemProperty(FachPojo.FACHLEHRER_ZWEI_COLUMN).getValue();
			select.setPropertyDataSource(item.getItemProperty(propertyId));
			for (LehrerPojo lehrer : lehrerContainer.getItemIds()) {
				select.setItemCaption(lehrer, lehrer.getUser().getFirstname() + " " + lehrer.getUser().getLastname());
				if (currentLehrer != null && lehrer.getId().equals(currentLehrer.getId())) {
					select.select(lehrer);
				}
			}
			return select;
		} else if (propertyId.equals(FachPojo.FACHLEHRER_DREI_COLUMN)) {
			Select select = new Select(FachContainer.FACHLEHRER_DREI_COLUMN_LABEL);
			select.setNullSelectionAllowed(true);
			BeanItemContainer<LehrerPojo> lehrerContainer = new BeanItemContainer<LehrerPojo>(LehrerPojo.class);
			for (LehrerLaso lehrer : LehrerContainer.getInstance().getItemIds()) {
				if (!lehrer.getPojo().getIsAdmin()) {
					lehrerContainer.addItem(lehrer.getPojo());
				}
			}
			select.setContainerDataSource(lehrerContainer);
			LehrerPojo currentLehrer = (LehrerPojo) item.getItemProperty(FachPojo.FACHLEHRER_DREI_COLUMN).getValue();
			select.setPropertyDataSource(item.getItemProperty(propertyId));
			for (LehrerPojo lehrer : lehrerContainer.getItemIds()) {
				select.setItemCaption(lehrer, lehrer.getUser().getFirstname() + " " + lehrer.getUser().getLastname());
				if (currentLehrer != null && lehrer.getId().equals(currentLehrer.getId())) {
					select.select(lehrer);
				}
			}
			return select;
		} 
		return field;
			
	}
}

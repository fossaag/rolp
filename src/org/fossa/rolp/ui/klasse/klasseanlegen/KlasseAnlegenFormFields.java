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

package org.fossa.rolp.ui.klasse.klasseanlegen;

import org.fossa.rolp.data.klasse.KlassePojo;

import com.vaadin.data.Item;
import com.vaadin.data.validator.IntegerValidator;
import com.vaadin.ui.Component;
import com.vaadin.ui.DefaultFieldFactory;
import com.vaadin.ui.Field;

public class KlasseAnlegenFormFields extends DefaultFieldFactory {

	private static final long serialVersionUID = -7125663607032486479L;

	@Override
	public Field createField(Item item, Object propertyId, Component uiContext) {
		Field field = super.createField(item, propertyId, uiContext);
		if (propertyId.equals(KlassePojo.KLASSENNAME_COLUMN)) {
			field.setCaption("Klassenbezeichnung: ");
			field.setRequired(true);
		} else if (propertyId.equals(KlassePojo.ABGANGSJAHR_COLUMN)) {
			field.setCaption("Abgangsjahr: ");
			field.setRequired(true);
			field.addValidator(new IntegerValidator("Jahreszahl besteht aus 4 Zahlen"));
		} 
		return field;	
	}	
}

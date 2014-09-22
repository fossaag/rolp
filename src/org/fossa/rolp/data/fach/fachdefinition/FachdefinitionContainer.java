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

package org.fossa.rolp.data.fach.fachdefinition;

import java.io.Serializable;

import org.fossa.rolp.data.fach.fachtyp.FachtypPojo;
import org.fossa.vaadin.laso.FossaLaso;

import com.vaadin.data.util.BeanItemContainer;

public class FachdefinitionContainer extends BeanItemContainer<FachdefinitionLaso> implements Serializable {

	private static final long serialVersionUID = -6251806552306016249L;

	public static final Object[] NATURAL_COL_ORDER = new String[] {
		FachdefinitionPojo.FACHBEZEICHNUNG_COLUMN,
		FachdefinitionLaso.FACHTYP_COLUMN
	};
	
	public static final Object[] NATURAL_FORM_ORDER = new String[] {
		FachdefinitionPojo.FACHBEZEICHNUNG_COLUMN,
		FachdefinitionPojo.FACHTYP_COLUMN
	};

	public static final String[] COL_HEADERS = new String[] {
		FachdefinitionPojo.FACHBEZEICHNUNG_COLUMN,
		FachdefinitionPojo.FACHTYP_COLUMN
	};
	
	private static FachdefinitionContainer fachdefinitionContainer;

	private FachdefinitionContainer() {
		super(FachdefinitionLaso.class);
	}

	public static FachdefinitionContainer getInstance() {
		if (fachdefinitionContainer == null) {
			fachdefinitionContainer = new FachdefinitionContainer();
			for (FachdefinitionPojo fachdefinitionPojo: FachdefinitionLaso.getAll()) {
				FachdefinitionLaso fachdefinitionLaso = new FachdefinitionLaso(fachdefinitionPojo);
				fachdefinitionContainer.addBean(fachdefinitionLaso);
			}
		}
		return fachdefinitionContainer;
	}
	
	public static BeanItemContainer<FachdefinitionLaso> getSortedFachdefinitionen() {
		BeanItemContainer<FachdefinitionLaso> fachdefinitionen = new BeanItemContainer<FachdefinitionLaso>(FachdefinitionLaso.class);
		fachdefinitionen.addAll(getInstance().getItemIds());
		fachdefinitionen.sort(new Object[]{FachdefinitionLaso.FACHTYP_COLUMN, FachdefinitionPojo.FACHBEZEICHNUNG_COLUMN}, new boolean[] {false, true});
		return fachdefinitionen;
	}
	
	public static BeanItemContainer<FachdefinitionPojo> getAllFaecherOfFachtyp(BeanItemContainer<FachdefinitionLaso> fachdefinitionen, FachtypPojo fachtyp) {
		BeanItemContainer<FachdefinitionPojo> filteredFaecher = new BeanItemContainer<FachdefinitionPojo>(FachdefinitionPojo.class);
		for (FachdefinitionLaso fachdefinition : fachdefinitionen.getItemIds()) {
			if (fachdefinition.getFachtyp().getId().equals(fachtyp.getId())) {
				filteredFaecher.addBean(fachdefinition.getPojo());				
			}
		}
		return filteredFaecher;
	}

	public void deleteFachdefinition(FachdefinitionLaso fachdefinition) {
		FossaLaso.deleteIfExists(fachdefinition.getPojo());
		for (FachdefinitionLaso aFachdefinition : getInstance().getItemIds()) {
			if (fachdefinition.getId().equals(aFachdefinition.getId())) {
				fachdefinitionContainer.removeItem(aFachdefinition);
				return;
			}
		}
	}
}
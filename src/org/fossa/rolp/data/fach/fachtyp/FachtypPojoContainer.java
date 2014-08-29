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

package org.fossa.rolp.data.fach.fachtyp;

import java.io.Serializable;
import java.util.Collection;

import org.fossa.vaadin.laso.FossaLaso;

import com.vaadin.data.util.BeanItemContainer;

public class FachtypPojoContainer extends BeanItemContainer<FachtypPojo> implements Serializable {

	private static final long serialVersionUID = 4807992123610814046L;
	
	private static FachtypPojoContainer fachtypContainer;

	@SuppressWarnings("unchecked")
	private FachtypPojoContainer() {
		super(FachtypPojo.class);
		addAll((Collection<? extends FachtypPojo>) FossaLaso.getAll(FachtypPojo.class));
	}

	public static FachtypPojoContainer getInstance() {
		if (fachtypContainer == null) {
			fachtypContainer = new FachtypPojoContainer();
		}
		return fachtypContainer;
	}
	
	public FachtypPojo getPflichtfach() {
		for (FachtypPojo fachtyp: fachtypContainer.getItemIds()) {			
			if (fachtyp.isPflichtfach()) {
				return fachtyp;
			}
		}
		return null;
	}

	public FachtypPojo getKurs() {
		for (FachtypPojo fachtyp: fachtypContainer.getItemIds()) {			
			if (fachtyp.isKurs()) {
				return fachtyp;
			}
		}
		return null;
	}

}

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

import org.fossa.rolp.data.fach.fachdefinition.FachdefinitionContainer;
import org.fossa.rolp.data.fach.fachdefinition.FachdefinitionLaso;

import com.vaadin.ui.Table;

public class FachdefinitionList extends Table {
	
	private static final long serialVersionUID = 6468038405927881479L;

	public FachdefinitionList() {
		buildContainer();
		initialize();
	}
	
	public void initialize() {
		setSizeFull();
		setPageLength(10);
		setVisibleColumns(FachdefinitionContainer.NATURAL_COL_ORDER);
		setColumnHeaders(FachdefinitionContainer.COL_HEADERS);
		setSelectable(true);
		setImmediate(true);
		setNullSelectionAllowed(false);
		setColumnCollapsingAllowed(true);
		setColumnReorderingAllowed(true);
	}
	
	public void refresh() {
		FachdefinitionLaso fachdefinition = (FachdefinitionLaso) getValue();
		buildContainer();		
		initialize();
		if (fachdefinition != null) {
			for (Object object : getContainerDataSource().getItemIds()) {
				FachdefinitionLaso aFachdefinition = (FachdefinitionLaso) object;
				if (aFachdefinition.getId().equals(fachdefinition.getId())) {
					setValue(aFachdefinition);
				}
			}		
		}
	}
	
	private void buildContainer() {
		setContainerDataSource(FachdefinitionContainer.getSortedFachdefinitionen());
	}
}

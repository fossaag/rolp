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

package org.fossa.rolp.ui.lehrer;

import org.fossa.rolp.data.lehrer.LehrerContainer;
import org.fossa.rolp.data.lehrer.LehrerLaso;
import org.fossa.vaadin.FossaApplication;

import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.Table;

public class LehrerList extends Table {

	private static final long serialVersionUID = -3494379310773011692L;

	public LehrerList(FossaApplication app) {
		buildContainer();
		initialize();
	}
	
	private void buildContainer() {
		BeanItemContainer<LehrerLaso> lehrerOnlyContainer = new BeanItemContainer<LehrerLaso>(LehrerLaso.class);		
		for (LehrerLaso lehrer: LehrerContainer.getInstance().getItemIds()) {
			if (!lehrer.getPojo().getIsAdmin()) {
				lehrerOnlyContainer.addBean(lehrer);
			}
		}
		setContainerDataSource(lehrerOnlyContainer);
	}

	public void initialize() {
		setSizeFull();
		setPageLength(8);
		setVisibleColumns(LehrerContainer.NATURAL_COL_ORDER);
		setColumnHeaders(LehrerContainer.COL_HEADERS);
		setSelectable(true);
		setImmediate(true);
		setNullSelectionAllowed(false);
		setColumnCollapsingAllowed(true);
		setColumnReorderingAllowed(true);
	}
	public void refresh() {
		LehrerLaso lehrer = (LehrerLaso) getValue();
		buildContainer();
		initialize();
		if (lehrer != null) {
			for (Object object : getContainerDataSource().getItemIds()) {
				LehrerLaso aLehrer = (LehrerLaso) object;
				if (aLehrer.getId().equals(lehrer.getId())) {
					setValue(aLehrer);
				}
			}		
		}
	}

}
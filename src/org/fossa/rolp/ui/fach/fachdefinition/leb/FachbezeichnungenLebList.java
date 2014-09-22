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

package org.fossa.rolp.ui.fach.fachdefinition.leb;

import org.fossa.rolp.data.fach.fachdefinition.leb.FachbezeichnungLebContainer;
import org.fossa.rolp.data.fach.fachdefinition.leb.FachbezeichnungLebLaso;

import com.vaadin.ui.Table;

public class FachbezeichnungenLebList extends Table {
	
	private static final long serialVersionUID = -6412982003872466868L;

	public FachbezeichnungenLebList(FachbezeichnungenLeblisteAnzeigen fachbezeichnungenLeblisteAnzeigen) {
		buildContainer(fachbezeichnungenLeblisteAnzeigen);
		initialize();
	}
	
	public void initialize() {
		setSizeFull();
		setPageLength(10);
		setVisibleColumns(FachbezeichnungLebContainer.NATURAL_COL_ORDER);
		setColumnHeaders(FachbezeichnungLebContainer.COL_HEADERS);
		setSelectable(true);
		setImmediate(true);
		setNullSelectionAllowed(false);
		setColumnCollapsingAllowed(true);
		setColumnReorderingAllowed(true);
	}
	
	public void refresh(FachbezeichnungenLeblisteAnzeigen fachbezeichnungenLeblisteAnzeigen) {
		FachbezeichnungLebLaso fachbezeichnungenleb = (FachbezeichnungLebLaso) getValue();
		buildContainer(fachbezeichnungenLeblisteAnzeigen);		
		initialize();
		if (fachbezeichnungenleb != null) {
			for (Object object : getContainerDataSource().getItemIds()) {
				FachbezeichnungLebLaso aFachbezeichnungenleb = (FachbezeichnungLebLaso) object;
				if (aFachbezeichnungenleb.getId().equals(fachbezeichnungenleb.getId())) {
					setValue(aFachbezeichnungenleb);
				}
			}		
		}
	}
	
	private void buildContainer(FachbezeichnungenLeblisteAnzeigen fachbezeichnungenLeblisteAnzeigen) {
		setContainerDataSource(FachbezeichnungLebContainer.getFachbezeichnungenLebOfFachdefinition(fachbezeichnungenLeblisteAnzeigen.getFachdefinition().getPojo()));
	}
}

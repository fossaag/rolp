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

package org.fossa.rolp.ui.schueler;

import org.fossa.rolp.RolpApplication;
import org.fossa.rolp.data.fach.FachLaso;
import org.fossa.rolp.data.zuordnung.fachschueler.ZuordnungFachSchuelerContainer;
import org.fossa.rolp.data.zuordnung.fachschueler.ZuordnungFachSchuelerLaso;
import org.fossa.vaadin.ui.FossaBooleanCellImageHandler;

import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.Table;

public class FachschuelerList extends Table {
	
	private static final long serialVersionUID = 161416954040090870L;
	private RolpApplication app;
	private FachLaso fach;
	
	public FachschuelerList(RolpApplication app, FachLaso fach) {
		this.app = app;
		this.fach = fach;
		buildContainer(app);
		addGeneratedColumn(ZuordnungFachSchuelerContainer.DYNAMIC_GENERATED_COLUMN_ERLEDIGT, new ColumnGenerator() {

			private static final long serialVersionUID = 592218601145322566L;

			public Object generateCell(Table source, Object itemId, Object columnId) {				
				ZuordnungFachSchuelerLaso zuordnungFS = (ZuordnungFachSchuelerLaso) itemId;
				return new FossaBooleanCellImageHandler(zuordnungFS.getErledigt());
			}
		});
		initialize();
	}
	
	private void buildContainer(RolpApplication app) {
		BeanItemContainer<ZuordnungFachSchuelerLaso> fachschuelerContainer = ZuordnungFachSchuelerContainer.getAllZuordnungenFachSchuelerOfFach(fach.getPojo());
		setContainerDataSource(fachschuelerContainer);
	}

	public void initialize() {
		setSizeFull();
		setPageLength(20);
		setVisibleColumns(ZuordnungFachSchuelerContainer.FACHSCHUELERLIST_COL_ORDER);
		setSelectable(true);
		setImmediate(true);
		setNullSelectionAllowed(false);
		setColumnCollapsingAllowed(true);
		setColumnReorderingAllowed(true);
		setEditable(false);
	}
	
	public void refresh() {
		ZuordnungFachSchuelerLaso zuordnungFS = (ZuordnungFachSchuelerLaso) getValue();
		buildContainer(app);		
		initialize();
		if (zuordnungFS != null) {
			for (Object object : getContainerDataSource().getItemIds()) {
				ZuordnungFachSchuelerLaso aZuordnungFS = (ZuordnungFachSchuelerLaso) object;
				if (aZuordnungFS.getId().equals(zuordnungFS.getId())) {
					setValue(aZuordnungFS);
				}
			}		
		}
	}
}

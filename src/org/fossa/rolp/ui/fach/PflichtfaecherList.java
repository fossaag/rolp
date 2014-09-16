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

import org.fossa.rolp.RolpApplication;
import org.fossa.rolp.data.fach.FachContainer;
import org.fossa.rolp.data.fach.FachLaso;
import org.fossa.rolp.data.klasse.KlasseContainer;
import org.fossa.rolp.data.klasse.KlasseLaso;
import org.fossa.rolp.data.lehrer.LehrerPojo;
import org.fossa.rolp.data.zuordnung.fachschueler.ZuordnungFachSchuelerContainer;

import com.vaadin.event.ItemClickEvent.ItemClickListener;
import com.vaadin.ui.Table;

public class PflichtfaecherList extends Table {
	
	private static final long serialVersionUID = 4729454005664382006L;
	private RolpApplication app;

	public PflichtfaecherList(RolpApplication app, PflichtfaecherlisteAnzeigen pflichtfaecherlisteAnzeigen) {
		this.app = app;
		buildContainer();
		initialize();
		addListener((ItemClickListener) pflichtfaecherlisteAnzeigen);
	}
	
	public void initialize() {
		setSizeFull();
		setPageLength(10);
		setVisibleColumns(FachContainer.NATURAL_PFLICHTFACH_ORDER);
		setColumnHeaders(FachContainer.PFLICHTFACH_HEADERS);
		setSelectable(true);
		setImmediate(true);
		setNullSelectionAllowed(false);
		setColumnCollapsingAllowed(true);
		setColumnReorderingAllowed(true);
	}
	
	public void refresh() {
		FachLaso fach = (FachLaso) getValue();
		buildContainer();		
		initialize();
		if (fach != null) {
			for (Object object : getContainerDataSource().getItemIds()) {
				FachLaso aFach = (FachLaso) object;
				if (aFach.getId().equals(fach.getId())) {
					setValue(aFach);
				}
			}		
		}
	}
	
	private void buildContainer() {
		LehrerPojo klassenlehrer =  app.getLoginLehrer();
		KlasseLaso klasse = KlasseContainer.getKlasseByLehrer(klassenlehrer);
		setContainerDataSource(ZuordnungFachSchuelerContainer.getAllPflichtfaecherOfKlasse(klasse.getPojo()));
	}
}

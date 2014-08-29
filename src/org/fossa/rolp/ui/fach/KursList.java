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
import org.fossa.rolp.data.schueler.SchuelerPojo;
import org.fossa.rolp.data.zuordnung.fachschueler.ZuordnungFachSchuelerContainer;

import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.event.ItemClickEvent.ItemClickListener;
import com.vaadin.ui.Table;

public class KursList extends Table {
	
	private static final long serialVersionUID = 4729454005664382006L;
	
	private RolpApplication app;

	public KursList(KurseZuordnen kurseZuordnen) {
		this.app = kurseZuordnen.app;
		buildContainer();
		addGeneratedColumn(FachContainer.DYNAMIC_GENERATED_COLUMN_ZUGEWIESENE_SCHUELER_DER_KLASSE, new ColumnGenerator() {

			private static final long serialVersionUID = 3266221651368747559L;

			public Object generateCell(Table source, Object itemId, Object columnId) {
				KlasseLaso klasse = KlasseContainer.getKlasseByLehrer(app.getLoginLehrer());
				if (klasse == null) {
					return 0;
				}
				FachLaso fach = (FachLaso) itemId;
				BeanItemContainer<SchuelerPojo> relevanteSchueler = new BeanItemContainer<SchuelerPojo>(SchuelerPojo.class); 
				for (SchuelerPojo schueler: ZuordnungFachSchuelerContainer.getAllSchuelerOfFach(fach.getPojo()).getItemIds()) {
					if (schueler.getKlasse().getId().equals(klasse.getId())) {
						relevanteSchueler.addBean(schueler);
					}
				}
				return relevanteSchueler.size();
			}
		});
		initialize();
		addListener((ItemClickListener) kurseZuordnen);
	}
	
	public void initialize() {
		setSizeFull();
		setPageLength(10);
		setVisibleColumns(FachContainer.NATURAL_KURS_ORDER);
		setColumnHeaders(FachContainer.KURS_HEADERS);
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
		setContainerDataSource(FachContainer.getAllKurse());
	}
}

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

import org.fossa.rolp.RolpApplication;
import org.fossa.rolp.data.klasse.KlasseContainer;
import org.fossa.rolp.data.klasse.KlasseLaso;
import org.fossa.vaadin.ui.FossaWindow;

import com.vaadin.event.ItemClickEvent.ItemClickListener;
import com.vaadin.ui.Table;

public class KlassenList extends Table {

	private static final long serialVersionUID = 3449398116384342438L;
	
	private RolpApplication app;
	private String[] naturalColOrder;
	private String[] colHeaders;
	
	public KlassenList(RolpApplication app, FossaWindow window, String[] naturalColOrder, String[] colHeaders) {
		this.naturalColOrder = naturalColOrder;
		this.colHeaders = colHeaders;
		this.app = app;
		buildContainer(app);
		initialize();
		addListener((ItemClickListener) window);
	}
	
	private void buildContainer(RolpApplication app) {
		setContainerDataSource(KlasseContainer.getAlleKlassen(KlasseContainer.getInstance()));
	}

	public void initialize() {
		setSizeFull();
		setPageLength(8);
		setVisibleColumns(naturalColOrder);
		setColumnHeaders(colHeaders);
		setSelectable(true);
		setImmediate(true);
		setNullSelectionAllowed(false);
		setColumnCollapsingAllowed(true);
		setColumnReorderingAllowed(true);
	}
	
	public void refresh() {
		KlasseLaso klasse = (KlasseLaso) getValue();
		buildContainer(app);
		initialize();
		if (klasse != null) {
			for (Object object : getContainerDataSource().getItemIds()) {
				KlasseLaso aKlasse = (KlasseLaso) object;
				if (aKlasse.getId().equals(klasse.getId())) {
					setValue(aKlasse);
				}
			}		
		}
	}
}

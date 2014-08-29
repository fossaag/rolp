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
import org.fossa.rolp.data.klasse.KlasseContainer;
import org.fossa.rolp.data.klasse.KlasseLaso;
import org.fossa.rolp.data.klasse.KlassePojo;
import org.fossa.rolp.data.schueler.SchuelerContainer;
import org.fossa.rolp.data.schueler.SchuelerLaso;
import org.fossa.vaadin.ui.FossaBooleanCellImageHandler;
import org.fossa.vaadin.ui.FossaWindow;

import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.event.ItemClickEvent.ItemClickListener;
import com.vaadin.ui.Table;

public class SchuelerList extends Table {
	
	private static final long serialVersionUID = 161416954040090870L;
	private RolpApplication app;
	private String[] naturalColOrder;
	private String[] colHeaders;
	
	public SchuelerList(RolpApplication app, FossaWindow window, String[] naturalColOrder, String[] colHeaders) {
		this.naturalColOrder = naturalColOrder;
		this.colHeaders = colHeaders;
		this.app = app;
		buildContainer(app);
		addGeneratedColumn(SchuelerContainer.DYNAMIC_GENERATED_COLUMN_SCHUELEREINSCHAETZUNG, new ColumnGenerator() {

			private static final long serialVersionUID = -2622728050430251945L;

			public Object generateCell(Table source, Object itemId, Object columnId) {				
				SchuelerLaso schuelerLaso = (SchuelerLaso) itemId;
				return new FossaBooleanCellImageHandler(schuelerLaso.getErledigt());
			}
		});
		
		addGeneratedColumn(SchuelerContainer.DYNAMIC_GENERATED_COLUMN_FACHEINSCHAETZUNG, new ColumnGenerator() {

			private static final long serialVersionUID = -6082867243178582792L;

			public Object generateCell(Table source, Object itemId, Object columnId) {				
				SchuelerLaso schuelerLaso = (SchuelerLaso) itemId;
				return new FossaBooleanCellImageHandler(schuelerLaso.getFacheinschaetzungenErledigt());
			}
		});
		
		initialize();
		addListener((ItemClickListener) window);
	}
	
	private void buildContainer(RolpApplication app) {
		KlasseLaso klasse = KlasseContainer.getKlasseByLehrer(app.getLoginLehrer());
		BeanItemContainer<SchuelerLaso> unsortedContainer;
		if (klasse != null) {
			unsortedContainer = SchuelerContainer.getAllSchuelerOfKlasse(klasse.getPojo());
		} else {
			unsortedContainer = new BeanItemContainer<SchuelerLaso>(SchuelerLaso.class);
		}
		setContainerDataSource(SchuelerContainer.sortContainer(unsortedContainer));
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
	
	public void refresh(KlassePojo klasse) {
		SchuelerLaso schueler = (SchuelerLaso) getValue();
		buildContainer(app);
		initialize();
		if (schueler != null) {
			for (Object object : getContainerDataSource().getItemIds()) {
				SchuelerLaso aSchueler = (SchuelerLaso) object;
				if (aSchueler.getId().equals(schueler.getId())) {
					setValue(aSchueler);
				}
			}		
		}
	}
}

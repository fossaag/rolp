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

package org.fossa.rolp.ui.klasse;

import org.fossa.rolp.data.klasse.KlasseContainer;
import org.fossa.rolp.data.lehrer.LehrerContainer;
import org.fossa.vaadin.auth.data.FossaUserContainer;
import org.fossa.vaadin.auth.data.FossaUserLaso;
import org.fossa.vaadin.ui.exception.FossaLasoLockedException;

import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.ui.Table;

public class KlassenlehrerZuordnenList extends Table implements ValueChangeListener {

	private static final long serialVersionUID = 8217399612578369441L;

	public KlassenlehrerZuordnenList() throws FossaLasoLockedException {
		throwExceptionIfLasosUnavailable();
		buildContainer();
		initialize();
	}
	
	private void throwExceptionIfLasosUnavailable() throws FossaLasoLockedException {
		for (FossaUserLaso user: FossaUserContainer.getInstance().getItemIds()) {
			if (user.isLocked() && !LehrerContainer.getLehrerByUser(user).getPojo().getIsAdmin()) {				
				throw new FossaLasoLockedException();
			}			
		}
	}

	public void initialize() {
		setSizeFull();
		setPageLength(KlasseContainer.getInstance().size());
		setVisibleColumns(KlasseContainer.KLASSENLEHRER_ORDER);
//		setColumnHeaders(KlasseContainer.FACH_ZUORDNEN_COL_HEADERS);
		setSelectable(false);
		setImmediate(false);
		setNullSelectionAllowed(false);
		setColumnCollapsingAllowed(true);
		setColumnReorderingAllowed(true);
		setTableFieldFactory(new KlassenlehrerZuordnenFieldFactory());
		setEditable(true);		
	}
	
	public void refresh() {
		buildContainer();
		initialize();
	}
	
	private void buildContainer() {
		for (FossaUserLaso user: FossaUserContainer.getInstance().getItemIds()) {
			user.lock();			
		}
		setContainerDataSource(KlasseContainer.getInstance());
	}

	public void unlockLasos() {
		for (FossaUserLaso user: FossaUserContainer.getInstance().getItemIds()) {
			user.unlock();			
		}
	}
	
	@Override
	public void valueChange(com.vaadin.data.Property.ValueChangeEvent event) {
		refresh();
	}
}

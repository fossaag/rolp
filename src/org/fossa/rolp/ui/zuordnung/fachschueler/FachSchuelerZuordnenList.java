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

package org.fossa.rolp.ui.zuordnung.fachschueler;

import org.fossa.rolp.RolpApplication;
import org.fossa.rolp.data.fach.FachLaso;
import org.fossa.rolp.data.klasse.KlasseContainer;
import org.fossa.rolp.data.schueler.SchuelerContainer;
import org.fossa.rolp.data.schueler.SchuelerLaso;
import org.fossa.rolp.data.zuordnung.fachschueler.ZuordnungFachSchuelerContainer;
import org.fossa.rolp.data.zuordnung.fachschueler.ZuordnungFachSchuelerHandler;
import org.fossa.rolp.data.zuordnung.fachschueler.ZuordnungFachSchuelerLaso;
import org.fossa.vaadin.ui.exception.FossaLasoLockedException;

import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.Table;

public class FachSchuelerZuordnenList extends Table {
	
	private static final long serialVersionUID = 4729454005664382006L;
	private RolpApplication app;
	public FachLaso fach;
	private BeanItemContainer<ZuordnungFachSchuelerHandler> zuordnungenFS;
	
	public FachSchuelerZuordnenList(RolpApplication app, FachLaso fach) throws FossaLasoLockedException {
		this.app = app;
		this.fach = fach;
		throwExceptionIfLasosUnavailable();
		buildContainer();
		initialize();
	}
	
	private void throwExceptionIfLasosUnavailable() throws FossaLasoLockedException {
		if (fach.isLocked()) {
			throw new FossaLasoLockedException();
		}
		for (SchuelerLaso schueler: SchuelerContainer.getAllSchuelerOfKlasse(KlasseContainer.getKlasseByLehrer(app.getLoginLehrer()).getPojo()).getItemIds()) {
			if (schueler.isLocked()) {
				throw new FossaLasoLockedException();
			}
			ZuordnungFachSchuelerLaso zuordnungFS = ZuordnungFachSchuelerContainer.getZuordnung(schueler.getPojo(), fach.getPojo());
			if (zuordnungFS != null && zuordnungFS.isLocked()) {
				throw new FossaLasoLockedException();
			}			
		}
	}

	public void initialize() {
		setSizeFull();
		setPageLength(20);
		setVisibleColumns(ZuordnungFachSchuelerContainer.FACH_ZUORDNEN_COL_ORDER);
		setColumnHeaders(ZuordnungFachSchuelerContainer.FACH_ZUORDNEN_COL_HEADERS);
		setSelectable(false);
		setImmediate(false);
		setNullSelectionAllowed(false);
		setColumnCollapsingAllowed(true);
		setColumnReorderingAllowed(true);
		setTableFieldFactory(new FachSchuelerZuordnenFieldFactory());
		setEditable(true);		
	}
	
	public void refresh() {
		buildContainer();
		initialize();
	}
	
	private void buildContainer() {
		fach.lock();
		zuordnungenFS = new BeanItemContainer<ZuordnungFachSchuelerHandler>(ZuordnungFachSchuelerHandler.class);
		for (SchuelerLaso schueler: SchuelerContainer.getAllSchuelerOfKlasse(KlasseContainer.getKlasseByLehrer(app.getLoginLehrer()).getPojo()).getItemIds()) {
			boolean schuelerHasFach = (ZuordnungFachSchuelerContainer.getZuordnung(schueler.getPojo(), fach.getPojo()) != null);
			ZuordnungFachSchuelerHandler aZuordnungFS = new ZuordnungFachSchuelerHandler(schueler.getPojo(), fach.getPojo(), schuelerHasFach, this);
			zuordnungenFS.addBean(aZuordnungFS);
			schueler.lock();
			ZuordnungFachSchuelerLaso zuordnungFS = ZuordnungFachSchuelerContainer.getZuordnung(schueler.getPojo(), fach.getPojo());
			if (zuordnungFS != null) {
				zuordnungFS.lock();
			}
		}
		setContainerDataSource(zuordnungenFS);
	}

	public void unlockLasos() {
		fach.unlock();
		for (SchuelerLaso schueler: SchuelerContainer.getAllSchuelerOfKlasse(KlasseContainer.getKlasseByLehrer(app.getLoginLehrer()).getPojo()).getItemIds()) {
			schueler.unlock();
			ZuordnungFachSchuelerLaso zuordnungFS = ZuordnungFachSchuelerContainer.getZuordnung(schueler.getPojo(), fach.getPojo());
			if (zuordnungFS != null) {
				zuordnungFS.unlock();
			}
		}
	}

	public boolean nichtsZugeordnet() {
		for (ZuordnungFachSchuelerHandler zuordnungFS: zuordnungenFS.getItemIds()) {
			if (zuordnungFS.getZugeordnet()) {
				return false;
			}
		}		
		return true;
	}
}

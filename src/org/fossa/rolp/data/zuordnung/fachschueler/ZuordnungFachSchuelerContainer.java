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

package org.fossa.rolp.data.zuordnung.fachschueler;

import java.io.Serializable;

import org.fossa.rolp.data.fach.FachContainer;
import org.fossa.rolp.data.fach.FachLaso;
import org.fossa.rolp.data.fach.FachPojo;
import org.fossa.rolp.data.schueler.SchuelerContainer;
import org.fossa.rolp.data.schueler.SchuelerLaso;
import org.fossa.rolp.data.schueler.SchuelerPojo;
import org.fossa.vaadin.laso.FossaLaso;

import com.vaadin.data.util.BeanItemContainer;

public class ZuordnungFachSchuelerContainer extends BeanItemContainer<ZuordnungFachSchuelerLaso> implements Serializable {
	
	private static final long serialVersionUID = -5091851244135719601L;
	
	private static final String ZUGEORDNET_HEADER = "";
	public static final String ZUGEORDNET_CAPTION = "";
	private static final String SCHUELERNAME_COLUMN_HEADER = "Schülername";
	public static final String DYNAMIC_GENERATED_COLUMN_ERLEDIGT = "erledigt";

	public static final String[] FACH_ZUORDNEN_COL_ORDER = new String[] {
		ZuordnungFachSchuelerHandler.SCHUELERNAME_COLUMN, 
		ZuordnungFachSchuelerHandler.ZUGEORDNET_COLUMN,
	};
	
	public static final String[] FACH_ZUORDNEN_COL_HEADERS = new String[] {
		SCHUELERNAME_COLUMN_HEADER, 
		ZUGEORDNET_HEADER,
	};
	
	public static final String[] FACHSCHUELERLIST_COL_ORDER = new String[] {
		SchuelerPojo.VORNAME_COLUMN,
		SchuelerPojo.NAME_COLUMN,
		DYNAMIC_GENERATED_COLUMN_ERLEDIGT,
	};
	
	public static final String[] SCHUELERFACHLIST_COL_ORDER = new String[] {
	    FachPojo.FACHBEZEICHNUNG_COLUMN,
		DYNAMIC_GENERATED_COLUMN_ERLEDIGT,
	};
	
	private static ZuordnungFachSchuelerContainer zuordnungFachSchuelerContainer;
	
	private ZuordnungFachSchuelerContainer() {
		super(ZuordnungFachSchuelerLaso.class);
	}
	
	public static ZuordnungFachSchuelerContainer getInstance() {
		if (zuordnungFachSchuelerContainer == null) {
			FachContainer.getInstance().getItemIds();
			SchuelerContainer.getInstance().getItemIds();
			zuordnungFachSchuelerContainer = new ZuordnungFachSchuelerContainer();
			for (ZuordnungFachSchuelerPojo zuordnungFachSchuelerPojo: ZuordnungFachSchuelerLaso.getAll()) {
				ZuordnungFachSchuelerLaso zuordnungFachSchuelerLaso = new ZuordnungFachSchuelerLaso(zuordnungFachSchuelerPojo);
				zuordnungFachSchuelerContainer.addBean(zuordnungFachSchuelerLaso);
			}
		}
		return zuordnungFachSchuelerContainer;
	}
	
	public static BeanItemContainer<SchuelerPojo> getAllSchuelerOfFach(FachPojo fach) {
		BeanItemContainer<SchuelerPojo> schuelerOfFach = new BeanItemContainer<SchuelerPojo>(SchuelerPojo.class);
		for (ZuordnungFachSchuelerLaso zuordnungFS : getAllZuordnungenFachSchuelerOfFach(fach).getItemIds()) {
			schuelerOfFach.addBean(zuordnungFS.getSchueler());
		}
		return schuelerOfFach;
	}
	
	public static BeanItemContainer<SchuelerLaso> getAllSchuelerLasoOfFach(FachPojo fach) {
		BeanItemContainer<SchuelerLaso> schuelerLasoOfFach = new BeanItemContainer<SchuelerLaso>(SchuelerLaso.class);
		for (ZuordnungFachSchuelerLaso zuordnungFS : getAllZuordnungenFachSchuelerOfFach(fach).getItemIds()) {
			for (SchuelerLaso schueler : SchuelerContainer.getInstance().getItemIds()) {
				if (zuordnungFS.getSchueler().getId().equals(schueler.getId())) {
					schuelerLasoOfFach.addBean(schueler);
				}
			}
		}
		return schuelerLasoOfFach;
	}
	
	public static BeanItemContainer<FachLaso> getAllFachLasoOfSchueler(SchuelerPojo schueler) {
		BeanItemContainer<FachLaso> fachLasoOfSchueler = new BeanItemContainer<FachLaso>(FachLaso.class);
		for (ZuordnungFachSchuelerLaso zuordnungFS : getAllZuordnungenFachSchuelerOfSchueler(schueler).getItemIds()) {
			for (FachLaso fach : FachContainer.getInstance().getItemIds()) {
				if (zuordnungFS.getFach().getId().equals(fach.getId())) {
					fachLasoOfSchueler.addBean(fach);
				}
			}
		}
		return fachLasoOfSchueler;
	}
	
	public static BeanItemContainer<ZuordnungFachSchuelerLaso> getAllZuordnungenFachSchuelerOfFach(FachPojo fach) {
		BeanItemContainer<ZuordnungFachSchuelerLaso> zuordnungFSOfFach = new BeanItemContainer<ZuordnungFachSchuelerLaso>(ZuordnungFachSchuelerLaso.class);
		for (ZuordnungFachSchuelerLaso zuordnungFS : getInstance().getItemIds()) {
			if (zuordnungFS.getFach().getId().equals(fach.getId())) {
				zuordnungFSOfFach.addBean(zuordnungFS);
			}
		}
		return zuordnungFSOfFach;
	}
	
	public static BeanItemContainer<FachPojo> getAllFaecherOfSchueler(SchuelerPojo schueler) {
		BeanItemContainer<FachPojo> faecherOfSchueler = new BeanItemContainer<FachPojo>(FachPojo.class);
		for (ZuordnungFachSchuelerLaso zuordnungFS : getAllZuordnungenFachSchuelerOfSchueler(schueler).getItemIds()) {
			faecherOfSchueler.addBean(zuordnungFS.getFach());
		}
		return faecherOfSchueler;
	}
	
	public static BeanItemContainer<ZuordnungFachSchuelerLaso> getAllZuordnungenFachSchuelerOfSchueler(SchuelerPojo schueler) {
		BeanItemContainer<ZuordnungFachSchuelerLaso> zuordnungFSOfSchueler = new BeanItemContainer<ZuordnungFachSchuelerLaso>(ZuordnungFachSchuelerLaso.class);
		for (ZuordnungFachSchuelerLaso zuordnungFS : getInstance().getItemIds()) {
			if (zuordnungFS.getSchueler().getId().equals(schueler.getId())) {
				zuordnungFSOfSchueler.addBean(zuordnungFS);
			}
		}
		return zuordnungFSOfSchueler;
	}

	public static ZuordnungFachSchuelerLaso getZuordnung(SchuelerPojo schuelerPojo, FachPojo fach) {
		for (ZuordnungFachSchuelerLaso zuordnungFS : getInstance().getItemIds()) {
			if (zuordnungFS.getSchueler().getId().equals(schuelerPojo.getId()) && zuordnungFS.getFach().getId().equals(fach.getId())) {
				return zuordnungFS;
			}
		}
		return null;
	}

	public static String getErledigteFacheinschaetzungenString(FachPojo fach) {
		BeanItemContainer<SchuelerPojo> alleFachSchueler = ZuordnungFachSchuelerContainer.getAllSchuelerOfFach(fach);
		int counter = 0;
		for (SchuelerPojo schueler : alleFachSchueler.getItemIds()) {
			ZuordnungFachSchuelerLaso zuordnungFS = ZuordnungFachSchuelerContainer.getZuordnung(schueler, fach);
			if (zuordnungFS.getFacheinschaetzung() != null && zuordnungFS.getFacheinschaetzung().getErledigt()) {
				counter++;
			}
		}
		return counter + "/" + alleFachSchueler.size();
	}
	
	public static boolean alleFacheinschaetzungenVonSchuelerErledigt(SchuelerPojo schueler) {
		BeanItemContainer<FachPojo> alleFachSchueler = ZuordnungFachSchuelerContainer.getAllFaecherOfSchueler(schueler);
		if(alleFachSchueler.size() == 0){
			return false;
		}
		int counter = 0;
		for (FachPojo fach : alleFachSchueler.getItemIds()) {
			ZuordnungFachSchuelerLaso zuordnungFS = ZuordnungFachSchuelerContainer.getZuordnung(schueler, fach);
			if (zuordnungFS.getFacheinschaetzung() != null && zuordnungFS.getFacheinschaetzung().getErledigt()) {
				counter++;
			}
		}
		return counter == alleFachSchueler.size();
	}

	public static boolean fachHatEinschaetzungen(FachPojo fach) {
		for (ZuordnungFachSchuelerLaso zuordnungFS : getAllZuordnungenFachSchuelerOfFach(fach).getItemIds()) {
			if (zuordnungFS.getFacheinschaetzung() != null) {
				return true;
			}
		}
		return false;
	}

	public static boolean schuelerHatEinschaetzungen(SchuelerPojo schueler) {
		for (ZuordnungFachSchuelerLaso zuordnungFS : getAllZuordnungenFachSchuelerOfSchueler(schueler).getItemIds()) {
			if (zuordnungFS.getFacheinschaetzung() != null) {
				return true;
			}
		}
		return false;
	}

	public void deleteZuordnungFS(ZuordnungFachSchuelerLaso zuordnungFachSchueler) {
		FossaLaso.deleteIfExists(zuordnungFachSchueler.getPojo());
		zuordnungFachSchuelerContainer = null;
		getInstance();
	}
}

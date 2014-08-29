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

import org.fossa.rolp.data.fach.FachPojo;
import org.fossa.rolp.data.schueler.SchuelerPojo;

import com.vaadin.ui.Table;

public class ZuordnungFachSchuelerHandler {
	
	public static final String SCHUELERNAME_COLUMN = "schuelername";
	public static final String ZUGEORDNET_COLUMN = "zugeordnet";
	
	private FachPojo fach;
	private SchuelerPojo schueler;
	private boolean zugeordnet;
	private Object object;

	public ZuordnungFachSchuelerHandler(SchuelerPojo schueler, FachPojo fach, boolean zugeordnet, Object object) {
		this.fach = fach;
		this.schueler = schueler;
		this.zugeordnet = zugeordnet;
		this.object = object;
	}
	
	public String getSchuelername() {
		return schueler.getName() + ", " + schueler.getVorname();
	}
	
	public boolean getZugeordnet() {
		return zugeordnet;
	}
	
	public void setZugeordnet(boolean zugeordnet) throws Exception {
		if (this.zugeordnet == zugeordnet) {
			return;
		}
		this.zugeordnet = zugeordnet;
		ZuordnungFachSchuelerLaso zuordnungFS = ZuordnungFachSchuelerContainer.getZuordnung(schueler, fach);
		if (zuordnungFS == null) {
			zuordnungFS = new ZuordnungFachSchuelerLaso();
			zuordnungFS.setFach(fach);
			zuordnungFS.setSchueler(schueler);
			ZuordnungFachSchuelerContainer.getInstance().addItem(zuordnungFS);
		} else {
			if (object instanceof Table &&
					fach.getFachtyp().isPflichtfach() && 
					ZuordnungFachSchuelerContainer.getAllSchuelerOfFach(fach).size() == 1 && 
					ZuordnungFachSchuelerContainer.getAllSchuelerOfFach(fach).firstItemId().getId().equals(schueler.getId())
				)
			{
				zugeordnet = true;
				return;
			} else {				
				ZuordnungFachSchuelerContainer.getInstance().deleteZuordnungFS(zuordnungFS);
			}
		}
		System.out.println("ich wurde zugeordnet: " + zugeordnet);
		System.out.println("Kurs: " + fach);
		System.out.println("Schüler: " + getSchuelername());
	}
}

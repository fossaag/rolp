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

import java.util.List;

import org.fossa.rolp.data.einschaetzung.EinschaetzungLaso;
import org.fossa.rolp.data.fach.FachContainer;
import org.fossa.rolp.data.fach.FachPojo;
import org.fossa.rolp.data.schueler.SchuelerContainer;
import org.fossa.rolp.data.schueler.SchuelerPojo;
import org.fossa.vaadin.laso.FossaLaso;

public class ZuordnungFachSchuelerLaso extends FossaLaso {

	protected ZuordnungFachSchuelerPojo zuordnungFachSchueler;
	private EinschaetzungLaso facheinschaetzungLaso;

	public ZuordnungFachSchuelerLaso(ZuordnungFachSchuelerPojo zuordnungFachSchuelerPojo) {
		if (zuordnungFachSchuelerPojo.getFacheinschaetzung() != null) {
			this.facheinschaetzungLaso = new EinschaetzungLaso(zuordnungFachSchuelerPojo.getFacheinschaetzung());
		} else {
			this.facheinschaetzungLaso = null;
		}
		this.zuordnungFachSchueler = zuordnungFachSchuelerPojo;		
	}
	
	public ZuordnungFachSchuelerLaso() {
		this.zuordnungFachSchueler = new ZuordnungFachSchuelerPojo();
	}
	
	public FachPojo getFach() {
		return FachContainer.getFachLasoAktuell(zuordnungFachSchueler.getFach()).getPojo();
	}

	public void setFach(FachPojo fach) {
		zuordnungFachSchueler.setFach(fach);
		writeToDatabase();
	}

	public SchuelerPojo getSchueler() {
		return SchuelerContainer.getSchuelerLasoAktuell(zuordnungFachSchueler.getSchueler()).getPojo();
	}

	public void setSchueler(SchuelerPojo schueler) {
		zuordnungFachSchueler.setSchueler(schueler);
		writeToDatabase();
	}
	
	public EinschaetzungLaso getFacheinschaetzung() {
		return facheinschaetzungLaso;
	}

	public void setFacheinschaetzung(EinschaetzungLaso facheinschaetzung) {
		facheinschaetzungLaso = facheinschaetzung;
		zuordnungFachSchueler.setFacheinschaetzung(facheinschaetzung.getPojo());
		writeToDatabase();
	}
	
	public String getName() {
		if (getSchueler() == null) {
			return "";
		}
		return getSchueler().getName();
	}
	
	public String getVorname() {
		if (getSchueler() == null) {
			return "";
		}
		return getSchueler().getVorname();
	}
	
	public String getFachbezeichnung() {
		if (getFach() == null) {
			return "";
		}
		return getFach().getFachbezeichnung();
	}
	
	public boolean getErledigt() {
		if (getFacheinschaetzung() == null) {
			return false;
		}
		return getFacheinschaetzung().getErledigt();
	}

	@Override
	public Long getId() {
		return zuordnungFachSchueler.getId();
	}

	@Override
	public void setId(Long id) {
		zuordnungFachSchueler.setId(id);
		writeToDatabase();
	}

	@Override
	public ZuordnungFachSchuelerPojo getPojo() {
		return zuordnungFachSchueler;
	}

	@Override
	protected void writeToDatabase() {
		writeToDatabase(zuordnungFachSchueler);
	}

	@SuppressWarnings("unchecked")
	public static List<ZuordnungFachSchuelerPojo> getAll() {
		return (List<ZuordnungFachSchuelerPojo>) getAll(ZuordnungFachSchuelerPojo.class);
	}
}

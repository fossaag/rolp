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

package org.fossa.rolp.data.fach;

import java.util.List;

import org.fossa.rolp.data.fach.fachdefinition.FachdefinitionPojo;
import org.fossa.rolp.data.lehrer.LehrerPojo;
import org.fossa.rolp.data.zuordnung.fachschueler.ZuordnungFachSchuelerContainer;
import org.fossa.vaadin.laso.FossaLaso;

public class FachLaso extends FossaLaso {
	
	private static final long serialVersionUID = 893521359692022131L;
	
	public static final String ZUGEWIESENE_SCHUELER_COLUMN = "zugewieseneSchuelerAnzahl";
	public static final String FACHLEHRER_EINS_COLUMN = "fachlehrerEinsString";
	public static final String FACHLEHRER_ZWEI_COLUMN = "fachlehrerZweiString";
	public static final String FACHLEHRER_DREI_COLUMN = "fachlehrerDreiString";
	public static final String ERLEDIGTE_FACHEINSCHAETZUNGEN_COLUMN = "erledigteFacheinschaetzungenString";
	public static final String KLASSE_COLUMN = "klasse";
	
	private FachPojo fach;
	
	public FachLaso() {
		fach = new FachPojo();
	}
	
	public FachLaso(FachPojo fachPojo) {
		this.fach = fachPojo;
	}
	
	public FachPojo getPojo() {
		return fach;		
	}
	
	public Long getId() {
		return fach.getId();
	}

	public void setId(Long id) {
		fach.setId(id);
		writeToDatabase();
	}
	
	public FachdefinitionPojo getFachdefinition() {
		return fach.getFachdefinition();
	}

	public void setFachdefinition(FachdefinitionPojo fachdefinition) {
		fach.setFachdefinition(fachdefinition);
		writeToDatabase();
	}
	
	public int getZugewieseneSchuelerAnzahl() {		
		return ZuordnungFachSchuelerContainer.getAllSchuelerOfFach(fach).size();
	}
	
	public LehrerPojo getFachlehrerEins() {
		return fach.getFachlehrerEins();
	}

	public void setFachlehrerEins(LehrerPojo fachlehrerEins) {
		fach.setFachlehrerEins(fachlehrerEins);
		writeToDatabase();
	}
	
	public LehrerPojo getFachlehrerZwei() {
		return fach.getFachlehrerZwei();
	}

	public void setFachlehrerZwei(LehrerPojo fachlehrerZwei) {
		fach.setFachlehrerZwei(fachlehrerZwei);
		writeToDatabase();
	}
	
	public LehrerPojo getFachlehrerDrei() {
		return fach.getFachlehrerDrei();
	}

	public void setFachlehrerDrei(LehrerPojo fachlehrerDrei) {
		fach.setFachlehrerDrei(fachlehrerDrei);
		writeToDatabase();
	}
	
	public String getFachbezeichnung() {
		if (fach.getFachdefinition() == null) {
			return " - ";
		}
		return fach.getFachdefinition().getFachbezeichnung();
	}

	public String getFachlehrerEinsString() {
		if (fach.getFachlehrerEins() == null){
			return " - ";
		}
		return fach.getFachlehrerEins().getUser().getFirstname() + " " + fach.getFachlehrerEins().getUser().getLastname();
	}
	
	public String getFachlehrerZweiString() {
		if (fach.getFachlehrerZwei() == null){
			return " - ";
		}
		return fach.getFachlehrerZwei().getUser().getFirstname() + " " + fach.getFachlehrerZwei().getUser().getLastname();
	}
	
	public String getFachlehrerDreiString() {
		if (fach.getFachlehrerDrei() == null){
			return " - ";
		}
		return fach.getFachlehrerDrei().getUser().getFirstname() + " " + fach.getFachlehrerDrei().getUser().getLastname();
	}
	
	public String getKlasse() {
		if (getFachdefinition() == null) {
			return " - ";
		}
		if (getFachdefinition().getFachtyp() == null) {
			return " - ";
		}
		if (getFachdefinition().getFachtyp().isKurs()) {
			return " - ";
		}
		return ZuordnungFachSchuelerContainer.getAllSchuelerOfFach(getPojo()).firstItemId().getKlasse().getKlassenname();
	}
	
	public String getErledigteFacheinschaetzungenString() {
		return ZuordnungFachSchuelerContainer.getErledigteFacheinschaetzungenString(fach);
	}
	
	@Override
	protected void writeToDatabase() {
		writeToDatabase(fach);
	}

	@SuppressWarnings("unchecked")
	public static List<FachPojo> getAll() {
		return (List<FachPojo>) getAll(FachPojo.class);
	}
}

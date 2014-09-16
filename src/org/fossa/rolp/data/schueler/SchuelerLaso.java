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

package org.fossa.rolp.data.schueler;

import java.util.List;

import org.fossa.rolp.data.einschaetzung.EinschaetzungLaso;
import org.fossa.rolp.data.klasse.KlassePojo;
import org.fossa.rolp.data.zuordnung.fachschueler.ZuordnungFachSchuelerContainer;
import org.fossa.vaadin.laso.FossaLaso;

public class SchuelerLaso extends FossaLaso {

	private static final long serialVersionUID = -8771333743186930287L;
	
	private SchuelerPojo schueler;
	private EinschaetzungLaso schuelereinschaetzungLaso;
	
	public SchuelerLaso(SchuelerPojo schueler) {
		if (schueler.getSchuelereinschaetzung() != null) {
			this.schuelereinschaetzungLaso = new EinschaetzungLaso(schueler.getSchuelereinschaetzung());
		} else {
			this.schuelereinschaetzungLaso = null;
		}
		this.schueler = schueler;
	}

	public SchuelerLaso() {
		schueler = new SchuelerPojo();
	}
	
	public SchuelerLaso(KlassePojo klasse) {
		schueler = new SchuelerPojo();
		schueler.setKlasse(klasse);
	}

	public SchuelerPojo getPojo() {
		return schueler;		
	}

	public String getVorname() {
		return schueler.getVorname();
	}

	public void setVorname(String vorname) {
		schueler.setVorname(vorname);
		writeToDatabase();
	}

	public String getName() {
		return schueler.getName();
	}

	public void setName(String name) {
		schueler.setName(name);
		writeToDatabase();
	}

	public Long getId() {
		return schueler.getId();
	}

	public void setId(Long id) {
		schueler.setId(id);
		writeToDatabase();
	}
	
	public String getVersetzungsvermerk() {
		return schueler.getVersetzungsvermerk();
	}

	public void setVersetzungsvermerk(String versetzungsvermerk) {
		schueler.setVersetzungsvermerk(versetzungsvermerk);
		writeToDatabase();
	}
	
	public KlassePojo getKlasse(){
		return schueler.getKlasse();
	}
	
	public void setKlasse(KlassePojo klasse) {
		schueler.setKlasse(klasse);
		writeToDatabase();
	}
	
	public EinschaetzungLaso getSchuelereinschaetzung(){
		return schuelereinschaetzungLaso;
	}
	
	public void setSchuelereinschaetzung(EinschaetzungLaso schuelereinschaetzung) {
		schuelereinschaetzungLaso = schuelereinschaetzung;
		if (schuelereinschaetzung != null) {
			schueler.setSchuelereinschaetzung(schuelereinschaetzung.getPojo());
		} else {
			schueler.setSchuelereinschaetzung(null);
		}		
		writeToDatabase();
	}
	
	public boolean getErledigt() {
		if (getSchuelereinschaetzung() == null) {
			return false;
		}
		return getSchuelereinschaetzung().getErledigt();
	}
	
	public boolean getFacheinschaetzungenErledigt() {
		return ZuordnungFachSchuelerContainer.alleFacheinschaetzungenVonSchuelerErledigt(schueler);
	}
	
	@Override
	protected void writeToDatabase() {
		writeToDatabase(schueler);
	}

	@SuppressWarnings("unchecked")
	public static List<SchuelerPojo> getAll() {
		return (List<SchuelerPojo>) getAll(SchuelerPojo.class);
	}
}

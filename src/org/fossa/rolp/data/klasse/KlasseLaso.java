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

package org.fossa.rolp.data.klasse;

import java.util.List;

import org.fossa.rolp.data.einschaetzung.EinschaetzungLaso;
import org.fossa.rolp.data.lehrer.LehrerPojo;
import org.fossa.vaadin.laso.FossaLaso;

public class KlasseLaso extends FossaLaso {

	private static final long serialVersionUID = -1089342731703984092L;

	public static final String KLASSENEINSCHAETZUNG_COLUMN = "klasseneinschaetzungString";
	
	private KlassePojo klasse;
	private EinschaetzungLaso klasseneinschaetzungLaso;
	
	public KlasseLaso(KlassePojo klassePojo) {
		if (klassePojo.getKlasseneinschaetzung() != null) {
			this.klasseneinschaetzungLaso = new EinschaetzungLaso(klassePojo.getKlasseneinschaetzung());
		} else {
			this.klasseneinschaetzungLaso = null;
		}
		this.klasse = klassePojo;
	}

	public KlasseLaso() {
		klasse = new KlassePojo();
	}
	
	public KlassePojo getPojo() {
		return klasse;		
	}
	
	public Long getId() {
		return klasse.getId();
	}

	public void setId(Long id) {
		klasse.setId(id);
		writeToDatabase();
	}	

	public String getKlassenname() {
		return klasse.getKlassenname();
	}
	
	public void setKlassenname(String klassenname) {
		klasse.setKlassenname(klassenname);
		writeToDatabase();
	}
	
	public LehrerPojo getKlassenlehrer(){
		return klasse.getKlassenlehrer();
	}
	
	public void setKlassenlehrer(LehrerPojo klassenlehrer){
		klasse.setKlassenlehrer(klassenlehrer);
		writeToDatabase();
	}
	
	public EinschaetzungLaso getKlasseneinschaetzung(){
		return klasseneinschaetzungLaso;
	}
	
	public void setKlasseneinschaetzung(EinschaetzungLaso klasseneinschaetzung){
		klasseneinschaetzungLaso = klasseneinschaetzung;
		if (klasseneinschaetzung != null) {
			klasse.setKlasseneinschaetzung(klasseneinschaetzung.getPojo());
		} else {
			klasse.setKlasseneinschaetzung(null);
		}		
		writeToDatabase();
	}

	@Override
	protected void writeToDatabase() {
		writeToDatabase(klasse);
	}

	@SuppressWarnings("unchecked")
	public static List<KlassePojo> getAll() {
		return (List<KlassePojo>) getAll(KlassePojo.class);
	}
}

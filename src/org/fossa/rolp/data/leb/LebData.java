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

package org.fossa.rolp.data.leb;

import java.util.ArrayList;
import java.util.Collection;

import org.fossa.rolp.data.leb.halbjahr.HalbjahrPojo;

public class LebData {

	private String schuelername = "";
	private String klassenname = "";
	private String klassenlehrerUnterschrift = "";
	private String schuljahr = "";
	private HalbjahrPojo schulhalbjahr;
	private String klassenbrief  = "";
	private String individuelleEinschaetzung = "";
	private Collection<LebFacheinschaetzungData> facheinschaetzungsdaten = new ArrayList<LebFacheinschaetzungData>();
	private String versetzungsvermerk = "";
	private String datumString = "";
	
	
	public String getSchuelername() {
		return schuelername;
	}

	public void setSchuelername(String schuelername) {
		this.schuelername = schuelername;
	}

	public String getKlassenlehrerUnterschrift() {
		return klassenlehrerUnterschrift;
	}

	public void setKlassenlehrerUnterschrift(String klassenlehrerUnterschrift) {
		this.klassenlehrerUnterschrift = klassenlehrerUnterschrift;
	}

	public String getKlassenname() {
		return klassenname;
	}
	
	public void setKlassenname(String klassenname) {
		this.klassenname = klassenname;
	}
	
	public String getSchuljahr() {
		return schuljahr;
	}
	
	public void setSchuljahr(String schuljahr) {
		this.schuljahr = schuljahr;
	}
	
	public HalbjahrPojo getSchulhalbjahr() {
		return schulhalbjahr;
	}
	
	public void setSchulhalbjahr(HalbjahrPojo halbjahrPojo) {
		this.schulhalbjahr = halbjahrPojo;
	}
	
	public String getKlassenbrief() {
		return klassenbrief;
	}
	
	public void setKlassenbrief(String klassenbrief) {
		this.klassenbrief = klassenbrief;
	}
	
	public String getIndividuelleEinschaetzung() {
		return individuelleEinschaetzung;
	}
	
	public void setIndividuelleEinschaetzung(String individuelleEinschaetzung) {
		this.individuelleEinschaetzung = individuelleEinschaetzung;
	}
	
	public Collection<LebFacheinschaetzungData> getFacheinschaetzungsdaten() {
		return facheinschaetzungsdaten;
	}
	
	public void setFacheinschaetzungsdaten(Collection<LebFacheinschaetzungData> facheinschaetzungsdaten) {
		this.facheinschaetzungsdaten = facheinschaetzungsdaten;
	}
	
	public String getVersetzungsvermerk() {
		return versetzungsvermerk;
	}
	
	public void setVersetzungsvermerk(String versetzungsvermerk) {
		this.versetzungsvermerk = versetzungsvermerk;
	}
	
	public String getDatumString() {
		return datumString;
	}
	
	public void setDatumString(String datumString) {
		this.datumString = datumString;
	}
}

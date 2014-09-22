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

public class LebFacheinschaetzungData {

	private String fachname = "";
	private String facheinschaetzung = "";
	private Collection<String> fachbezeichnungen = new ArrayList<String>();
	private String unterschrift = "";
	
	public String getFachname() {
		return fachname;
	}
	
	public void setFachname(String fachname) {
		this.fachname = fachname;
	}
	
	public String getFacheinschaetzung() {
		return facheinschaetzung;
	}
	
	public void setFacheinschaetzung(String facheinschaetzung) {
		this.facheinschaetzung = facheinschaetzung;
	}
	
	public Collection<String> getFachbezeichnungen() {
		return fachbezeichnungen;
	}
	
	public void setFachbezeichnungen(Collection<String> fachbezeichnungen) {
		this.fachbezeichnungen = fachbezeichnungen;
	}

	public String getUnterschrift() {
		return unterschrift;
	}

	public void setUnterschrift(String unterschrift) {
		this.unterschrift = unterschrift;
	}
}

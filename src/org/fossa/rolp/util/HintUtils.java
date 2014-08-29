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

package org.fossa.rolp.util;

import org.fossa.rolp.data.fach.FachLaso;
import org.fossa.rolp.data.klasse.KlassePojo;
import org.fossa.rolp.data.schueler.SchuelerLaso;
import org.fossa.rolp.data.zuordnung.fachschueler.ZuordnungFachSchuelerLaso;

import com.vaadin.data.util.BeanItemContainer;

public class HintUtils {
	public static final String SEPERATOR = "<br><br>";
	public static final String HEADER_WARNING = "<b>Warnung:</b><br>";

	public static String createHinweistextKlasseKeinePflichtfaecher(BeanItemContainer<FachLaso> pflichtfaecher, KlassePojo klasse) {
		if (pflichtfaecher.size() == 0) {
			return HEADER_WARNING + "Klasse '" + klasse.getKlassenname() + "' besitzt keine Pflichtfächer<br><br>";
		}	
		return "";		
	}
	
	public static String createHinweistextSchuelerKeineKurse(BeanItemContainer<ZuordnungFachSchuelerLaso> fachSchuelerZuordnungen, SchuelerLaso schueler) {
		boolean schuelerHatKurs = false;
		for (ZuordnungFachSchuelerLaso zuordnungFS: fachSchuelerZuordnungen.getItemIds()) {
			if (zuordnungFS.getFach().getFachtyp().isKurs() && zuordnungFS.getSchueler().getId().equals(schueler.getId())) {
				schuelerHatKurs = true;
			}				
		}
		if (!schuelerHatKurs) {
			return HEADER_WARNING + "Schüler '" + schueler.getVorname() + " " + schueler.getName() + "' besucht keinen Kurs!" + SEPERATOR;
		}
		return "";		
	}
}

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

import org.fossa.rolp.data.klasse.KlasseLaso;

import com.vaadin.data.util.BeanItemContainer;

public class KlassenstufenUtils {

	public static int getKlassenstufe(String klassenname) throws NumberFormatException {
		if (klassenname.isEmpty()) {
			return 0;
		}
		String klassenstufe = klassenname.substring(0, 1);
		if (keineKlassenstufeEnthalten(klassenstufe)) {
			return 0;
		}
		if (klassenname.length() > 1) {
			if (Integer.valueOf(klassenstufe) == 1) {
				if (klassenname.substring(1, 2).equals("0")) {
					return Integer.valueOf(klassenname.substring(0, 2));
				}				
			}
		}
		return Integer.valueOf(klassenstufe);
	}

	public static String erhoeheKlassenstufe(String klassenname) {
		int klassenstufe = KlassenstufenUtils.getKlassenstufe(klassenname);
		if (klassenstufe == 0) {
			return klassenname;
		}
		Integer neueKlassenstufe = klassenstufe + 1;
		if (klassenname.length() == 1) {
			return neueKlassenstufe.toString();
		}
		if (klassenname.substring(1, 2).equals("0")) {
			return neueKlassenstufe + klassenname.substring(2);
		}
		return neueKlassenstufe + klassenname.substring(1);
	}

	public static String generateKlassennameForKlassenstufe(int klassenstufe, BeanItemContainer<KlasseLaso> klasseContainer) {
		String[] klassennamenErweiterungen = {"a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z"};
		for (int i=0; i<klassennamenErweiterungen.length; i++) {
			boolean foundDoublette = false;
			for (KlasseLaso klasse : klasseContainer.getItemIds()) {
				if (klasse.getKlassenname().equals(klassenstufe + klassennamenErweiterungen[i])) {
					foundDoublette = true;
					break;
				}
			}
			if (!foundDoublette) {
				return klassenstufe + klassennamenErweiterungen[i];
			}
		}
		return null;
	}
	
	public static int getLebFontSize(String klassenname) {
		int klassenstufe = getKlassenstufe(klassenname);
		if (klassenstufe > 4) {
			return 10;
		} else {
			return 12;
		}	
	}
	
	public static float getLebZeilenabstandAsFactor(String klassenname) {
		int klassenstufe = getKlassenstufe(klassenname);
		if (klassenstufe > 4) {
			return 1.5f;
		} else {
			return 1.3f;
		}	
	}
	
	private static boolean keineKlassenstufeEnthalten(String klassenstufe) {
		try {
			Integer.valueOf(klassenstufe);
			return false;
		} catch (NumberFormatException e){
			return true;
		}
	}
}

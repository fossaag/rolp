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

import java.io.Serializable;

import org.fossa.rolp.data.lehrer.LehrerPojo;
import org.fossa.vaadin.laso.FossaLaso;

import com.vaadin.data.util.BeanItemContainer;

public class KlasseContainer extends BeanItemContainer<KlasseLaso> implements Serializable {
	
	private static final long serialVersionUID = -1561855055662151443L;

	public static final String[] NATURAL_FORM_ORDER = new String[] {
		KlassePojo.KLASSENNAME_COLUMN,
		KlassePojo.KLASSENTYP_COLUMN,
	};
	
	public static final String[] NATURAL_COL_ORDER = new String[] {
		KlassePojo.KLASSENNAME_COLUMN,
		KlasseLaso.KLASSENTYP_COLUMN,
	};
	
	public static final String[] COL_HEADERS = new String[] {
		KlassePojo.KLASSENNAME_COLUMN,
		KlassePojo.KLASSENTYP_COLUMN,
	};
	
	public static final Object[] KLASSENLEHRER_ORDER = new Object[] {
		KlassePojo.KLASSENNAME_COLUMN,
		KlassePojo.KLASSENLEHRER_COLUMN,
	};


	private static KlasseContainer klasseContainer;

	private KlasseContainer() {
		super(KlasseLaso.class);
	}
	
	public static KlasseContainer getInstance() {
		if (klasseContainer == null) {
			klasseContainer = new KlasseContainer();
			for (KlassePojo klassePojo: KlasseLaso.getAll()) {
				KlasseLaso klasseLaso = new KlasseLaso(klassePojo);
				klasseContainer.addBean(klasseLaso);
			}
		}
		return klasseContainer;		
	}
	
	public static BeanItemContainer<KlasseLaso> getAlleKlassen(BeanItemContainer<KlasseLaso> unsortedContainer) {
		
		BeanItemContainer<KlasseLaso> eintraege = new BeanItemContainer<KlasseLaso>(KlasseLaso.class);
		
		for (KlasseLaso klassenItem : unsortedContainer.getItemIds()) {
			eintraege.addBean(klassenItem);
		}
		eintraege.sort(new Object[] {KlassePojo.KLASSENNAME_COLUMN}, new boolean[] {true});
				
		BeanItemContainer<KlasseLaso> resultSchueler = new BeanItemContainer<KlasseLaso>(KlasseLaso.class);
		resultSchueler.addAll(eintraege.getItemIds());
		
		return resultSchueler;
	}
	
	public static KlasseLaso getKlasseByLehrer(LehrerPojo lehrer) {
		for (KlasseLaso aKlasse: getInstance().getItemIds()) {
			if (aKlasse.getKlassenlehrer() != null && aKlasse.getKlassenlehrer().getId().equals(lehrer.getId())) {
				return aKlasse;
			}
		}
		return null;			 
	}
	
	public void deleteKlasse(KlasseLaso klasse) {
		FossaLaso.deleteIfExists(klasse.getPojo());
		for (KlasseLaso aKlasse : getInstance().getItemIds()) {
			if (klasse.getId().equals(aKlasse.getId())) {
				klasseContainer.removeItem(aKlasse);
				return;
			}
		}
	}
}
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

import java.io.Serializable;

import org.fossa.rolp.data.klasse.KlassePojo;
import org.fossa.rolp.data.lehrer.LehrerPojo;
import org.fossa.rolp.data.schueler.SchuelerContainer;
import org.fossa.rolp.data.schueler.SchuelerLaso;
import org.fossa.rolp.data.zuordnung.fachschueler.ZuordnungFachSchuelerContainer;
import org.fossa.rolp.data.zuordnung.fachschueler.ZuordnungFachSchuelerLaso;
import org.fossa.vaadin.laso.FossaLaso;

import com.vaadin.data.util.BeanItemContainer;

public class FachContainer extends BeanItemContainer<FachLaso> implements Serializable {
	
	private static final long serialVersionUID = -1561855055662151443L;
	
	public static final String ZUGEWIESENE_SCHUELER_COLUMN_LABEL = "Anzahl der zugewiesenen Sch�ler";
	public static final String DYNAMIC_GENERATED_COLUMN_ZUGEWIESENE_SCHUELER_DER_KLASSE = "zugewieseneSchuelerAnzahlDerKlasse";
	public static final String ZUGEWIESENE_SCHUELER_DER_KLASSE_COLUMN_LABEL = "davon aus der eigenen Klasse";	
	public static final String FACHLEHRER_EINS_COLUMN_LABEL = "1. Fachlehrer";
	public static final String FACHLEHRER_ZWEI_COLUMN_LABEL = "2. Fachlehrer";
	public static final String ERLEDIGTE_FACHEINSCHAETZUNGEN_COLUMN_LABEL = "Einsch�tzungen erledigt";

	public static final String[] NATURAL_FORM_PFLICHTFACH_ORDER = new String[] {
		FachPojo.FACHBEZEICHNUNG_COLUMN,
		FachPojo.FACHLEHRER_EINS_COLUMN,
		FachPojo.FACHLEHRER_ZWEI_COLUMN,
	};
	
	public static final String[] NATURAL_FORM_KURS_ORDER = new String[] {
		FachPojo.FACHBEZEICHNUNG_COLUMN, 
		FachPojo.FACHLEHRER_EINS_COLUMN,
		FachPojo.FACHLEHRER_ZWEI_COLUMN,
	};
	
	public static final String[] NATURAL_PFLICHTFACH_ORDER = new String[] {
		FachPojo.FACHBEZEICHNUNG_COLUMN, 
		FachLaso.FACHLEHRER_EINS_COLUMN,
		FachLaso.FACHLEHRER_ZWEI_COLUMN,
	};
	
	public static final String[] PFLICHTFACH_HEADERS = new String[] {
		FachPojo.FACHBEZEICHNUNG_COLUMN,
		FACHLEHRER_EINS_COLUMN_LABEL,
		FACHLEHRER_ZWEI_COLUMN_LABEL,
	};
	
	public static final String[] NATURAL_KURS_ORDER = new String[] {
		FachPojo.FACHBEZEICHNUNG_COLUMN, 
		FachLaso.FACHLEHRER_EINS_COLUMN,
		FachLaso.FACHLEHRER_ZWEI_COLUMN,
		FachLaso.ZUGEWIESENE_SCHUELER_COLUMN,
		DYNAMIC_GENERATED_COLUMN_ZUGEWIESENE_SCHUELER_DER_KLASSE,
	};

	public static final String[] KURS_HEADERS = new String[] {
		FachPojo.FACHBEZEICHNUNG_COLUMN, 
		FACHLEHRER_EINS_COLUMN_LABEL,
		FACHLEHRER_ZWEI_COLUMN_LABEL,
		ZUGEWIESENE_SCHUELER_COLUMN_LABEL,
		ZUGEWIESENE_SCHUELER_DER_KLASSE_COLUMN_LABEL,
	};
	
	public static final String[] NATURAL_FACH_ORDER = new String[] {
		FachPojo.FACHBEZEICHNUNG_COLUMN,
		FachLaso.KLASSE_COLUMN,
		FachLaso.FACHLEHRER_EINS_COLUMN,
		FachLaso.FACHLEHRER_ZWEI_COLUMN,
		FachLaso.ERLEDIGTE_FACHEINSCHAETZUNGEN_COLUMN,
	};

	public static final String[] FACH_HEADERS = new String[] {
		FachPojo.FACHBEZEICHNUNG_COLUMN, 
		FachLaso.KLASSE_COLUMN,
		FACHLEHRER_EINS_COLUMN_LABEL,
		FACHLEHRER_ZWEI_COLUMN_LABEL,
		ERLEDIGTE_FACHEINSCHAETZUNGEN_COLUMN_LABEL,
	};
	
	private static FachContainer fachContainer;

	private FachContainer() {
		super(FachLaso.class);
	}
	
	public static FachContainer getInstance() {
		if (fachContainer == null) {
			fachContainer = new FachContainer();
			for (FachPojo fachPojo: FachLaso.getAll()) {
				FachLaso fachLaso = new FachLaso(fachPojo);
				fachContainer.addBean(fachLaso);
			}
		}
		return fachContainer;
	}
	
	public static BeanItemContainer<FachLaso> getAllPflichtfaecherOfKlasse(KlassePojo klasse) {
		BeanItemContainer<FachLaso> pflichtfaecherOfKlasse = new BeanItemContainer<FachLaso>(FachLaso.class);
		BeanItemContainer<SchuelerLaso> alleSchueler = SchuelerContainer.getAllSchuelerOfKlasse(klasse);
		if (alleSchueler.size() == 0) {
			return pflichtfaecherOfKlasse;
		}
		SchuelerLaso schueler = alleSchueler.firstItemId();
		for (ZuordnungFachSchuelerLaso zuordnungFS: ZuordnungFachSchuelerContainer.getInstance().getItemIds()) {
			if (zuordnungFS.getSchueler().getId().equals(schueler.getId()) && zuordnungFS.getFach().getFachtyp().isPflichtfach()) {
				pflichtfaecherOfKlasse.addBean(new FachLaso(zuordnungFS.getFach()));
			}
		}
		return pflichtfaecherOfKlasse;   
	}

	public static BeanItemContainer<FachLaso> getAllKurse() {
		BeanItemContainer<FachLaso> kurse = new BeanItemContainer<FachLaso>(FachLaso.class);
		for (FachLaso fach: getInstance().getItemIds()) {
			if (fach.getFachtyp().isKurs()) {
				kurse.addBean(fach);
			}
		}
		return kurse;   
	}

	public static BeanItemContainer<FachLaso> getFaecherByLehrer(LehrerPojo fachlehrer) {
		BeanItemContainer<FachLaso> faecher = new BeanItemContainer<FachLaso>(FachLaso.class);
		for (FachLaso fach: getInstance().getItemIds()) {
			if (((fach.getFachlehrerEins() != null) && (fach.getFachlehrerEins()).getId().equals(fachlehrer.getId())) || ((fach.getFachlehrerZwei() != null) && (fach.getFachlehrerZwei().getId().equals(fachlehrer.getId())))) {
				faecher.addBean(fach);
			}
		}		
		return faecher;
	}
	
	public static FachLaso getFachLasoAktuell(FachPojo fachPojo) {
		for (FachLaso fach : FachContainer.getInstance().getItemIds()) {
			if (fach.getId().equals(fachPojo.getId())) {
				return fach;
			}
		}
		return null;
	}
	
	public void deleteFach(FachLaso fach) {
		FossaLaso.deleteIfExists(fach.getPojo());
		fachContainer = null;
		getInstance();
	}
}
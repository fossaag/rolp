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

import java.io.Serializable;

import org.fossa.rolp.data.klasse.KlassePojo;
import org.fossa.vaadin.laso.FossaLaso;

import com.vaadin.data.util.BeanItemContainer;

public class SchuelerContainer extends BeanItemContainer<SchuelerLaso> implements Serializable {
	
	private static final long serialVersionUID = -1561855055662151443L;
	
	public static final String DYNAMIC_GENERATED_COLUMN_SCHUELEREINSCHAETZUNG = "schuelereinschaetzung";
	public static final String DYNAMIC_GENERATED_COLUMN_FACHEINSCHAETZUNG = "facheinschaetzung";
	public static final String DYNAMIC_GENERATED_COLUMN_SCHUELEREINSCHAETZUNG_LABEL = "Individuelle Einschätzung";
	public static final String DYNAMIC_GENERATED_COLUMN_FACHEINSCHAETZUNG_LABEL = "Facheinschätzungen";
	
	public static final String[] NATURAL_COL_ORDER = new String[] {
		SchuelerPojo.VORNAME_COLUMN,
		SchuelerPojo.NAME_COLUMN,
	};

	public static final String[] COL_HEADERS = new String[] {
		SchuelerPojo.VORNAME_COLUMN,
		SchuelerPojo.NAME_COLUMN,
	};
	
	public static final String[] NATURAL_FORM_ORDER = new String[] {
		SchuelerPojo.VORNAME_COLUMN,
		SchuelerPojo.NAME_COLUMN,
	};
	
	
	public static final String[] VERSETZUNGSVERMERK_COL_ORDER = new String[] {
		SchuelerPojo.VORNAME_COLUMN,
		SchuelerPojo.NAME_COLUMN,
		SchuelerPojo.VERSETZUNGSVERMERK_COLUMN,
	};
	
	public static final String[] VERSETZUNGSVERMERK_COL_HEADERS = new String[] {
		SchuelerPojo.VORNAME_COLUMN,
		SchuelerPojo.NAME_COLUMN,
		SchuelerPojo.VERSETZUNGSVERMERK_COLUMN,
	};
	
	public static final String[] VERSETZUNGSVERMERK_FORM_ORDER = new String[] {
		SchuelerPojo.VERSETZUNGSVERMERK_COLUMN,
	};
	
	public static final String[] CHECKLIST_COL_ORDER = new String[] {
		SchuelerPojo.VORNAME_COLUMN,
		SchuelerPojo.NAME_COLUMN,
		DYNAMIC_GENERATED_COLUMN_SCHUELEREINSCHAETZUNG,
		DYNAMIC_GENERATED_COLUMN_FACHEINSCHAETZUNG,
	};

	public static final String[] CHECKLIST_COL_HEADERS = new String[] {
		SchuelerPojo.VORNAME_COLUMN,
		SchuelerPojo.NAME_COLUMN,
		DYNAMIC_GENERATED_COLUMN_SCHUELEREINSCHAETZUNG_LABEL,
		DYNAMIC_GENERATED_COLUMN_FACHEINSCHAETZUNG_LABEL,
	};

	private static SchuelerContainer schuelerContainer;
	
	private SchuelerContainer() {
		super(SchuelerLaso.class);
	};
	
	public static BeanItemContainer<SchuelerLaso> getAllSchuelerOfKlasse(KlassePojo klasse) {
		BeanItemContainer<SchuelerLaso> schuelerOfKlasse = new BeanItemContainer<SchuelerLaso>(SchuelerLaso.class);
		for (SchuelerLaso schueler: getInstance().getItemIds()){	
			if((schueler.getKlasse()!=null) && (klasse!=null) && (schueler.getKlasse().getId().equals(klasse.getId()))){
				schuelerOfKlasse.addBean(schueler);
			}
		}
		schuelerOfKlasse.sort(new Object[] {SchuelerPojo.NAME_COLUMN}, new boolean[] {true});
		return schuelerOfKlasse;   
	}

	public static SchuelerContainer getInstance() {
		if (schuelerContainer == null) {
			schuelerContainer = new SchuelerContainer();
			for (SchuelerPojo schuelerPojo: SchuelerLaso.getAll()) {
				SchuelerLaso schuelerLaso = new SchuelerLaso(schuelerPojo);
				schuelerContainer.addBean(schuelerLaso);
			}
		}
		return schuelerContainer;
		
	}
	
	public static SchuelerLaso getSchuelerLasoAktuell(SchuelerPojo schuelerPojo) {
		for (SchuelerLaso schueler : SchuelerContainer.getInstance().getItemIds()) {
			if (schueler.getId().equals(schuelerPojo.getId())) {
				return schueler;
			}
		}
		return null;
	}

	public void deleteSchueler(SchuelerLaso schueler) {
		FossaLaso.deleteIfExists(schueler.getPojo());
		for (SchuelerLaso aSchueler : getInstance().getItemIds()) {
			if (schueler.getId().equals(aSchueler.getId())) {
				schuelerContainer.removeItem(aSchueler);
				return;
			}
		}
	}
}
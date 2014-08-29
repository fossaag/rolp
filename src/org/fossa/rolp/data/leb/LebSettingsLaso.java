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

import java.util.Date;
import java.util.List;

import org.fossa.rolp.data.klasse.halbjahr.HalbjahrPojo;
import org.fossa.rolp.util.DateUtil;
import org.fossa.vaadin.laso.FossaLaso;

public class LebSettingsLaso extends FossaLaso {

	public static final String ZEUGNISAUSGABEDATUM_COLUMN = "zeugnisausgabedatumString";
	public static final String HALBJAHR_COLUMN = "halbjahrString";
	
	private LebSettingsPojo lebsettings;

	public LebSettingsLaso(LebSettingsPojo lebsettings) {
		this.lebsettings = lebsettings;
	}
	
	public LebSettingsLaso() {
		this.lebsettings = new LebSettingsPojo();
	}
	
	public HalbjahrPojo getHalbjahr() {
		return lebsettings.getHalbjahr();
	}

	public void setHalbjahr(HalbjahrPojo halbjahr) {
		lebsettings.setHalbjahr(halbjahr);
		writeToDatabase();
	}
	
	public String getHalbjahrString() {
		if (lebsettings.getHalbjahr()!= null) {
			return lebsettings.getHalbjahr().getHalbjahr();
		} else {
			return " - ";
		}
	}
	
	public Date getZeugnisausgabedatum() {
		return lebsettings.getZeugnisausgabedatum();
	}

	public void setZeugnisausgabedatum(Date zeugnisausgabedatum) {
		lebsettings.setZeugnisausgabedatum(DateUtil.cutTimeOffDate(zeugnisausgabedatum));
		writeToDatabase();
	}
	
	public Integer getAnzahlErsteKlassen() {
		return lebsettings.getAnzahlErsteKlassen();
	}

	public void setAnzahlErsteKlassen(Integer anzahlErsteKlassen) {
		lebsettings.setAnzahlErsteKlassen(anzahlErsteKlassen);
		writeToDatabase();
	}

	public Integer getLetzteKlassenstufe() {
		return lebsettings.getLetzteKlassenstufe();
	}

	public void setLetzteKlassenstufe(Integer letzteKlassenstufe) {
		lebsettings.setLetzteKlassenstufe(letzteKlassenstufe);
		writeToDatabase();
	}
	
	public String getZeugnisausgabedatumString() {
		return DateUtil.showDateString(getZeugnisausgabedatum());
	}

	@Override
	public Long getId() {
		return lebsettings.getId();
	}

	@Override
	public void setId(Long id) {
		lebsettings.setId(id);
		writeToDatabase();
	}

	@Override
	public LebSettingsPojo getPojo() {
		return lebsettings;
	}

	@Override
	protected void writeToDatabase() {
		writeToDatabase(lebsettings);		
	}
	
	@SuppressWarnings("unchecked")
	public static List<LebSettingsPojo> getAll() {
		return (List<LebSettingsPojo>) getAll(LebSettingsPojo.class);
	}
}

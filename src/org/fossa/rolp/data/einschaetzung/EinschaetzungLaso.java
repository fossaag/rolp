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

package org.fossa.rolp.data.einschaetzung;

import java.util.List;

import org.fossa.vaadin.laso.FossaLaso;

public class EinschaetzungLaso extends FossaLaso {

	private static final long serialVersionUID = -1296436051855969848L;
	
	private EinschaetzungPojo einschaetzung;
	
	public EinschaetzungLaso(EinschaetzungPojo einschaetzung) {
		this.einschaetzung= einschaetzung;
	}

	public EinschaetzungLaso() {
		einschaetzung = new EinschaetzungPojo();
	}
	
	public EinschaetzungPojo getPojo() {
		return einschaetzung;		
	}
	
	public Long getId() {
		return einschaetzung.getId();
	}

	public void setId(Long id) {
		einschaetzung.setId(id);
		writeToDatabase();
	}
	
	public String getEinschaetzungstext() {
		return einschaetzung.getEinschaetzungstext();
	}

	public void setEinschaetzungstext(String einschaetzungstext) {
		einschaetzung.setEinschaetzungstext(einschaetzungstext);
		writeToDatabase();
	}
	
	public boolean getErledigt() {
		return einschaetzung.getErledigt();
	}

	public void setErledigt(boolean erledigt) {
		einschaetzung.setErledigt(erledigt);
		writeToDatabase();
	}

	@Override
	protected void writeToDatabase() {
		writeToDatabase(einschaetzung);
	}
	@SuppressWarnings("unchecked")
	public static List<EinschaetzungPojo> getAll() {
		return (List<EinschaetzungPojo>) getAll(EinschaetzungPojo.class);
	}
}

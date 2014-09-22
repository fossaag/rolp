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

package org.fossa.rolp.data.fach.fachdefinition.leb;

import java.util.List;

import org.fossa.rolp.data.fach.fachdefinition.FachdefinitionPojo;
import org.fossa.vaadin.laso.FossaLaso;

public class FachbezeichnungLebLaso extends FossaLaso {

	private static final long serialVersionUID = 3632462663608568784L;
	
	private FachbezeichnungLebPojo fachbezeichnungLeb;
	
	public FachbezeichnungLebLaso(FachbezeichnungLebPojo fachbezeichnungLebPojo) {
		this.fachbezeichnungLeb = fachbezeichnungLebPojo;
	}

	public FachbezeichnungLebLaso() {
		fachbezeichnungLeb = new FachbezeichnungLebPojo();
	}
	
	public FachbezeichnungLebPojo getPojo() {
		return fachbezeichnungLeb;		
	}
	
	public Long getId() {
		return fachbezeichnungLeb.getId();
	}

	public void setId(Long id) {
		fachbezeichnungLeb.setId(id);
		writeToDatabase();
	}	

	public String getBezeichnung() {
		return fachbezeichnungLeb.getBezeichnung();
	}
	
	public void setBezeichnung(String bezeichnung) {
		fachbezeichnungLeb.setBezeichnung(bezeichnung);
		writeToDatabase();
	}
	
	public FachdefinitionPojo getFachdefinition() {
		return fachbezeichnungLeb.getFachdefinition();
	}

	public void setFachdefinition(FachdefinitionPojo fachdefinition) {
		fachbezeichnungLeb.setFachdefinition(fachdefinition);
		writeToDatabase();
	}

	@Override
	protected void writeToDatabase() {
		writeToDatabase(fachbezeichnungLeb);
	}

	@SuppressWarnings("unchecked")
	public static List<FachbezeichnungLebPojo> getAll() {
		return (List<FachbezeichnungLebPojo>) getAll(FachbezeichnungLebPojo.class);
	}

}

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

package org.fossa.rolp.data.fach.fachdefinition;

import java.util.List;

import org.fossa.rolp.data.fach.fachtyp.FachtypPojo;
import org.fossa.vaadin.laso.FossaLaso;

public class FachdefinitionLaso extends FossaLaso {

	private static final long serialVersionUID = -7908223534757446802L;

	public static final String FACHTYP_COLUMN = "fachtypString";
	
	private FachdefinitionPojo fachdefinition;
	
	public FachdefinitionLaso(FachdefinitionPojo fachdefinitionPojo) {
		this.fachdefinition = fachdefinitionPojo;
	}

	public FachdefinitionLaso() {
		fachdefinition = new FachdefinitionPojo();
	}
	
	public FachdefinitionPojo getPojo() {
		return fachdefinition;		
	}
	
	public Long getId() {
		return fachdefinition.getId();
	}

	public void setId(Long id) {
		fachdefinition.setId(id);
		writeToDatabase();
	}	

	public String getFachbezeichnung() {
		return fachdefinition.getFachbezeichnung();
	}
	
	public void setFachbezeichnung(String fachbezeichnung) {
		fachdefinition.setFachbezeichnung(fachbezeichnung);
		writeToDatabase();
	}
	
	public FachtypPojo getFachtyp(){
		return fachdefinition.getFachtyp();
	}
	
	public void setFachtyp(FachtypPojo fachtyp){
		fachdefinition.setFachtyp(fachtyp);
		writeToDatabase();
	}
	
	public String getFachtypString() {
		return getFachtyp().getFachtyp();
	}
	
	@Override
	protected void writeToDatabase() {
		writeToDatabase(fachdefinition);
	}

	@SuppressWarnings("unchecked")
	public static List<FachdefinitionPojo> getAll() {
		return (List<FachdefinitionPojo>) getAll(FachdefinitionPojo.class);
	}
}

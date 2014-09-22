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

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.fossa.rolp.data.fach.fachdefinition.FachdefinitionPojo;
import org.fossa.vaadin.data.FossaPojo;

@Entity
@Table(name = "dynamic_fachbezeichnung_leb")
public class FachbezeichnungLebPojo implements FossaPojo, Serializable {

	private static final long serialVersionUID = -4760966467749881171L;
	
	public static final String ID_COLUMN = "id";
	public static final String BEZEICHNUNG_COLUMN = "bezeichnung";
	public static final String FACHDEFINITION_COLUMN = "fachdefinition";

	@Id
	@GeneratedValue
	@Column(name = ID_COLUMN)
	private Long id;
	@Column(name = BEZEICHNUNG_COLUMN)
	private String bezeichnung = "";
	@ManyToOne()
	@JoinColumn(name = FACHDEFINITION_COLUMN + FossaPojo.FOREIGNKEY_SUFFIX)
	private FachdefinitionPojo fachdefinition = null;

	public FachbezeichnungLebPojo() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getBezeichnung() {
		return bezeichnung;
	}

	public void setBezeichnung(String bezeichnung) {
		this.bezeichnung = bezeichnung;
	}

	public FachdefinitionPojo getFachdefinition() {
		return fachdefinition;
	}

	public void setFachdefinition(FachdefinitionPojo fachdefinition) {
		this.fachdefinition = fachdefinition;
	}
	
}

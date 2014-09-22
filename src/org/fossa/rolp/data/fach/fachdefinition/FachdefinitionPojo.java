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

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.fossa.rolp.data.fach.fachtyp.FachtypPojo;
import org.fossa.vaadin.data.FossaPojo;

@Entity
@Table(name = "dynamic_fachdefinition")
public class FachdefinitionPojo implements FossaPojo, Serializable {

	private static final long serialVersionUID = -549023713122071979L;
	
	public static final String ID_COLUMN = "id";
	public static final String FACHBEZEICHNUNG_COLUMN = "fachbezeichnung";
	public static final String FACHTYP_COLUMN = "fachtyp";

	@Id
	@GeneratedValue
	@Column(name = ID_COLUMN)
	private Long id;
	@Column(name = FACHBEZEICHNUNG_COLUMN)
	private String fachbezeichnung = "";
	@ManyToOne
	@JoinColumn(name = FACHTYP_COLUMN + FossaPojo.FOREIGNKEY_SUFFIX)
	private FachtypPojo fachtyp;
	
	public FachdefinitionPojo() {

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public String getFachbezeichnung() {
		return fachbezeichnung;
	}

	public void setFachbezeichnung(String fachbezeichnung) {
		this.fachbezeichnung = fachbezeichnung;
	}

	public FachtypPojo getFachtyp() {
		return fachtyp;
	}

	public void setFachtyp(FachtypPojo fachtyp) {
		this.fachtyp = fachtyp;
	}
}

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

package org.fossa.rolp.data.fach.fachtyp;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.fossa.vaadin.data.FossaPojo;

@Entity
@Table(name = "static_fachtyp")
public class FachtypPojo implements FossaPojo, Serializable {

	private static final long serialVersionUID = -1952758871285609060L;

	public static final String ID_COLUMN = "id";
	public static final String FACHTYP_COLUMN = "fachtyp";

	@Id
	@GeneratedValue
	@Column(name = ID_COLUMN)
	private Long id;
	@Column(name = FACHTYP_COLUMN)
	private String fachtyp = "";

	public FachtypPojo() {

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFachtyp() {
		return fachtyp;
	}

	public void setFachtyp(String fachtyp) {
		this.fachtyp = fachtyp;
	}
	
	public boolean isPflichtfach() {
		return getId() == 1;
	}

	public boolean isKurs() {
		return getId() == 2;
	}

}

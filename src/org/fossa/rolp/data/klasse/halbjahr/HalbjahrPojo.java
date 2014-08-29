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

package org.fossa.rolp.data.klasse.halbjahr;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.fossa.vaadin.data.FossaPojo;

@Entity
@Table(name = "static_halbjahr")
public class HalbjahrPojo implements FossaPojo, Serializable {

	private static final long serialVersionUID = -599551207080150709L;
	
	public static final String ID_COLUMN = "id";
	public static final String HALBJAHR_COLUMN = "halbjahr";

	@Id
	@GeneratedValue
	@Column(name = ID_COLUMN)
	private Long id;
	@Column(name = HALBJAHR_COLUMN)
	private String halbjahr = "";

	public HalbjahrPojo() {

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getHalbjahr() {
		return halbjahr;
	}

	public void setHalbjahr(String halbjahr) {
		this.halbjahr = halbjahr;
	}
	
	public boolean isErstesHalbjahr() {
		return getId() == 1;
	}

	public boolean isZweitesHalbjahr() {
		return getId() == 2;
	}
}
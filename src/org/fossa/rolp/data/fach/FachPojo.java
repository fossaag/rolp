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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.fossa.rolp.data.fach.fachdefinition.FachdefinitionPojo;
import org.fossa.rolp.data.lehrer.LehrerPojo;
import org.fossa.vaadin.data.FossaPojo;

@Entity
@Table(name="dynamic_fach")
public class FachPojo implements FossaPojo, Serializable {

	private static final long serialVersionUID = 8656811931816148805L;

	public static final String ID_COLUMN = "id";
	public static final String FACHDEFINITION_COLUMN = "fachdefinition";
	public static final String FACHLEHRER_EINS_COLUMN = "fachlehrerEins";
	public static final String FACHLEHRER_ZWEI_COLUMN = "fachlehrerZwei";
	public static final String FACHLEHRER_DREI_COLUMN = "fachlehrerDrei";
	
	@Id
	@GeneratedValue
	@Column(name = ID_COLUMN)
	private Long id;
	@ManyToOne
	@JoinColumn(name = FACHDEFINITION_COLUMN + FossaPojo.FOREIGNKEY_SUFFIX)
	private FachdefinitionPojo fachdefinition;
	@ManyToOne()
	@JoinColumn(name = FACHLEHRER_EINS_COLUMN + FossaPojo.FOREIGNKEY_SUFFIX)
	private LehrerPojo fachlehrerEins = null;
	@ManyToOne()
	@JoinColumn(name = FACHLEHRER_ZWEI_COLUMN + FossaPojo.FOREIGNKEY_SUFFIX)
	private LehrerPojo fachlehrerZwei = null;
	@ManyToOne()
	@JoinColumn(name = FACHLEHRER_DREI_COLUMN + FossaPojo.FOREIGNKEY_SUFFIX)
	private LehrerPojo fachlehrerDrei = null;

	public FachPojo() {
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public FachdefinitionPojo getFachdefinition() {
		return fachdefinition;
	}

	public void setFachdefinition(FachdefinitionPojo fachdefinition) {
		this.fachdefinition = fachdefinition;
	}

	public LehrerPojo getFachlehrerEins() {
		return fachlehrerEins;
	}

	public void setFachlehrerEins(LehrerPojo fachlehrerEins) {
		this.fachlehrerEins = fachlehrerEins;
	}

	public LehrerPojo getFachlehrerZwei() {
		return fachlehrerZwei;
	}

	public void setFachlehrerZwei(LehrerPojo fachlehrerZwei) {
		this.fachlehrerZwei = fachlehrerZwei;
	}

	public LehrerPojo getFachlehrerDrei() {
		return fachlehrerDrei;
	}

	public void setFachlehrerDrei(LehrerPojo fachlehrerDrei) {
		this.fachlehrerDrei = fachlehrerDrei;
	}

}

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

package org.fossa.rolp.data.zuordnung.fachschueler;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.fossa.rolp.data.einschaetzung.EinschaetzungPojo;
import org.fossa.rolp.data.fach.FachPojo;
import org.fossa.rolp.data.schueler.SchuelerPojo;
import org.fossa.vaadin.data.FossaPojo;

@Entity
@Table(name="dynamic_zuordnung_fach_schueler")
public class ZuordnungFachSchuelerPojo implements FossaPojo, Serializable {

	private static final long serialVersionUID = 5811326310781450596L;
	
	public static final String ID_COLUMN = "id";
	public static final String FACH_COLUMN = "fach";
	public static final String SCHUELER_COLUMN = "schueler";
	public static final String FACHEINSCHAETZUNG_COLUMN = "facheinschaetzung";
	
	@Id
	@GeneratedValue
	@Column(name = ID_COLUMN)
	private Long id;
	@ManyToOne()
	@JoinColumn(name = FACH_COLUMN + FossaPojo.FOREIGNKEY_SUFFIX)
	private FachPojo fach;
	@ManyToOne()
	@JoinColumn(name = SCHUELER_COLUMN + FossaPojo.FOREIGNKEY_SUFFIX)
	private SchuelerPojo schueler;
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = FACHEINSCHAETZUNG_COLUMN + FossaPojo.FOREIGNKEY_SUFFIX)
	private EinschaetzungPojo facheinschaetzung = null;

	public ZuordnungFachSchuelerPojo() {
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public FachPojo getFach() {
		return fach;
	}

	public void setFach(FachPojo fach) {
		this.fach = fach;
	}

	public SchuelerPojo getSchueler() {
		return schueler;
	}

	public void setSchueler(SchuelerPojo schueler) {
		this.schueler = schueler;
	}

	public EinschaetzungPojo getFacheinschaetzung() {
		return facheinschaetzung;
	}

	public void setFacheinschaetzung(EinschaetzungPojo facheinschaetzung) {
		this.facheinschaetzung = facheinschaetzung;
	}
}

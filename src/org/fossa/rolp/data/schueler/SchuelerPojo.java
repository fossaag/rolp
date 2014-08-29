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

package org.fossa.rolp.data.schueler;

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
import org.fossa.rolp.data.klasse.KlassePojo;
import org.fossa.vaadin.data.FossaPojo;

@Entity
@Table(name="dynamic_schueler")
public class SchuelerPojo implements FossaPojo, Serializable {

	private static final long serialVersionUID = 8656811931816148805L;

	public static final String ID_COLUMN = "id";
	public static final String VORNAME_COLUMN = "vorname";
	public static final String NAME_COLUMN = "name";
	public static final String VERSETZUNGSVERMERK_COLUMN = "versetzungsvermerk";
	public static final String KLASSE_COLUMN = "klasse";
	public static final String SCHUELEREINSCHAETZUNG_COLUMN = "schuelereinschaetzung";
	
	@Id
	@GeneratedValue
	@Column(name = ID_COLUMN)
	private Long id;
	@Column(name = VORNAME_COLUMN)
	private String vorname = "";
	@Column(name = NAME_COLUMN)
	private String name = "";
	@Column(name = VERSETZUNGSVERMERK_COLUMN)
	private String versetzungsvermerk = "";	
	@ManyToOne()
	@JoinColumn(name = KLASSE_COLUMN + FossaPojo.FOREIGNKEY_SUFFIX)
	private KlassePojo klasse;
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = SCHUELEREINSCHAETZUNG_COLUMN + FossaPojo.FOREIGNKEY_SUFFIX)
	private EinschaetzungPojo schuelereinschaetzung = null;
	
	public SchuelerPojo() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getVorname() {
		return vorname;
	}

	public void setVorname(String vorname) {
		this.vorname = vorname;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getVersetzungsvermerk() {
		return versetzungsvermerk;
	}

	public void setVersetzungsvermerk(String versetzungsvermerk) {
		this.versetzungsvermerk = versetzungsvermerk;
	}

	public KlassePojo getKlasse() {
		return klasse;
	}

	public void setKlasse(KlassePojo klasse) {
		this.klasse = klasse;
	}

	public EinschaetzungPojo getSchuelereinschaetzung() {
		return schuelereinschaetzung;
	}

	public void setSchuelereinschaetzung(EinschaetzungPojo schuelereinschaetzung) {
		this.schuelereinschaetzung = schuelereinschaetzung;
	}
}

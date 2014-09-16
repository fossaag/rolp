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

package org.fossa.rolp.data.klasse;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.fossa.rolp.data.einschaetzung.EinschaetzungPojo;
import org.fossa.rolp.data.lehrer.LehrerPojo;
import org.fossa.vaadin.data.FossaPojo;

@Entity
@Table(name="dynamic_klasse")
public class KlassePojo implements FossaPojo, Serializable {

	private static final long serialVersionUID = 8656811931816148805L;

	public static final String ID_COLUMN = "id";
	public static final String KLASSENNAME_COLUMN = "klassenname";
	public static final String KLASSENLEHRER_COLUMN = "klassenlehrer";
	public static final String KLASSENEINSCHAETZUNG_COLUMN = "klasseneinschaetzung";
	public static final String SCHUELER_COLUMN = "schueler";
	
	
	@Id
	@GeneratedValue
	@Column(name = ID_COLUMN)
	private Long id;
	@Column(name = KLASSENNAME_COLUMN)
	private String klassenname = "";
	@OneToOne()
	@JoinColumn(name = KLASSENLEHRER_COLUMN + FossaPojo.FOREIGNKEY_SUFFIX)
	private LehrerPojo klassenlehrer = null;
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = KLASSENEINSCHAETZUNG_COLUMN + FossaPojo.FOREIGNKEY_SUFFIX)
	private EinschaetzungPojo klasseneinschaetzung = null;

	
	public KlassePojo() {
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getKlassenname() {
		return klassenname;
	}

	public void setKlassenname(String klassenname) {
		this.klassenname = klassenname;
	}
	
	public LehrerPojo getKlassenlehrer() {
		return klassenlehrer;
	}

	public void setKlassenlehrer(LehrerPojo klassenlehrer) {
		this.klassenlehrer = klassenlehrer;
	}

	public EinschaetzungPojo getKlasseneinschaetzung() {
		return klasseneinschaetzung;
	}

	public void setKlasseneinschaetzung(EinschaetzungPojo klasseneinschaetzung) {
		this.klasseneinschaetzung = klasseneinschaetzung;
	}
}

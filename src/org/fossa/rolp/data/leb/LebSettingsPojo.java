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

package org.fossa.rolp.data.leb;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.fossa.rolp.data.leb.halbjahr.HalbjahrPojo;
import org.fossa.vaadin.data.FossaPojo;

@Entity
@Table(name="dynamic_leb_settings")
public class LebSettingsPojo implements FossaPojo, Serializable {

	private static final long serialVersionUID = -7143565305280254394L;
	
	public static final String ID_COLUMN = "id";
	public static final String ZEUGNISAUSGABEDATUM_COLUMN = "zeugnisausgabedatum";
	public static final String HALBJAHR_COLUMN = "halbjahr";
	public static final String ANZAHL_ERSTE_KLASSEN_COLUMN = "anzahlErsteKlassen";
	public static final String LETZTE_KLASSENSTUFE_COLUMN = "letzteKlassenstufe";
	
	@Id
	@GeneratedValue
	@Column(name = ID_COLUMN)
	private Long id;
	@Column(name = ZEUGNISAUSGABEDATUM_COLUMN)
	private Date zeugnisausgabedatum;
	@ManyToOne
	@JoinColumn(name = HALBJAHR_COLUMN + FossaPojo.FOREIGNKEY_SUFFIX)
	private HalbjahrPojo halbjahr;
	@Column(name = ANZAHL_ERSTE_KLASSEN_COLUMN)
	private Integer anzahlErsteKlassen;
	@Column(name = LETZTE_KLASSENSTUFE_COLUMN)
	private Integer letzteKlassenstufe;
	
	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;		
	}

	public Date getZeugnisausgabedatum() {
		return zeugnisausgabedatum;
	}

	public void setZeugnisausgabedatum(Date zeugnisausgabedatum) {
		this.zeugnisausgabedatum = zeugnisausgabedatum;
	}

	public HalbjahrPojo getHalbjahr() {
		return halbjahr;
	}

	public void setHalbjahr(HalbjahrPojo halbjahr) {
		this.halbjahr = halbjahr;
	}

	public Integer getAnzahlErsteKlassen() {
		return anzahlErsteKlassen;
	}

	public void setAnzahlErsteKlassen(Integer anzahlErsteKlassen) {
		this.anzahlErsteKlassen = anzahlErsteKlassen;
	}

	public Integer getLetzteKlassenstufe() {
		return letzteKlassenstufe;
	}

	public void setLetzteKlassenstufe(Integer letzteKlassenstufe) {
		this.letzteKlassenstufe = letzteKlassenstufe;
	}

}

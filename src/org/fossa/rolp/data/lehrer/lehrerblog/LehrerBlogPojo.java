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

package org.fossa.rolp.data.lehrer.lehrerblog;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.fossa.rolp.data.lehrer.LehrerPojo;
import org.fossa.vaadin.data.FossaPojo;

@Entity
@Table(name="dynamic_lehrerblog")
public class LehrerBlogPojo implements FossaPojo, Serializable {

	private static final long serialVersionUID = 1312103163911643242L;
	
	public static final String ID_COLUMN = "id";
	public static final String LEHRER_COLUMN = "lehrer";
	public static final String EREIGNIS_COLUMN = "ereignis";
	public static final String TIMESTAMP_COLUMN = "timestamp";	
		
	@Id
	@GeneratedValue
	@Column(name = ID_COLUMN)
	private Long id;
	@ManyToOne()
	@JoinColumn(name = LEHRER_COLUMN + FossaPojo.FOREIGNKEY_SUFFIX)
	private LehrerPojo lehrer = null;
	@Column(name = EREIGNIS_COLUMN)
	private String ereignis;
	@Column(name = TIMESTAMP_COLUMN)
	private Date timestamp;
	
	public LehrerBlogPojo() {
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LehrerPojo getLehrer() {
		return lehrer;
	}

	public void setLehrer(LehrerPojo lehrer) {
		this.lehrer = lehrer;
	}

	public String getEreignis() {
		return ereignis;
	}

	public void setEreignis(String ereignis) {
		this.ereignis = ereignis;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

}

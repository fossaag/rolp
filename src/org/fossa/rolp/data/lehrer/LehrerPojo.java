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

package org.fossa.rolp.data.lehrer;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.fossa.vaadin.auth.data.FossaUserPojo;
import org.fossa.vaadin.data.FossaPojo;

@Entity
@Table(name="dynamic_lehrer")
public class LehrerPojo implements FossaPojo, Serializable {


	private static final long serialVersionUID = 1464217282440473093L;
	
	public static final String ID_COLUMN = "id";
	public static final String USER_COLUMN = "user";
	public static final String KLASSE_COLUMN = "klasse";
	public static final String MAIL_COLUMN = "mail";
	public static final String IS_ADMIN_COLUMN = "isAdmin";
		
	@Id
	@GeneratedValue
	@Column(name = ID_COLUMN)
	private Long id;
	@OneToOne()
	@JoinColumn(name = USER_COLUMN + FossaPojo.FOREIGNKEY_SUFFIX)
	private FossaUserPojo user = new FossaUserPojo();
	@Column(name = MAIL_COLUMN)
	private String mail = "";
	@Column(name = IS_ADMIN_COLUMN)
	private Boolean isAdmin = false;
	
	public LehrerPojo() {
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public FossaUserPojo getUser() {
		return user;
	}

	public void setUser(FossaUserPojo user) {
		this.user = user;
	}
	
	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public Boolean getIsAdmin() {
		return isAdmin;
	}

	public void setIsAdmin(Boolean isAdmin) {
		this.isAdmin = isAdmin;
	}

}

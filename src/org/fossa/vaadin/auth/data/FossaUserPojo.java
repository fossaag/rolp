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

package org.fossa.vaadin.auth.data;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.fossa.vaadin.data.FossaPojo;


@Entity
@Table(name="dynamic_authentication_user")
public class FossaUserPojo implements Serializable, FossaPojo {

	private static final long serialVersionUID = 1313567289184569134L;
	
	public static final String ID_COLUMN = "id";
	public static final String USERNAME_COLUMN = "username";
	public static final String PASSWORD_COLUMN = "password";
	public static final String FIRSTNAME_COLUMN = "firstname";
	public static final String LASTNAME_COLUMN = "lastname";
	
	@Id
	@GeneratedValue
	@Column(name = ID_COLUMN)
	private Long id;
	@Column(name = USERNAME_COLUMN)
	private String username = "";
	@Column(name = PASSWORD_COLUMN)
	private String password = "";
	@Column(name = FIRSTNAME_COLUMN)
	private String firstname = "";
	@Column(name = LASTNAME_COLUMN)
	private String lastname = "";

	public FossaUserPojo() {

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
}

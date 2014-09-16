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

import java.util.List;

import org.fossa.vaadin.laso.FossaLaso;

public class FossaUserLaso extends FossaLaso {

	private static final long serialVersionUID = -2995412865467231895L;

	private FossaUserPojo user;
	
	public static final String FORM_PW_ONE_COLUMN = "formPwOne";
	public static final String FORM_PW_TWO_COLUMN = "formPwTwo";
	private String formPwOne = "";
	private String formPwTwo = "";

	public FossaUserLaso(FossaUserPojo user) {
		this.user = user;
	}
	
	public FossaUserLaso() {
		this.user = new FossaUserPojo();
	}
	
	public Long getId() {
		return user.getId();
	}

	public void setId(Long id) {
		user.setId(id);
		writeToDatabase();
	}	

	public String getUsername() {
		return user.getUsername();
	}
	
	public void setUsername(String username) {
		user.setUsername(username);
		writeToDatabase();
	}

	public String getFirstname() {
		return user.getFirstname();
	}
	
	public void setFirstname(String firstname) {
		user.setFirstname(firstname);
		writeToDatabase();
	}

	public String getLastname() {
		return user.getLastname();
	}
	
	public void setLastname(String lastname) {
		user.setLastname(lastname);
		writeToDatabase();
	}

	public String getPassword() {
		return user.getPassword();
	}
	
	public void setPassword(String password) {
		user.setPassword(password);
		writeToDatabase();
	}

	@Override
	public FossaUserPojo getPojo() {
		return user;
	}

	@Override
	protected void writeToDatabase() {
		writeToDatabase(user);
	}

	@SuppressWarnings("unchecked")
	public static List<FossaUserPojo> getAll() {
		return (List<FossaUserPojo>) getAll(FossaUserPojo.class);
	}

	public String getFormPwOne() {
		return formPwOne;
	}

	public void setFormPwOne(String formPwOne) {
		this.formPwOne = formPwOne;
	}

	public String getFormPwTwo() {
		return formPwTwo;
	}

	public void setFormPwTwo(String formPwTwo) {
		this.formPwTwo = formPwTwo;
	}

}

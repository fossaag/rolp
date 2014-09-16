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

import java.util.List;

import org.fossa.vaadin.auth.data.FossaUserLaso;
import org.fossa.vaadin.laso.FossaLaso;

public class LehrerLaso extends FossaLaso {

	private static final long serialVersionUID = 7486587832858711319L;
	
	protected FossaUserLaso userLaso;
	private LehrerPojo lehrer;

	public LehrerLaso(LehrerPojo lehrer) {
		this.lehrer = lehrer;
		this.userLaso = new FossaUserLaso(lehrer.getUser());
	}
	
	public LehrerLaso() {
		this.lehrer = new LehrerPojo();
		this.userLaso = new FossaUserLaso(lehrer.getUser());
	}
	
	public void setUser(FossaUserLaso userLaso) {
		this.userLaso = userLaso;
		lehrer.setUser(userLaso.getPojo());
		writeToDatabase();
	}

	public FossaUserLaso getUser() {
		return userLaso;
	}
	
	public Long getId() {
		return lehrer.getId();
	}

	public void setId(Long id) {
		lehrer.setId(id);
		writeToDatabase();
	}	

	public String getUsername() {
		return userLaso.getUsername();
	}
	
	public void setUsername(String username) {
		userLaso.setUsername(username);
		writeToDatabase();
	}

	public String getFirstname() {
		return userLaso.getFirstname();
	}
	
	public void setFirstname(String firstname) {
		userLaso.setFirstname(firstname);
		writeToDatabase();
	}

	public String getLastname() {
		return userLaso.getLastname();
	}
	
	public void setLastname(String lastname) {
		userLaso.setLastname(lastname);
		writeToDatabase();
	}

	public String getPassword() {
		return userLaso.getPassword();
	}
	
	public void setPassword(String password) {
		userLaso.setPassword(password);
		writeToDatabase();
	}

	public String getMail() {
		return lehrer.getMail();
	}
	
	public void setMail(String mail) {
		lehrer.setMail(mail);
		writeToDatabase();
	}
	
	public String getFormPwOne() {
		return userLaso.getFormPwOne();
	}

	public void setFormPwOne(String formPwOne) {
		userLaso.setFormPwOne(formPwOne);
	}

	public String getFormPwTwo() {
		return userLaso.getFormPwTwo();
	}

	public void setFormPwTwo(String formPwTwo) {
		userLaso.setFormPwTwo(formPwTwo);
	}
	
	@Override
	public LehrerPojo getPojo() {
		return lehrer;
	}

	@Override
	protected void writeToDatabase() {
		writeToDatabase(lehrer);
	}

	@SuppressWarnings("unchecked")
	public static List<LehrerPojo> getAll() {
		return (List<LehrerPojo>) getAll(LehrerPojo.class);
	}
}

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

package org.fossa.vaadin.auth;

import java.io.Serializable;

import org.fossa.rolp.util.PasswortEncryptUtil;
import org.fossa.vaadin.FossaApplication;
import org.fossa.vaadin.auth.data.FossaUserContainer;
import org.fossa.vaadin.auth.data.FossaUserLaso;
import org.fossa.vaadin.auth.ui.FossaAuthorizerFrameBuilder;
import org.fossa.vaadin.auth.ui.FossaLoginScreen;

import com.vaadin.ui.Layout;

public class FossaAuthorizer implements Serializable {

	private static final long serialVersionUID = 6061585018138134110L;

	public FossaApplication fossaApp = null;
	
	private FossaUserLaso user = null;

	private Layout layout;

    public FossaAuthorizer(FossaApplication fossaApp, Layout layout) {
    	this.fossaApp = fossaApp;
    	this.layout = layout;
    }
    
	public boolean checkAuthorization(String checkedClass) {
		if (user==null) {
			forceLogin("");
			return false;
		}
		unlockApplication();
		return true;
	}
	
	public void forceLogin(String message) {
		fossaApp.getMainWindow().addWindow(new FossaLoginScreen(fossaApp, this, message));
	}
	
	private void unlockApplication() {
		layout.addComponent(new FossaAuthorizerFrameBuilder(this, user));
		fossaApp.buildMainLayout();
	}
	
	public void lockApplication() {
		fossaApp.close();
	}
	
	public void unlockUser() {
		if (user != null) {
			user.unlock();
		}
	}
	
	public void tryToLogin(String username, String password) {
		String md5Password = PasswortEncryptUtil.encryptPassword(password);
		for (FossaUserLaso tempUser: FossaUserContainer.getInstance().getItemIds()) {
			if (username.equals(tempUser.getUsername()) && md5Password.equals(tempUser.getPassword())) {
				if (tempUser.isLocked()) {
					forceLogin("Benutzer '" + username + "' wird bereits verwendet!");
					return;
				}
				this.user = tempUser;
				user.lock();
				fossaApp.setUser(user);
				unlockApplication();
				return;
			}
		}
		forceLogin("Anmeldung fehlgeschlagen!");
	}
}

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

package org.fossa.vaadin.auth.ui;


import org.fossa.vaadin.FossaApplication;
import org.fossa.vaadin.auth.FossaAuthorizer;
import org.fossa.vaadin.ui.FossaWindow;

import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.CustomLayout;
import com.vaadin.ui.Form;
import com.vaadin.ui.Label;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;

public class FossaLoginScreen extends FossaWindow implements Button.ClickListener {
	private static final long serialVersionUID = -2249592053367777867L;
	
	private FossaAuthorizer authorizer;
	private TextField username = new TextField("Benutzername:");
	private PasswordField password = new PasswordField("Passwort:");
	private Form formLoginScreen;

	public FossaLoginScreen(FossaApplication app, FossaAuthorizer authorizer, String error) {
		super(app);
		setStyleName("login");
		
		this.authorizer = authorizer;
		setCaption("Bitte melden Sie sich an!");
		setWidth("500px");
		center();
		CustomLayout layout = new CustomLayout("./login/loginScreen");
		setContent(layout);

		Label errorlabel = new Label(error);
		formLoginScreen = new Form();
		
		formLoginScreen.addField(username, username);
	    username.focus();
		formLoginScreen.addField(password, password);
		formLoginScreen.getField(username).setRequired(true);
		formLoginScreen.getField(password).setRequired(true);
		
		layout.addComponent(formLoginScreen, "form");
		layout.addComponent(errorlabel, "errorlabel");

		Button login = new Button("Anmelden");
		layout.addComponent(login, "login");
		login.addListener((Button.ClickListener) this);
		login.setClickShortcut(KeyCode.ENTER);

	}
	
	@Override
	public void buttonClick(ClickEvent event) {		
		authorizer.tryToLogin(formLoginScreen.getField(username).getValue().toString(), formLoginScreen.getField(password).getValue().toString());
		close();		
	}

	@Override
	public void unlockLaso() {		
	}
	
}

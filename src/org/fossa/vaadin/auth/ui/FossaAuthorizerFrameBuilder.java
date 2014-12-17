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

import org.fossa.rolp.data.lehrer.LehrerContainer;
import org.fossa.rolp.data.lehrer.LehrerLaso;
import org.fossa.rolp.ui.lehrer.LehrerAnlegen;
import org.fossa.vaadin.FossaApplication;
import org.fossa.vaadin.auth.FossaAuthorizer;
import org.fossa.vaadin.auth.data.FossaUserLaso;

import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

public class FossaAuthorizerFrameBuilder extends VerticalLayout implements Button.ClickListener {
	
	private static final long serialVersionUID = 5615816402453319540L;
	
	private FossaAuthorizer fossaAuthorizer;
	private Button logout;
	private Button editUser;
	private FossaUserLaso user;
	
	public FossaAuthorizerFrameBuilder(FossaAuthorizer fossaAuthorizer, FossaUserLaso user) {
		this.fossaAuthorizer = fossaAuthorizer;
		if (user==null) {
			return;
		}
		this.user = user;
		TextField loginUser = new TextField();
		loginUser.setValue("Angemeldet als: " + user.getFirstname() + " " + user.getLastname());
		loginUser.setReadOnly(true);
		loginUser.setStyleName("loginUser");
		loginUser.setWidth("400px");
		logout = new Button("Ausloggen");
		logout.addListener((Button.ClickListener) this);
		logout.setWidth("150px");
		editUser = new Button("Benutzerkonto");
		editUser.addListener((Button.ClickListener) this);
		editUser.setWidth("150px");
		setSpacing(true);
		addComponent(loginUser);
		HorizontalLayout buttonBattery = new HorizontalLayout();
		buttonBattery.addComponent(editUser);
		buttonBattery.addComponent(logout);
		addComponent(buttonBattery);
		setComponentAlignment(loginUser, Alignment.MIDDLE_RIGHT);
		setComponentAlignment(buttonBattery, Alignment.MIDDLE_RIGHT);
	}
	
	@Override
	public void buttonClick(ClickEvent event) {
		final Button source = event.getButton();
		if (source == logout) {
			fossaAuthorizer.lockApplication();
		} else if (source == editUser) {
			LehrerLaso lehrer = LehrerContainer.getLehrerByUser(user);
			getApplication().getMainWindow().addWindow(new LehrerAnlegen((FossaApplication) getApplication(), lehrer));
		}
	}
}

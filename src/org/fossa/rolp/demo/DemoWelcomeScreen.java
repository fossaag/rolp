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

package org.fossa.rolp.demo;

import org.fossa.rolp.RolpApplication;
import org.fossa.vaadin.auth.FossaAuthorizer;
import org.fossa.vaadin.ui.FossaWindow;

import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.terminal.ThemeResource;
import com.vaadin.terminal.gwt.server.WebBrowser;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.CustomLayout;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.Label;
import com.vaadin.ui.Select;

public class DemoWelcomeScreen extends FossaWindow implements Button.ClickListener {

	private static final long serialVersionUID = 7446873422087050954L;
	
	private Select roleSelect = new Select();
	private Button okButton = new Button("Demo starten!", (Button.ClickListener) this);
	
	private FossaAuthorizer authorizer;
	private RolpApplication app;
	
	private static final String DEMO_LOGO_ROLP_PATH = "images/rolp_logo.png";
	private static final String DEMO_LOGO_FOSSA_PATH = "images/fossa_logo_banner.png";

	public DemoWelcomeScreen(RolpApplication app, FossaAuthorizer authorizer) {
		super(app);
		this.app = app;
		setStyleName("demo");
		setWidth("900px");
		center();
		this.authorizer = authorizer;
		CustomLayout layout = new CustomLayout("./demo/welcomeScreen");
		CustomLayout layoutTop = new CustomLayout("./demo/welcomeScreenTop");
		CustomLayout layoutMiddle = new CustomLayout("./demo/welcomeScreenMiddle");
		CustomLayout layoutBottom = new CustomLayout("./demo/welcomeScreenBottom");
		
		
		Embedded logoRolp = new Embedded(null, new ThemeResource(DEMO_LOGO_ROLP_PATH));
		logoRolp.setType(Embedded.TYPE_IMAGE);
		logoRolp.setWidth("100px");
		logoRolp.setHeight("96px");
		
		Label rolpDemoText = new Label("<h2>Wilkommen</h2>Dies ist eine Demoversion der Software ROLP. Wählen Sie eine Rolle aus, um in das entsprechende Szenario zu gelangen. Der Schulleiter hat eine eigene Verwaltungsoberfläche, wo er die Lehrer und Klassen überblicken und zuweisen kann. Als Klassenlehrer und Fachlehrer gelangt man auf den Startbildschirm. Von da aus gelangt man zum Klassenlehrer-Dashboard und dem Fachlehrer-Dashboard. Der Klassenlehrer hat Zugang zu beiden Dashboards, da er im Normalfall auch Fächer unterrichtet.", Label.CONTENT_XHTML);
		Label githubLinkDummy = new Label("ROLP auf <a href=\"https://github.com/fossaag/rolp\" target=\"_blank\">GitHub.com</a>", Label.CONTENT_XHTML);
		
		Embedded logoFossa = new Embedded(null, new ThemeResource(DEMO_LOGO_FOSSA_PATH));
		logoFossa.setType(Embedded.TYPE_IMAGE);
		logoFossa.setWidth("150px");
		
		Label roleSelectCaption = new Label("Bitte wählen Sie eine Rolle aus:", Label.CONTENT_XHTML);
		
		BeanItemContainer<String> roles = new BeanItemContainer<String>(String.class);
		roles.addBean("Fachlehrer");
		roles.addBean("Klassenlehrer");
		roles.addBean("Schulleiter");
		roleSelect.setContainerDataSource(roles);
		roleSelect.setWidth("200px");
		

		layoutTop.addComponent(logoRolp, "logoRolp");
		layoutTop.addComponent(rolpDemoText, "rolpDemoText");
		layoutTop.addComponent(logoFossa, "logoFossa");
		layout.addComponent(layoutTop, "layoutTop");
		
		
		layoutMiddle.addComponent(roleSelectCaption, "roleSelectCaption");
		layoutMiddle.addComponent(roleSelect, "roleSelect");
		layoutMiddle.addComponent(okButton, "okButton");
		layout.addComponent(layoutMiddle, "layoutMiddle");
		
		layoutBottom.addComponent(githubLinkDummy, "githubLinkDummy");
		layout.addComponent(layoutBottom, "layoutBottom");
		setContent(layout);
	}
	
	@Override
	public void buttonClick(ClickEvent event) {
		final Button source = event.getButton();
		WebBrowser b = (WebBrowser) app.getMainWindow().getTerminal();
		String ip = b.getAddress();
		System.out.println(ip + " - " + b.getCurrentDate());
		if (source == okButton) {
			if (roleSelect.getValue() == null) {
				showNotification("Bitte wählen Sie eine Option!");
			} else if (roleSelect.getValue().equals("Fachlehrer")) {
				DemoSceneBuilder.buildFachlehrerScene(app, authorizer);
				close();
			} else if (roleSelect.getValue().equals("Klassenlehrer")) {
				DemoSceneBuilder.buildKlassenlehrerScene(app, authorizer);
				close();
			} else if (roleSelect.getValue().equals("Schulleiter")) {
				DemoSceneBuilder.buildSchulleiterScene(app, authorizer);
				close();
			}
		}
	}

	@Override
	public void unlockLaso() {
	}

}

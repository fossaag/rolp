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

package org.fossa.rolp;

import org.fossa.rolp.data.klasse.KlasseContainer;
import org.fossa.rolp.data.leb.LebSettingsContainer;
import org.fossa.rolp.data.lehrer.LehrerContainer;
import org.fossa.rolp.data.lehrer.LehrerPojo;
import org.fossa.rolp.demo.DemoWelcomeScreen;
import org.fossa.rolp.ui.dashboard.AdminDashboard;
import org.fossa.rolp.ui.dashboard.FachlehrerDashboard;
import org.fossa.rolp.ui.dashboard.KlassenlehrerDashboard;
import org.fossa.rolp.ui.fach.KurseZuordnen;
import org.fossa.rolp.ui.fach.PflichtfaecherlisteAnzeigen;
import org.fossa.rolp.ui.fach.fachdefinition.FachdefinitionlisteAnzeigen;
import org.fossa.rolp.ui.fach.fachdefinition.leb.FachbezeichnungenLeblisteAnzeigen;
import org.fossa.rolp.ui.klasse.klasseanlegen.KlassenlisteAnzeigen;
import org.fossa.rolp.ui.schueler.SchuelerlisteAnzeigen;
import org.fossa.rolp.ui.schueler.versetzungsvermerk.VersetzungsvermerklisteAnzeigen;
import org.fossa.rolp.ui.zuordnung.fachschueler.FachschuelerlisteAnzeigen;
import org.fossa.rolp.ui.zuordnung.fachschueler.SchuelerfachlisteAnzeigen;
import org.fossa.rolp.util.Config;
import org.fossa.vaadin.FossaApplication;
import org.fossa.vaadin.auth.FossaAuthorizer;
import org.fossa.vaadin.auth.data.FossaUserLaso;
import org.fossa.vaadin.ui.FossaWindow;
import org.vaadin.jouni.animator.client.ui.VAnimatorProxy.AnimType;

import com.vaadin.terminal.ThemeResource;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.CustomLayout;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.NativeButton;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.Window.CloseEvent;
import com.vaadin.ui.Window.CloseListener;

public class RolpApplication extends FossaApplication implements Button.ClickListener, CloseListener {
	
	private static final long serialVersionUID = 5703739221227738736L;
	
	private VerticalLayout authorizerLayout = new VerticalLayout();
	private final FossaAuthorizer authorizer = new FossaAuthorizer(this, authorizerLayout);
	
	private Button klassenlehrerButton = new NativeButton ("");
	private Button fachlehrerButton = new NativeButton ("");

	private CustomLayout layout = new CustomLayout("./applicationMainLayout/appMainLayout");
	private CustomLayout main = new CustomLayout("./applicationMainLayout/mainLayout");	
	private CustomLayout headlineApp = new CustomLayout("./applicationMainLayout/headlineApp");

	private AdminDashboard adminDashboard;
	
	private static final String MAINPAGE_PANEL_ANMELDEN_LOGO_PATH = "images/rolp_logo.png";
	
	@Override
    public void init() {
		setMainWindow(new Window("Rolp"));
		setTheme("rolp");
		String appSeverity = Config.getAppSeverity();
		if (appSeverity.equals("prod")) {			
			authorizer.checkAuthorization();
		} else if (appSeverity.equals("demo")) {
			demoWelcome();
		}
	}

	@Override
	public void buildMainLayout() {
		Embedded logo = new Embedded(null, new ThemeResource(MAINPAGE_PANEL_ANMELDEN_LOGO_PATH));
		logo.setType(Embedded.TYPE_IMAGE);
		logo.setWidth("100px");
		logo.setHeight("96px");
		headlineApp.addComponent(logo,"logo");
		headlineApp.addComponent(authorizerLayout,"authorizerLayout");
		headlineApp.addStyleName("headlineApp");
		
		layout.addComponent(headlineApp,"headlineApp");
		
		getMainWindow().setContent(layout);		
		getMainWindow().addComponent(getAnimator());
		
		if(getLoginLehrer().getIsAdmin()){
			layout.addComponent(getAdminDashboard(), "admin");
		} else {
			layout.addComponent(main, "main");
			buildAppLayout();
		}
	}
	
	@Override
	public void buttonClick(ClickEvent event) {
		final Button source = event.getButton();
		if (source == klassenlehrerButton) {
			if (LebSettingsContainer.getLebSettings().getId() == null) {
				getMainWindow().showNotification("Noch keine Definition für das Halbjahr vorhanden. Bitte wenden Sie sich an den Administrator!");
				return;
			}
			if (KlasseContainer.getKlasseByLehrer(getLoginLehrer()) == null) {
				getMainWindow().showNotification("Ihnen ist keine Klasse zugewiesen");
				return;
			}
			showKlassenlehrerDashboard();
		} else if (source == fachlehrerButton) {
			if (LebSettingsContainer.getLebSettings().getId() == null) {
				getMainWindow().showNotification("Noch keine Definition für das Halbjahr vorhanden. Bitte wenden Sie sich an den Administrator!");
				return;
			}
			showFachlehrerDashboard();
		}
	}

	private void buildAppLayout() {
		main.addComponent(klassenlehrerButton, "klassenlehrerButton");	
		main.addComponent(fachlehrerButton, "fachlehrerButton");
		
		klassenlehrerButton.addListener((Button.ClickListener) this);
		klassenlehrerButton.setWidth("301px");
		klassenlehrerButton.setStyleName("blackboardButtonKlassenlehrer");
		
		fachlehrerButton.addListener((Button.ClickListener) this);
		fachlehrerButton.setWidth("301px");
		fachlehrerButton.setStyleName("blackboardButtonFachlehrer");
	}
	
	private AdminDashboard getAdminDashboard() {
		if (adminDashboard == null) {
			adminDashboard = new AdminDashboard(this);
		}
		return adminDashboard;
	}

	private void showKlassenlehrerDashboard() {
		KlassenlehrerDashboard klassenlehrerDashboard = new KlassenlehrerDashboard(this);
		getMainWindow().addWindow(klassenlehrerDashboard);
		getAnimator().animate(klassenlehrerDashboard, AnimType.FADE_IN).setDuration(300);
	}
	
	private void showFachlehrerDashboard() {
		FachlehrerDashboard fachlehrerDashboard = new FachlehrerDashboard(this);
		getMainWindow().addWindow(fachlehrerDashboard);
		getAnimator().animate(fachlehrerDashboard, AnimType.FADE_IN).setDuration(300);
	}

	public FossaAuthorizer getAuthorizer() {
		return authorizer;
	}
	
	public LehrerPojo getLoginLehrer() {
		return LehrerContainer.getLehrerByUser((FossaUserLaso) getUser()).getPojo();
	}

	private void unlockLasos(final Window window) {
		if (window instanceof FossaWindow) {
			((FossaWindow) window).unlockLaso();
			System.out.println(window.getCaption() + " was closed");
		}
	}
	
	@Override
	public void close() {
		super.close();
		for (Window window : getWindows()) {
			for (Window subwindow : window.getChildWindows()) {
				unlockLasos(subwindow);
			}
		}
		authorizer.unlockUser();
	}

	@Override
	public void windowClose(CloseEvent event) {
		final Window source = event.getWindow();
		unlockLasos(source);
		refreshPage();
		for (Window mainWindow : getWindows()) {
			for (Window window : mainWindow.getChildWindows()) {
				if (window instanceof SchuelerlisteAnzeigen) {
					((SchuelerlisteAnzeigen) window).refreshPage();
				} else if (window instanceof PflichtfaecherlisteAnzeigen) {
					((PflichtfaecherlisteAnzeigen) window).refreshPage();
				} else if (window instanceof KurseZuordnen) {
					((KurseZuordnen) window).refreshPage();
				} else if (window instanceof VersetzungsvermerklisteAnzeigen) {
					((VersetzungsvermerklisteAnzeigen) window).refreshPage();
				} else if (window instanceof FachschuelerlisteAnzeigen) {
					((FachschuelerlisteAnzeigen) window).refreshPage();
				} else if (window instanceof SchuelerfachlisteAnzeigen) {
					((SchuelerfachlisteAnzeigen) window).refreshPage();
				} else if (window instanceof KlassenlehrerDashboard) {
					((KlassenlehrerDashboard) window).refreshPage();
				} else if (window instanceof FachlehrerDashboard) {
					((FachlehrerDashboard) window).refreshPage();
				} else if (window instanceof FachdefinitionlisteAnzeigen) {
					((FachdefinitionlisteAnzeigen) window).refreshPage();
				} else if (window instanceof FachbezeichnungenLeblisteAnzeigen) {
					((FachbezeichnungenLeblisteAnzeigen) window).refreshPage();
				} else if (window instanceof KlassenlisteAnzeigen) {
					((KlassenlisteAnzeigen) window).refreshPage();
				}
			}
		}
	}
	
	private void refreshPage() {
		if (adminDashboard != null) {
			getAdminDashboard().refreshPage();
		}
	}
	
	protected void openSubwindow(FossaWindow window) {
		getMainWindow().addWindow(window);
	}
	
	public void demoWelcome() {
		getMainWindow().addWindow(new DemoWelcomeScreen(this, authorizer));
		
	}
}

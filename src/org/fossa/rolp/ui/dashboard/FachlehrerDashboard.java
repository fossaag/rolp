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

package org.fossa.rolp.ui.dashboard;

import org.fossa.rolp.RolpApplication;
import org.fossa.rolp.data.fach.FachLaso;
import org.fossa.rolp.ui.fach.FaecherList;
import org.fossa.rolp.ui.lehrer.LehrerBlog;
import org.fossa.rolp.ui.zuordnung.fachschueler.FachschuelerList;
import org.fossa.rolp.ui.zuordnung.fachschueler.FachschuelerlisteAnzeigen;
import org.fossa.vaadin.ui.FossaWindow;

import com.vaadin.terminal.Sizeable;
import com.vaadin.terminal.ThemeResource;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.CustomLayout;
import com.vaadin.ui.Embedded;

public class FachlehrerDashboard extends FossaWindow implements Button.ClickListener {

	private static final long serialVersionUID = -7023614013705197996L;
	
	private Button windowCloseButton = new Button("Schlieﬂen", (Button.ClickListener) this);
	private FaecherList faecherList;
	private Button fachspezifischeDetailsButton = new Button("Facheinsch‰tzungen ansehen", (Button.ClickListener) this);
	public RolpApplication app;
	private LehrerBlog lehrerBlog;
	private FachschuelerlisteAnzeigen fachschuelerlisteAnzeigen;
	
	private CustomLayout horizontalButtonBattery = new CustomLayout("./lehrerDashboards/fachlehrerHorizontalButtonBattery");
	
	private static final String MAINPAGE_PANEL_ANMELDEN_LOGO_PATH = "images/rolp_logo.png";

	public FachlehrerDashboard(RolpApplication app) {
		super(app);
		this.app = app;
		setCaption(" - FachlehrerDashboard - ");
		setWidth("100%");
		setHeight("100%");
		
		buildButtonBatteries();
		
		CustomLayout layout = new CustomLayout("./lehrerDashboards/fachLehrerDashboardMain");
		setContent(layout);
		
		CustomLayout headline = new CustomLayout("./lehrerDashboards/headline");
		headline.addStyleName("headline");
		
		Embedded logo = new Embedded(null, new ThemeResource(MAINPAGE_PANEL_ANMELDEN_LOGO_PATH));
		logo.setType(Embedded.TYPE_IMAGE);
		logo.setWidth("100px");
		logo.setHeight("96px");
		
		headline.addComponent(logo,"logo");
		
		CustomLayout faecherListe = new CustomLayout("./lehrerDashboards/liste");
		faecherListe.addStyleName("liste");
		faecherList = getFaecherList();
		faecherList.setHeight("250px");
		faecherList.setStyleName("list");
		faecherListe.addComponent(faecherList, "list");
		faecherListe.setHeight("260px");
		
		layout.addComponent(faecherListe,"liste");
		layout.addComponent(headline,"headline");
		layout.addComponent(horizontalButtonBattery,"horizontalButtonBattery");
		
		lehrerBlog = getLehrerBlog();
		layout.addComponent(lehrerBlog,"blog");
		windowCloseButton.setWidth("100%");
		layout.addComponent(windowCloseButton, "windowCloseButton");
		
	}
	
	private void buildButtonBatteries() {
		horizontalButtonBattery.removeAllComponents();
		horizontalButtonBattery.addStyleName("horizontalButtonBattery");
		fachspezifischeDetailsButton.setWidth(95, Sizeable.UNITS_PERCENTAGE);
		horizontalButtonBattery.addComponent(fachspezifischeDetailsButton, "fachspezifischeDetailsButton");
	}
	
	private LehrerBlog getLehrerBlog() {
		lehrerBlog = new LehrerBlog(app);
		return lehrerBlog;
	}

	private FaecherList getFaecherList() {
		if (faecherList == null) {
			faecherList = new FaecherList(app);
		}
		return faecherList;
	}
	
	@Override
	public void buttonClick(ClickEvent event) {
		final Button source = event.getButton();
		refreshPage();
		if (source == windowCloseButton) {
			close();
		} else if (source == fachspezifischeDetailsButton) {
			FachLaso fach = (FachLaso) faecherList.getValue();
			if (fach == null) {
				app.getMainWindow().showNotification("kein Fach ausgew‰hlt");
				return;
			}
			fachspezifischeDetailsAnzeigen(fach);
		}
	}

	private void fachspezifischeDetailsAnzeigen(FachLaso fach) {
		getApplication().getMainWindow().addWindow(buildFachschuelerlistAnzeigen(fach));
	}

	private FossaWindow buildFachschuelerlistAnzeigen(FachLaso fach) {
		FachschuelerList fachschuelerListe = getFachschuelerList(fach);
		fachschuelerlisteAnzeigen = new FachschuelerlisteAnzeigen(app, fach.getPojo(), fachschuelerListe);
		return fachschuelerlisteAnzeigen;
	}
	
	private FachschuelerList getFachschuelerList(FachLaso fach) {
		return new FachschuelerList(app, fach);
	}
	
	@Override
	public void unlockLaso() {
	}
	
	public void refreshPage(){
		getFaecherList().refresh();
		getFaecherList().requestRepaintAll();
		getFaecherList().refreshRowCache();
		lehrerBlog.refreshBlogtext();
	}


}

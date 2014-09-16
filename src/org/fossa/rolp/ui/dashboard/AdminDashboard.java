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

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Date;

import org.fossa.rolp.RolpApplication;
import org.fossa.rolp.data.klasse.KlasseContainer;
import org.fossa.rolp.data.klasse.KlasseLaso;
import org.fossa.rolp.data.leb.LebSettingsContainer;
import org.fossa.rolp.data.leb.LebSettingsLaso;
import org.fossa.rolp.data.lehrer.LehrerContainer;
import org.fossa.rolp.data.lehrer.LehrerLaso;
import org.fossa.rolp.data.schueler.SchuelerContainer;
import org.fossa.rolp.data.schueler.SchuelerLaso;
import org.fossa.rolp.ui.klasse.KlassenlehrerZuordnen;
import org.fossa.rolp.ui.klasse.klasseanlegen.KlassenlisteAnzeigen;
import org.fossa.rolp.ui.leb.LebSettingsAnlegen;
import org.fossa.rolp.ui.lehrer.LehrerAnlegen;
import org.fossa.rolp.ui.lehrer.LehrerList;
import org.fossa.rolp.util.Config;
import org.fossa.rolp.util.LebCreator;
import org.fossa.rolp.util.UpgradeUtils;
import org.fossa.rolp.util.ZipHandler;
import org.fossa.vaadin.auth.data.FossaUserContainer;
import org.fossa.vaadin.auth.data.FossaUserLaso;
import org.fossa.vaadin.ui.FossaBooleanDialog;
import org.fossa.vaadin.ui.FossaWindow;
import org.fossa.vaadin.ui.exception.FossaLasoLockedException;

import com.vaadin.terminal.FileResource;
import com.vaadin.terminal.Sizeable;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CustomLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.Window.CloseEvent;
import com.vaadin.ui.Window.CloseListener;
import com.vaadin.ui.Window.Notification;

public class AdminDashboard extends CustomLayout implements Button.ClickListener, CloseListener {

	private static final long serialVersionUID = -6232766368970823202L;

	public RolpApplication app;
	
	private FossaBooleanDialog confirmUpgrade;	
	
	private Button lehrerAnlegenButton = new Button("Lehrer anlegen", (ClickListener) this);
	private Button lehrerBearbeitenButton = new Button("Lehrer bearbeiten", (ClickListener) this);
	private Button klassenlehrerZuweisenButton = new Button("Klassenlehrer zuweisen", (ClickListener) this);
	private Button klasseVerwaltenButton = new Button("Klassen verwalten", (ClickListener) this);
	private Button lebSettingsButton = new Button("Einstellungen", (ClickListener) this);
	private Button lebArchivierungButton = new Button("LEB-Archivierung", (ClickListener) this);
	private Button upgradeButton = new Button("Systemupgrade", (ClickListener) this);	
	private CustomLayout horizontalButtonBattery = new CustomLayout("./applicationMainLayout/adminHorizontalButtonBattery");
	private CustomLayout verticalButtonBattery = new CustomLayout("./applicationMainLayout/adminVerticalButtonBattery");

	private LehrerList lehrerList;

	public AdminDashboard(RolpApplication app) {
		super("./applicationMainLayout/adminLayout");
		this.app = app;

		buildButtonBattery();

		CustomLayout lehrerListe = new CustomLayout("./lehrerDashboards/liste");
		lehrerListe.addStyleName("liste");
		
		lehrerListe.addComponent(getLehrerList(), "list");
		lehrerListe.setHeight("260px");
		
		addComponent(lehrerListe,"liste");
		addComponent(horizontalButtonBattery, "horizontalButtonBattery");
		addComponent(verticalButtonBattery, "verticalButtonBattery");
	}

	private void buildButtonBattery() {
		lehrerAnlegenButton.setWidth(95, Sizeable.UNITS_PERCENTAGE);
		lehrerBearbeitenButton.setWidth(95, Sizeable.UNITS_PERCENTAGE);
		lebSettingsButton.setWidth(95, Sizeable.UNITS_PERCENTAGE);
		lebArchivierungButton.setWidth(95, Sizeable.UNITS_PERCENTAGE);
		klassenlehrerZuweisenButton.setWidth(95, Sizeable.UNITS_PERCENTAGE);
		klasseVerwaltenButton.setWidth(95, Sizeable.UNITS_PERCENTAGE);
		upgradeButton.setWidth(95, Sizeable.UNITS_PERCENTAGE);
		upgradeButton.setEnabled(
				LebSettingsContainer.getLebSettings().getZeugnisausgabedatum() != null
				&& 
				new Date().after(LebSettingsContainer.getLebSettings().getZeugnisausgabedatum())
		);
		horizontalButtonBattery.removeAllComponents();
		horizontalButtonBattery.addComponent(lehrerAnlegenButton, "lehrerAnlegenButton");
		horizontalButtonBattery.addComponent(lehrerBearbeitenButton, "lehrerBearbeitenButton");
		horizontalButtonBattery.addComponent(klassenlehrerZuweisenButton, "klassenlehrerZuweisenButton");
		horizontalButtonBattery.addComponent(klasseVerwaltenButton, "klasseVerwaltenButton");
		
		verticalButtonBattery.removeAllComponents();
		verticalButtonBattery.addComponent(lebSettingsButton, "lebSettingsButton");
		verticalButtonBattery.addComponent(lebArchivierungButton, "zipDownloadButton");
		verticalButtonBattery.addComponent(upgradeButton, "upgradeButton");		
	}

	@Override
	public void buttonClick(ClickEvent event) {
		final Button source = event.getButton();
		if (source == lehrerAnlegenButton) {
			openSubwindow(getLehrerAnlegen(null));
		} else if (source == lehrerBearbeitenButton) {
			if (getLehrerList().getValue() == null) {
				app.getMainWindow().showNotification("kein Lehrer ausgewählt");
				return;
			}
			LehrerLaso lehrer = (LehrerLaso) getLehrerList().getValue();
			openSubwindow(getLehrerAnlegen(lehrer));
		} else if (source == klassenlehrerZuweisenButton) {
			try {
				openSubwindow(getKlassenlehrerZuweisen());
			} catch (FossaLasoLockedException e) {
				getWindow().showNotification("LOCKED");
				return;
			}			
		} else if (source == klasseVerwaltenButton) {
			getKlassenlisteAnzeigen();
		} else if (source == lebSettingsButton) {
			openSubwindow(getLebSettingsAnlegen(LebSettingsContainer.getLebSettings()));
		} else if (source == upgradeButton) {
			confirmUpgrade = new FossaBooleanDialog(app, " - Bestätigung - ", "Wenn Sie das Systemupgrade durchführen, werden alle Zuordnungen, alle Fächer bzw. Kurse und alle Bestandteile der LEBs dauerhaft entfernt. Möchten Sie das Upgrade dennoch durchführen?", "Ja", "Nein");
			confirmUpgrade.addListener((CloseListener) this); 
			openSubwindow(confirmUpgrade);
		} else if (source == lebArchivierungButton) {
			for (FossaUserLaso user: FossaUserContainer.getInstance().getItemIds()) {
				if (user.isLocked() && LehrerContainer.getLehrerByUser(user).getPojo().getIsAdmin() && !app.getLoginLehrer().getUser().getId().equals(user.getId())) {
					app.getMainWindow().showNotification("Die LEB-Archivierung kann nicht ausgeführt werden, solange mehrere Administratoren angemeldet sind.");
					return;
				}
			}
			try {
				File zipFile = new File(Config.getLocalTempPath()+ "LEBs.zip");
				zipFile.delete();
				ZipHandler zipHandler = new ZipHandler(zipFile.getAbsolutePath());
				for (KlasseLaso klasse : KlasseContainer.getInstance().getItemIds()) {				
					for (SchuelerLaso schueler : SchuelerContainer.getAllSchuelerOfKlasse(klasse.getPojo()).getItemIds()) {
						String dateiname = "Klasse" + klasse.getKlassenname() + "_" + schueler.getVorname() + "_" + schueler.getName() + ".pdf";
						LebCreator lebCreator = new LebCreator(app, schueler, klasse, dateiname);
						System.out.println(lebCreator.getLebFilename());
						File lebFile = new File(Config.getLocalTempPath()+ lebCreator.getLebFilename());
						System.out.println(lebFile.getAbsolutePath());
						lebFile.createNewFile();
						OutputStream outputStream = new FileOutputStream(lebFile); 
						lebCreator.getPdfOutputStream().writeTo(outputStream);
						outputStream.close();
						zipHandler.addFile(lebFile.getAbsolutePath());
					}
				}
				zipHandler.finalizeZipFile();
				for (File file : zipHandler.getFiles()) {
					file.delete();
				}				
				FileResource zipFileresource = new FileResource(zipFile, app);
				app.getMainWindow().open(zipFileresource);
				
			} catch (Exception e) {
				app.getMainWindow().showNotification("Fehler bei der LEB-Archivierung", Notification.TYPE_ERROR_MESSAGE);
				e.printStackTrace();
			}
		
		}
	}
	
	private KlassenlehrerZuordnen getKlassenlehrerZuweisen() throws FossaLasoLockedException {
		return new KlassenlehrerZuordnen(app);
	}

	private LehrerList getLehrerList() {
		if (lehrerList == null) {
			lehrerList = new LehrerList(app);
			lehrerList.setHeight("250px");
			lehrerList.setStyleName("list");
		}
		return lehrerList;
	}

	private LebSettingsAnlegen getLebSettingsAnlegen(LebSettingsLaso lebSettings) {
		return new LebSettingsAnlegen(app, lebSettings);
	}

	private LehrerAnlegen getLehrerAnlegen(LehrerLaso lehrer) {
		if (lehrer == null) {
			lehrer = new LehrerLaso();
		}
		return new LehrerAnlegen(app, lehrer);
	}
	
	private void getKlassenlisteAnzeigen() {
		app.getMainWindow().addWindow(new KlassenlisteAnzeigen(app));
	}

	@Override	
	public void windowClose(CloseEvent event) {
		Window source = event.getWindow();
		if (source == confirmUpgrade) {
			if (confirmUpgrade.getDecision()) {
				try {
					UpgradeUtils.doSystemUpgrade(app, this, getLebSettingsAnlegen(LebSettingsContainer.getLebSettings()));
				} catch (Exception e) {
					e.printStackTrace();
					app.getMainWindow().showNotification("Fehler beim Löschen! Bitte wenden Sie sich an den Administrator!", Notification.TYPE_ERROR_MESSAGE);
				}				
			}
		}
	}
	
	protected void openSubwindow(FossaWindow window) {
		app.getMainWindow().addWindow(window);
	}
	
	public void refreshPage() {
		getLehrerList().refresh();
		buildButtonBattery();
	}
}
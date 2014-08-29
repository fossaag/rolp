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
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.fossa.rolp.RolpApplication;
import org.fossa.rolp.data.einschaetzung.EinschaetzungLaso;
import org.fossa.rolp.data.fach.FachContainer;
import org.fossa.rolp.data.fach.FachLaso;
import org.fossa.rolp.data.klasse.KlasseContainer;
import org.fossa.rolp.data.klasse.KlasseLaso;
import org.fossa.rolp.data.leb.LebSettingsContainer;
import org.fossa.rolp.data.leb.LebSettingsLaso;
import org.fossa.rolp.data.lehrer.LehrerContainer;
import org.fossa.rolp.data.lehrer.LehrerLaso;
import org.fossa.rolp.data.schueler.SchuelerContainer;
import org.fossa.rolp.data.schueler.SchuelerLaso;
import org.fossa.rolp.data.schueler.SchuelerPojo;
import org.fossa.rolp.data.zuordnung.fachschueler.ZuordnungFachSchuelerContainer;
import org.fossa.rolp.data.zuordnung.fachschueler.ZuordnungFachSchuelerHandler;
import org.fossa.rolp.ui.klasse.KlassenlehrerZuordnen;
import org.fossa.rolp.ui.leb.LebSettingsAnlegen;
import org.fossa.rolp.ui.lehrer.LehrerAnlegen;
import org.fossa.rolp.ui.lehrer.LehrerList;
import org.fossa.rolp.util.Config;
import org.fossa.rolp.util.KlassenstufenUtils;
import org.fossa.rolp.util.LebCreator;
import org.fossa.rolp.util.ZipHandler;
import org.fossa.vaadin.auth.data.FossaUserContainer;
import org.fossa.vaadin.auth.data.FossaUserLaso;
import org.fossa.vaadin.laso.FossaLaso;
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
	private Button lebSettingsButton = new Button("Einstellungen", (ClickListener) this);
	private Button zipDownloadButton = new Button("LEB-Archivierung", (ClickListener) this);
	private Button klassenlehrerZuweisenButton = new Button("Klassenlehrer zuweisen", (ClickListener) this);
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
		zipDownloadButton.setWidth(95, Sizeable.UNITS_PERCENTAGE);
		klassenlehrerZuweisenButton.setWidth(95, Sizeable.UNITS_PERCENTAGE);
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
		
		verticalButtonBattery.removeAllComponents();
		verticalButtonBattery.addComponent(lebSettingsButton, "lebSettingsButton");
		verticalButtonBattery.addComponent(zipDownloadButton, "zipDownloadButton");
		verticalButtonBattery.addComponent(upgradeButton, "upgradeButton");		
	}

	@Override
	public void buttonClick(ClickEvent event) {
		final Button source = event.getButton();
		if (source == lehrerAnlegenButton) {
			openSubwindow(getLehrerAnlegen(null));
		} else if (source == lehrerBearbeitenButton) {
			if (getLehrerList().getValue() != null) {
				LehrerLaso lehrer = (LehrerLaso) getLehrerList().getValue();
				openSubwindow(getLehrerAnlegen(lehrer));
			}
		} else if (source == klassenlehrerZuweisenButton) {
			try {
				openSubwindow(getKlassenlehrerZuweisen());
			} catch (FossaLasoLockedException e) {
				getWindow().showNotification("LOCKED");
				return;
			}			
		} else if (source == lebSettingsButton) {
			openSubwindow(getLebSettingsAnlegen(LebSettingsContainer.getLebSettings()));
		} else if (source == upgradeButton) {
			confirmUpgrade = new FossaBooleanDialog(app, " - Bestätigung - ", "Wenn Sie das Systemupgrade durchführen, werden alle Zuordnungen, alle Fächer bzw. Kurse und alle Bestandteile der LEBs dauerhaft entfernt. Möchten Sie das Upgrade dennoch durchführen?", "Ja", "Nein");
			confirmUpgrade.addListener((CloseListener) this); 
			openSubwindow(confirmUpgrade);
		} else if (source == zipDownloadButton) {
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

	private FossaWindow getLebSettingsAnlegen(LebSettingsLaso lebSettings) {
		return new LebSettingsAnlegen(app, lebSettings);
	}

	private LehrerAnlegen getLehrerAnlegen(LehrerLaso lehrer) {
		if (lehrer == null) {
			lehrer = new LehrerLaso();
		}
		return new LehrerAnlegen(app, lehrer);
	}
	
	@Override	
	public void windowClose(CloseEvent event) {
		Window source = event.getWindow();
		if (source == confirmUpgrade) {
			if (confirmUpgrade.getDecision()) {
				try {
					doSystemUpgrade();
				} catch (Exception e) {
					e.printStackTrace();
					app.getMainWindow().showNotification("Fehler beim Löschen! Bitte wenden Sie sich an den Administrator!", Notification.TYPE_ERROR_MESSAGE);
				}				
			}
		}
	}
	
	private void doSystemUpgrade() throws Exception {
		for (FossaUserLaso user: FossaUserContainer.getInstance().getItemIds()) {
			if (user.isLocked() && !LehrerContainer.getLehrerByUser(user).getPojo().getIsAdmin()) {				
				app.getMainWindow().showNotification("Konflikt!", "Das Systemupdate kann nicht durchgeführt werden, während noch Lehrer am System angemeldet sind. Bitte sorgen Sie dafür, dass alle Lehrer sich vom System abgemeldet haben, bevor Sie es erneut versuchen.", Notification.TYPE_ERROR_MESSAGE);
				return;
			}			
		}
		for (FossaUserLaso user: FossaUserContainer.getInstance().getItemIds()) {
			user.lock();			
		}

		systemBereinigen();
		if (LebSettingsContainer.getLebSettings().getHalbjahr().isZweitesHalbjahr()) {
			letzteKlassenLoeschen();
			klassenUpgraden();
			ersteKlassenErstellen();
		}
		
		openSubwindow(getLebSettingsAnlegen(LebSettingsContainer.getLebSettings()));
		
		for (FossaUserLaso user: FossaUserContainer.getInstance().getItemIds()) {
			user.unlock();			
		}
		app.getMainWindow().showNotification("Das System-Upgrade ist abgeschlossen. Bitte passen Sie nun das Zeugnisausgabedatum und das Halbjahr an.", Notification.TYPE_TRAY_NOTIFICATION);

	}
	
	private void letzteKlassenLoeschen() {
		for (KlasseLaso klasse : KlasseContainer.getInstance().getItemIds()) {
			if (KlassenstufenUtils.getKlassenstufe(klasse.getKlassenname()) == LebSettingsContainer.getLebSettings().getLetzteKlassenstufe()) {
				for (SchuelerLaso schueler : SchuelerContainer.getAllSchuelerOfKlasse(klasse.getPojo()).getItemIds()) {
					SchuelerContainer.getInstance().deleteSchueler(schueler);
				}
				KlasseContainer.getInstance().deleteKlasse(klasse);
			}
		}
	}

	private void klassenUpgraden() {
		for (KlasseLaso klasse : KlasseContainer.getInstance().getItemIds()) {
			klasse.setKlassenname(KlassenstufenUtils.erhoeheKlassenstufe(klasse.getKlassenname()));
		}		
	}
	
	private void ersteKlassenErstellen() {
		int abgangsjahrDerErsties = GregorianCalendar.getInstance().get(Calendar.YEAR) + LebSettingsContainer.getLebSettings().getLetzteKlassenstufe();
		for (KlasseLaso klasse : KlasseContainer.getInstance().getItemIds()) {
			if (KlassenstufenUtils.getKlassenstufe(klasse.getKlassenname()) == 2) {
				abgangsjahrDerErsties = klasse.getAbgangsjahr() + 1;
				break;
			}
		}
		for (int i=0; i<LebSettingsContainer.getLebSettings().getAnzahlErsteKlassen(); i++) {
			KlasseLaso klasse = new KlasseLaso();
			klasse.setKlassenname(KlassenstufenUtils.generateKlassennameForKlassenstufe(1, KlasseContainer.getInstance()));
			klasse.setAbgangsjahr(abgangsjahrDerErsties);
			KlasseContainer.getInstance().addBean(klasse);
		}
		KlasseContainer.sortieren();
	}

	private void systemBereinigen() throws Exception {
		for (SchuelerLaso schueler : SchuelerContainer.getInstance().getItemIds()){
			EinschaetzungLaso einschaetzung = schueler.getSchuelereinschaetzung();
			if(einschaetzung != null){
				schueler.setSchuelereinschaetzung(null);
				FossaLaso.deleteIfExists(einschaetzung.getPojo());
			}
		}
		for (KlasseLaso klasse : KlasseContainer.getInstance().getItemIds()){
			EinschaetzungLaso einschaetzung = klasse.getKlasseneinschaetzung();
			if(einschaetzung != null){
				klasse.setKlasseneinschaetzung(null);
				FossaLaso.deleteIfExists(einschaetzung.getPojo());
			}
		}

		for (FachLaso fach : FachContainer.getInstance().getItemIds()) {
			for (SchuelerPojo schueler: ZuordnungFachSchuelerContainer.getAllSchuelerOfFach(fach.getPojo()).getItemIds()) {
				new ZuordnungFachSchuelerHandler(schueler, fach.getPojo(), true, this).setZugeordnet(false);
			}
			FachContainer.getInstance().deleteFach(fach);
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
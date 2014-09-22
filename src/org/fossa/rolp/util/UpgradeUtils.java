package org.fossa.rolp.util;

import org.fossa.rolp.RolpApplication;
import org.fossa.rolp.data.einschaetzung.EinschaetzungLaso;
import org.fossa.rolp.data.fach.FachContainer;
import org.fossa.rolp.data.fach.FachLaso;
import org.fossa.rolp.data.klasse.KlasseContainer;
import org.fossa.rolp.data.klasse.KlasseLaso;
import org.fossa.rolp.data.klasse.klassentyp.KlassentypPojoContainer;
import org.fossa.rolp.data.leb.LebSettingsContainer;
import org.fossa.rolp.data.schueler.SchuelerContainer;
import org.fossa.rolp.data.schueler.SchuelerLaso;
import org.fossa.rolp.data.schueler.SchuelerPojo;
import org.fossa.rolp.data.zuordnung.fachschueler.ZuordnungFachSchuelerContainer;
import org.fossa.rolp.data.zuordnung.fachschueler.ZuordnungFachSchuelerHandler;
import org.fossa.rolp.data.zuordnung.fachschueler.ZuordnungFachSchuelerLaso;
import org.fossa.rolp.ui.dashboard.AdminDashboard;
import org.fossa.rolp.ui.leb.LebSettingsAnlegen;
import org.fossa.vaadin.auth.data.FossaUserContainer;
import org.fossa.vaadin.auth.data.FossaUserLaso;
import org.fossa.vaadin.laso.FossaLaso;

import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.Window.Notification;

public class UpgradeUtils {

	public static void doSystemUpgrade(RolpApplication app, AdminDashboard adminDashboard, LebSettingsAnlegen lebSettingsAnlegen) throws Exception {
		for (FossaUserLaso user: FossaUserContainer.getInstance().getItemIds()) {
			if (user.isLocked() && !user.getId().equals(app.getLoginLehrer().getId())) {				
				app.getMainWindow().showNotification("Konflikt!", "Das Systemupdate kann nicht durchgeführt werden, während noch Lehrer oder andere Administratoren am System angemeldet sind. Bitte sorgen Sie dafür, dass alle Lehrer sich vom System abgemeldet haben, bevor Sie es erneut versuchen.");
				return;
			}		
		}
		for (FossaUserLaso user: FossaUserContainer.getInstance().getItemIds()) {
			user.lock();			
		}

		einschaetzungenLoeschen(adminDashboard);
		if (LebSettingsContainer.getLebSettings().getHalbjahr().isZweitesHalbjahr()) {
			faecherLoeschen(adminDashboard);
			letzteKlassenLoeschen();
			klassenUpgraden();
			ersteKlassenErstellen();
		}
		
		app.getMainWindow().addWindow(lebSettingsAnlegen);
		
		for (FossaUserLaso user: FossaUserContainer.getInstance().getItemIds()) {
			user.unlock();			
		}
		app.getMainWindow().showNotification("Das System-Upgrade ist abgeschlossen. Bitte passen Sie nun das Zeugnisausgabedatum und das Halbjahr an.", Notification.TYPE_TRAY_NOTIFICATION);

	}
	
	private static void letzteKlassenLoeschen() {
		BeanItemContainer<KlasseLaso> klassen = new BeanItemContainer<KlasseLaso>(KlasseLaso.class);
		klassen.addAll(KlasseContainer.getInstance().getItemIds());
		for (KlasseLaso klasse : klassen.getItemIds()) {
			if (klasse.getKlassentyp().isKlassenstufenorientiert() && KlassenstufenUtils.getKlassenstufe(klasse.getKlassenname()) == LebSettingsContainer.getLebSettings().getLetzteKlassenstufe()) {
				for (SchuelerLaso schueler : SchuelerContainer.getAllSchuelerOfKlasse(klasse.getPojo()).getItemIds()) {
					SchuelerContainer.getInstance().deleteSchueler(schueler);
				}
				KlasseContainer.getInstance().deleteKlasse(klasse);
			}
		}
	}

	private static void klassenUpgraden() {
		for (KlasseLaso klasse : KlasseContainer.getInstance().getItemIds()) {
			if(klasse.getKlassentyp().isKlassenstufenorientiert()){
			klasse.setKlassenname(KlassenstufenUtils.erhoeheKlassenstufe(klasse.getKlassenname()));
			}
		}		
	}
	
	private static void ersteKlassenErstellen() {
		for (int i=0; i<LebSettingsContainer.getLebSettings().getAnzahlErsteKlassen(); i++) {
			KlasseLaso klasse = new KlasseLaso();
			klasse.setKlassenname(KlassenstufenUtils.generateKlassennameForKlassenstufe(1, KlasseContainer.getInstance()));
			klasse.setKlassentyp(KlassentypPojoContainer.getInstance().getKlassenstufenorientiert());
			KlasseContainer.getInstance().addBean(klasse);
		}
	}

	private static void einschaetzungenLoeschen(AdminDashboard adminDashboard) throws Exception {
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
		for (ZuordnungFachSchuelerLaso zuordnungFS : ZuordnungFachSchuelerContainer.getInstance().getItemIds()) {
			EinschaetzungLaso einschaetzung = zuordnungFS.getFacheinschaetzung();
			if(einschaetzung != null){
				zuordnungFS.setFacheinschaetzung(null);
				FossaLaso.deleteIfExists(einschaetzung.getPojo());
			}
		}
	}
	
	private static void faecherLoeschen(AdminDashboard adminDashboard) throws Exception {
		BeanItemContainer<FachLaso> faecher = new BeanItemContainer<FachLaso>(FachLaso.class);
		faecher.addAll(FachContainer.getInstance().getItemIds());
		for (FachLaso fach : faecher.getItemIds()) {
			for (SchuelerPojo schueler: ZuordnungFachSchuelerContainer.getAllSchuelerOfFach(fach.getPojo()).getItemIds()) {
				new ZuordnungFachSchuelerHandler(schueler, fach.getPojo(), true, adminDashboard).setZugeordnet(false);
			}
			FachContainer.getInstance().deleteFach(fach);
		}
	}
	
}

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

import java.io.IOException;

import org.fossa.rolp.RolpApplication;
import org.fossa.rolp.data.einschaetzung.EinschaetzungLaso;
import org.fossa.rolp.data.fach.FachContainer;
import org.fossa.rolp.data.klasse.KlasseContainer;
import org.fossa.rolp.data.klasse.KlasseLaso;
import org.fossa.rolp.data.leb.LebSettingsContainer;
import org.fossa.rolp.data.schueler.SchuelerContainer;
import org.fossa.rolp.data.schueler.SchuelerLaso;
import org.fossa.rolp.data.zuordnung.fachschueler.ZuordnungFachSchuelerContainer;
import org.fossa.rolp.ui.einschaetzung.EinschaetzungAnlegen;
import org.fossa.rolp.ui.fach.KurseZuordnen;
import org.fossa.rolp.ui.fach.PflichtfaecherlisteAnzeigen;
import org.fossa.rolp.ui.fach.SchuelerfachList;
import org.fossa.rolp.ui.fach.SchuelerfachlisteAnzeigen;
import org.fossa.rolp.ui.klasse.klasseanlegen.KlasseAnlegen;
import org.fossa.rolp.ui.leb.LebAnzeigen;
import org.fossa.rolp.ui.schueler.SchuelerList;
import org.fossa.rolp.ui.schueler.SchuelerlisteAnzeigen;
import org.fossa.rolp.ui.schueler.versetzungsvermerk.VersetzungsvermerklisteAnzeigen;
import org.fossa.rolp.util.HintUtils;
import org.fossa.vaadin.ui.FossaBooleanCellImageHandler;
import org.fossa.vaadin.ui.FossaWindow;
import org.fossa.vaadin.ui.exception.FossaLasoLockedException;

import com.itextpdf.text.DocumentException;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.event.ItemClickEvent.ItemClickListener;
import com.vaadin.terminal.Sizeable;
import com.vaadin.terminal.ThemeResource;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CustomLayout;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.TextField;

public class KlassenlehrerDashboard extends FossaWindow implements Button.ClickListener, ItemClickListener {

	private static final long serialVersionUID = -6514418795515349474L;
	
	private Button schuelerlisteBearbeitenButton = new Button ("Schülerliste bearbeiten", (ClickListener) this);
	private Button klasseAnlegenButton = new Button("neue Klasse anlegen", (ClickListener) this);
	private Button faecherFestlegenButton = new Button ("Fächer bearbeiten", (ClickListener) this);
	private Button kurseZuorndnenButton = new Button ("Kurse zuordnen", (ClickListener) this);
	private Button individuelleEinschaetzungButton = new Button ("individuelle Einschätzung", (ClickListener) this);
	private Button klassenBriefVerfassenButton = new Button ("Klassenbrief", (ClickListener) this);
	private Button facheinschaetzungButton = new Button ("Facheinschätzungen", (ClickListener) this);
	private Button lebErstellenButton = new Button ("LEB erstellen", (ClickListener) this);
	private Button windowCloseButton = new Button ("Fenster schließen", (ClickListener) this);
	private Button versetzungsvermerkButton = new Button ("Versetzungsvermerk", (ClickListener) this);
	private TextField klasseLabel;
	private TextField abgangsjahrLabel;
	private TextField zeugnisausgabeLabel;
	private TextField halbjahrLabel;
	private Label hinweistext;
	private SchuelerlisteAnzeigen schuelerliste;
	private VersetzungsvermerklisteAnzeigen versetzungsvermerkliste;
	private KlasseAnlegen klasseAnlegen;
	private PflichtfaecherlisteAnzeigen faecherfestlegen;
	private RolpApplication app;
	private EinschaetzungAnlegen einschaetzungAnlegen;
	private KurseZuordnen kurseZuordnen;
	private SchuelerList schuelerList = null;
	private SchuelerfachlisteAnzeigen schuelerfachlisteAnzeigen;

	private CustomLayout horizontalButtonBattery = new CustomLayout("./lehrerDashboards/klassenlehrerHorizontalButtonBattery");

	private CustomLayout verticalButtonBattery = new CustomLayout("./lehrerDashboards/klassenlehrerVerticalButtonBattery");
	
	private CustomLayout headdataEditingPanel = new CustomLayout("./lehrerDashboards/headdataEditingPanel");

	private FossaBooleanCellImageHandler klasseneinschaetzungVorhandenHook;

	

	private static final String MAINPAGE_PANEL_ANMELDEN_LOGO_PATH = "images/rolp.png";
	
	public KlassenlehrerDashboard(RolpApplication app) {
		super(app);
		this.app = app;
		setCaption(" - KlassenlehrerDashboard - ");
		setWidth("100%");
		setHeight("100%");
		
		buildButtonBatteries();
		
		CustomLayout layout = new CustomLayout("./lehrerDashboards/klassenLehrerDashboardMain");
		setContent(layout);
		
		CustomLayout headline = new CustomLayout("./lehrerDashboards/headline");
		headline.addStyleName("headline");
		
		Embedded logo = new Embedded(null, new ThemeResource(MAINPAGE_PANEL_ANMELDEN_LOGO_PATH));
		logo.setType(Embedded.TYPE_IMAGE);
		
		headline.addComponent(logo,"logo");
		headline.addComponent(headdataEditingPanel,"headdataEditingPanel");
		
		Panel hints = new Panel();
		hints.addStyleName("hints");
		hinweistext = new Label();
		hinweistext.setContentMode(Label.CONTENT_XHTML);
		hinweistext.setReadOnly(true);
		hinweistext.setHeight("250px");
		hints.setHeight("250px");
		hints.setScrollable(true);
		hints.addComponent(hinweistext);
		refreshHinweistext();
		
		CustomLayout schuelerListe = new CustomLayout("./lehrerDashboards/liste");
		schuelerListe.addStyleName("liste");
		schuelerList = getSchuelerList();
		schuelerList.setHeight("250px");
		schuelerList.setStyleName("list");
		schuelerListe.addComponent(schuelerList, "list");
		schuelerListe.setHeight("260px");
		layout.addComponent(schuelerListe,"liste");
		
		layout.addComponent(headline,"headline");
		layout.addComponent(horizontalButtonBattery,"horizontalButtonBattery");
		layout.addComponent(verticalButtonBattery,"verticalButtonBattery");
		

		layout.addComponent(hints,"hints");
		windowCloseButton.setWidth("100%");
		layout.addComponent(windowCloseButton, "windowCloseButton");
//		app.getAnimator().animate(horizontalButtonBattery, AnimType.ROLL_DOWN_OPEN_POP).setDuration(500).setDelay(500);
	}
	
	private void refreshHinweistext() {
		hinweistext.setReadOnly(false);
		hinweistext.setValue(getHinweistext());
		hinweistext.setReadOnly(true);
	}

	private String getHinweistext() {
		String hinweisCollection = "";
		KlasseLaso klasse = KlasseContainer.getKlasseByLehrer(app.getLoginLehrer());
		if (klasse != null){
			if (FachContainer.getAllPflichtfaecherOfKlasse(klasse.getPojo()).size() == 0) {
				hinweisCollection = hinweisCollection + HintUtils.createHinweistextKlasseKeinePflichtfaecher(FachContainer.getAllPflichtfaecherOfKlasse(klasse.getPojo()), klasse.getPojo());
			}		
			for (SchuelerLaso schueler : SchuelerContainer.getAllSchuelerOfKlasse(klasse.getPojo()).getItemIds()) {
				hinweisCollection = hinweisCollection + HintUtils.createHinweistextSchuelerKeineKurse(ZuordnungFachSchuelerContainer.getInstance(), schueler);
			}
			return hinweisCollection;
		} else {
			hinweisCollection = hinweisCollection + HintUtils.HEADER_WARNING + "Keine Klasse angelegt";
		}
		return hinweisCollection;
	}
	
	private void buildButtonBatteries() {
		horizontalButtonBattery.removeAllComponents();
		horizontalButtonBattery.addStyleName("horizontalButtonBattery");
		KlasseLaso klasse = KlasseContainer.getKlasseByLehrer(app.getLoginLehrer());
		headdataEditingPanel.addComponent(klasseAnlegenButton, "klasseAnlegenButton");		
		horizontalButtonBattery.addComponent(schuelerlisteBearbeitenButton,"schuelerlisteBearbeitenButton");
		horizontalButtonBattery.addComponent(faecherFestlegenButton,"faecherFestlegenButton");
		horizontalButtonBattery.addComponent(kurseZuorndnenButton,"kurseZuordnenButton");

		schuelerlisteBearbeitenButton.setWidth(85, Sizeable.UNITS_PERCENTAGE);
		faecherFestlegenButton.setWidth(85, Sizeable.UNITS_PERCENTAGE);
		kurseZuorndnenButton.setWidth(85, Sizeable.UNITS_PERCENTAGE);
		
		verticalButtonBattery.removeAllComponents();
		verticalButtonBattery.addStyleName("verticalButtonBattery");
		individuelleEinschaetzungButton.setWidth(95, Sizeable.UNITS_PERCENTAGE);
		verticalButtonBattery.addComponent(individuelleEinschaetzungButton,"individuelleEinschaetzungButton");
		klassenBriefVerfassenButton.setWidth(95, Sizeable.UNITS_PERCENTAGE);
		verticalButtonBattery.addComponent(klassenBriefVerfassenButton,"klassenBriefVerfassenButton");
		facheinschaetzungButton.setEnabled(false);
		facheinschaetzungButton.setWidth(95, Sizeable.UNITS_PERCENTAGE);
		verticalButtonBattery.addComponent(facheinschaetzungButton,"facheinschaetzungButton");
		lebErstellenButton.setEnabled(true);
		lebErstellenButton.setWidth(95, Sizeable.UNITS_PERCENTAGE);
		verticalButtonBattery.addComponent(lebErstellenButton,"lebErstellenButton");
		
		activateButtons(klasse != null);
		if (klasse != null){
			klasseAnlegenButton.setCaption("Klassenkopf bearbeiten");			
			if (SchuelerContainer.getAllSchuelerOfKlasse(klasse.getPojo()).size() == 0) {
				faecherFestlegenButton.setEnabled(false);
			}
			buildKlassenkopfdaten(klasse);			
			
			if (LebSettingsContainer.getLebSettings().getHalbjahr().isZweitesHalbjahr()){
				versetzungsvermerkButton.setWidth(95, Sizeable.UNITS_PERCENTAGE);
				verticalButtonBattery.addComponent(versetzungsvermerkButton,"versetzungsvermerkButton");
				}
		}	
	}

	private void buildKlassenkopfdaten(KlasseLaso klasse) {
		headdataEditingPanel.removeAllComponents();
		headdataEditingPanel.addStyleName("headdataEditingPanel");
		klasseAnlegenButton.setWidth(35, Sizeable.UNITS_PERCENTAGE);
		headdataEditingPanel.addComponent(klasseAnlegenButton,"klasseAnlegenButton");
		klasseLabel = new TextField("Klasse " + klasse.getKlassenname());
		klasseLabel.setReadOnly(true);
		klasseLabel.addStyleName("klasseLabel");
		headdataEditingPanel.addComponent(klasseLabel,"klasseLabel");

		abgangsjahrLabel = new TextField("Abgangsjahr " + String.valueOf(klasse.getAbgangsjahr()));
		abgangsjahrLabel.setReadOnly(true);
		headdataEditingPanel.addComponent(abgangsjahrLabel,"abgangsjahrLabel");
		
		zeugnisausgabeLabel = new TextField("Zeugnisausgabe am " + LebSettingsContainer.getLebSettings().getZeugnisausgabedatumString());
		zeugnisausgabeLabel.setReadOnly(true);
		headdataEditingPanel.addComponent(zeugnisausgabeLabel,"zeugnisausgabeLabel");
		
		halbjahrLabel = new TextField(LebSettingsContainer.getLebSettings().getHalbjahrString());
		halbjahrLabel.setReadOnly(true);
		headdataEditingPanel.addComponent(halbjahrLabel,"halbjahrLabel");
		
		HorizontalLayout klasseneinschaetzung = new HorizontalLayout();
		if (klasse.getKlasseneinschaetzung() != null && klasse.getKlasseneinschaetzung().getErledigt()) {
			klasseneinschaetzungVorhandenHook = new FossaBooleanCellImageHandler(true);
		} else {
			klasseneinschaetzungVorhandenHook = new FossaBooleanCellImageHandler(false);
		}
		klasseneinschaetzung.addComponent(new Label("Klassenbrief vorhanden? "));
		klasseneinschaetzung.addComponent(klasseneinschaetzungVorhandenHook);
		headdataEditingPanel.addComponent(klasseneinschaetzung, "klasseneinschaetzung");
	}

	private void activateButtons(boolean klasseVorhanden) {
		schuelerlisteBearbeitenButton.setEnabled(klasseVorhanden);
		faecherFestlegenButton.setEnabled(klasseVorhanden);
		kurseZuorndnenButton.setEnabled(klasseVorhanden);
		versetzungsvermerkButton.setEnabled(klasseVorhanden);
		individuelleEinschaetzungButton.setEnabled(klasseVorhanden);
		klassenBriefVerfassenButton.setEnabled(klasseVorhanden);
		facheinschaetzungButton.setEnabled(klasseVorhanden);
		lebErstellenButton.setEnabled(klasseVorhanden);	
	}

	@Override
	public void buttonClick(ClickEvent event) {
		final Button source = event.getButton();
		if (source == windowCloseButton) {
			close();
		}
		if (source == schuelerlisteBearbeitenButton){
			showSchuelerliste();
		}
		else if (source == klasseAnlegenButton){
			showKlasseAnlegen();				
		}
		else if (source == faecherFestlegenButton ){
			showFaecherFestlegen();
		}
		else if (source == kurseZuorndnenButton ){
			showKurseZuordnen();
		}
		else if (source == individuelleEinschaetzungButton ){
			showIndividuelleEinschaetzung();
		}
		else if (source == klassenBriefVerfassenButton ){
			showKlassenbrief();
		}
		else if (source == facheinschaetzungButton ){
			SchuelerLaso schueler = (SchuelerLaso) schuelerList.getValue();
			if (schueler != null) {
				schuelerspezifischeDetailsAnzeigen(schueler);
			}
		}
		else if (source == lebErstellenButton ){
			SchuelerLaso schueler = (SchuelerLaso) schuelerList.getValue();
			if (schueler == null) {
				showNotification("kein Schüler ausgewählt");
				return;
			}
			lebErstellen();
		}
		else if (source == versetzungsvermerkButton){
			getVersetzungsvermerklisteAnzeigen();
		}
	}
	
	private SchuelerList getSchuelerList() {
		if (schuelerList == null) {
			schuelerList = new SchuelerList(app, this, SchuelerContainer.CHECKLIST_COL_ORDER, SchuelerContainer.CHECKLIST_COL_HEADERS);
		}
		return schuelerList;
	}

	private void schuelerspezifischeDetailsAnzeigen(SchuelerLaso schueler) {
		getApplication().getMainWindow().addWindow(buildSchuelerfachlistAnzeigen(schueler));
	}

	private FossaWindow buildSchuelerfachlistAnzeigen(SchuelerLaso schueler) {
		SchuelerfachList schuelerfachListe = getSchuelerfachList(schueler);
		schuelerfachlisteAnzeigen = new SchuelerfachlisteAnzeigen(app, schuelerfachListe);
		return schuelerfachlisteAnzeigen;
	}
	
	private SchuelerfachList getSchuelerfachList(SchuelerLaso schueler) {
		return new SchuelerfachList(app, schueler);
	}
	
	private void lebErstellen(){
		SchuelerLaso schueler = (SchuelerLaso) schuelerList.getValue();
		KlasseLaso klasse = KlasseContainer.getKlasseByLehrer(app.getLoginLehrer());
		try {
			getApplication().getMainWindow().addWindow(new LebAnzeigen(app, schueler, klasse));
		} catch (DocumentException e) {
			getApplication().getMainWindow().showNotification("DOCUMENT!", e.getMessage(), Notification.TYPE_ERROR_MESSAGE);
			e.printStackTrace();
		} catch (IOException e) {
			getApplication().getMainWindow().showNotification("DOCUMENT!", e.getMessage(), Notification.TYPE_ERROR_MESSAGE);
			e.printStackTrace();
		}
		
	}
		
	private void showKlassenbrief() {
		KlasseLaso klasse = KlasseContainer.getKlasseByLehrer(app.getLoginLehrer());
		EinschaetzungLaso einschaetzung = klasse.getKlasseneinschaetzung();
		if (einschaetzung == null){
			einschaetzung = new EinschaetzungLaso();
			klasse.setKlasseneinschaetzung(einschaetzung);
		}
		try {
			einschaetzungAnlegen = new EinschaetzungAnlegen(app, einschaetzung, "Klassenbrief für die Klasse " + klasse.getKlassenname(), null);
		} catch (FossaLasoLockedException e) {
			// can't happen!
		}
		getApplication().getMainWindow().addWindow(einschaetzungAnlegen);
		
	}

	private void showIndividuelleEinschaetzung() {
		SchuelerLaso schueler = (SchuelerLaso) schuelerList.getValue();
		if (schueler != null) {
			EinschaetzungLaso einschaetzung = schueler.getSchuelereinschaetzung();
			if (einschaetzung == null){
				einschaetzung = new EinschaetzungLaso();
				schueler.setSchuelereinschaetzung(einschaetzung);
			}
			try {
				einschaetzungAnlegen = new EinschaetzungAnlegen(app, einschaetzung, "individuelle Einschätzung für " + schueler.getVorname() + " " + schueler.getName(), schueler);
			} catch (FossaLasoLockedException e) {
				getWindow().showNotification("LOCKED");
				return;
			}
			getApplication().getMainWindow().addWindow(einschaetzungAnlegen);
		}		
	}

	private void showKurseZuordnen() {
		kurseZuordnen = new KurseZuordnen(app);
		getApplication().getMainWindow().addWindow(kurseZuordnen);
		
	}

	private void showFaecherFestlegen() {
		faecherfestlegen = new PflichtfaecherlisteAnzeigen(app);
		getApplication().getMainWindow().addWindow(faecherfestlegen);
	}

	private void showKlasseAnlegen() {
		KlasseLaso klasse = KlasseContainer.getKlasseByLehrer(app.getLoginLehrer());
		klasseAnlegen = new KlasseAnlegen(app, klasse);
		getApplication().getMainWindow().addWindow(klasseAnlegen);
		
	}
	
	private void getVersetzungsvermerklisteAnzeigen() {
		versetzungsvermerkliste = new VersetzungsvermerklisteAnzeigen(app);
		getApplication().getMainWindow().addWindow(versetzungsvermerkliste);
		
	}

	private void showSchuelerliste() {
		schuelerliste = new SchuelerlisteAnzeigen(app);
		getApplication().getMainWindow().addWindow(schuelerliste);
		
	}

	@Override
	public void unlockLaso() {
	}

	public void refreshPage() {
		buildButtonBatteries();
		refreshHinweistext();
		KlasseLaso klasseLaso = KlasseContainer.getKlasseByLehrer(app.getLoginLehrer());
		if (klasseLaso != null) {
			getSchuelerList().refresh(klasseLaso.getPojo());
			getSchuelerList().requestRepaintAll();
			getSchuelerList().refreshRowCache();
			buildKlassenkopfdaten(klasseLaso);
		}
	}

	@Override
	public void itemClick(ItemClickEvent event) {
		// TODO Auto-generated method stub
		
	}


}

package org.fossa.rolp.demo;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.fossa.rolp.RolpApplication;
import org.fossa.rolp.data.einschaetzung.EinschaetzungLaso;
import org.fossa.rolp.data.fach.FachContainer;
import org.fossa.rolp.data.fach.FachLaso;
import org.fossa.rolp.data.fach.fachdefinition.FachdefinitionContainer;
import org.fossa.rolp.data.fach.fachdefinition.FachdefinitionLaso;
import org.fossa.rolp.data.fach.fachdefinition.FachdefinitionPojo;
import org.fossa.rolp.data.fach.fachdefinition.leb.FachbezeichnungLebContainer;
import org.fossa.rolp.data.fach.fachdefinition.leb.FachbezeichnungLebLaso;
import org.fossa.rolp.data.fach.fachtyp.FachtypPojoContainer;
import org.fossa.rolp.data.klasse.KlasseContainer;
import org.fossa.rolp.data.klasse.KlasseLaso;
import org.fossa.rolp.data.klasse.klassentyp.KlassentypPojoContainer;
import org.fossa.rolp.data.leb.LebSettingsContainer;
import org.fossa.rolp.data.leb.LebSettingsLaso;
import org.fossa.rolp.data.leb.halbjahr.HalbjahrPojoContainer;
import org.fossa.rolp.data.lehrer.LehrerContainer;
import org.fossa.rolp.data.lehrer.LehrerLaso;
import org.fossa.rolp.data.lehrer.LehrerPojo;
import org.fossa.rolp.data.schueler.SchuelerContainer;
import org.fossa.rolp.data.schueler.SchuelerLaso;
import org.fossa.rolp.data.zuordnung.fachschueler.ZuordnungFachSchuelerContainer;
import org.fossa.rolp.data.zuordnung.fachschueler.ZuordnungFachSchuelerLaso;
import org.fossa.rolp.util.KlassenstufenUtils;
import org.fossa.vaadin.auth.FossaAuthorizer;
import org.fossa.vaadin.auth.data.FossaUserLaso;
import org.fossa.vaadin.auth.data.FossaUserPojo;

public class DemoSceneBuilder {
	
	private static String[] firstnames = new String[] {
		"Ronnie", "Romy", "Mark", "Judy", "Elke", "Amelie", "Justin", "Andreas", "Karsten", "Vanessa", "Linda", "Mario", "Max", "Elsa", "Anna", "Christoff", "Jan", "Bianca", "Ina", "Sabrina", "Tim", "Maxi", "Sophie", "Bernd", "Jens", "J�rg", "Uwe", "Jenny", "Helena", "Eileen", "Madlen", "Monique", "Gerd", "Klaus", "Franz", "Hugo", "Steve", "Pauline", "Jasmin", "Tom", "Chris", "Adam"};
	private static String[] lastnames = new String[] {
		"Baumann", "Hofmann", "Hoffmann", "Steinmann", "Mann", "J�ger", "Fischer", "Berger", "Weinert", "M�ller", "Meier", "Schulze", "Leitner", "Meggle", "K�hler", "Pohl", "Klaus", "Kunze", "Busch", "Wiese", "Weber", "Wache", "Saal", "Seifert", "Seidel", "Schnitzel", "Tippmann", "Trautner", "Marin", "Mendel", "Mauersberger", "Mund", "Muniz", "Erdel", "Elser", "Friedrich", "Daffner", "Baum", "F�rster", "Decker", "Becker", "Beck", "Tacker", "Brase"};
	
	public static void buildSchulleiterScene(RolpApplication app, FossaAuthorizer authorizer) {
		initializeContainers();
		initializeSettings();
		initializeFachdefinitionen();
		
		LehrerPojo schulleiterlehrer = new LehrerPojo();
		schulleiterlehrer.setIsAdmin(true);
		
		FossaUserPojo schulleiter = new FossaUserPojo();
		schulleiterlehrer.setUser(schulleiter);
		
		LehrerLaso lehrerLaso = new LehrerLaso(schulleiterlehrer);
		FossaUserLaso fossaUserLaso = new FossaUserLaso(schulleiter);
		fossaUserLaso.setUsername("Musterschulleiter");
		fossaUserLaso.setLastname("Musterschulleiter");
		lehrerLaso.setUser(fossaUserLaso);
		LehrerContainer.getInstance().addBean(lehrerLaso);
		
		app.setUser(fossaUserLaso);
		authorizer.unlockApplication(fossaUserLaso);
	}
	
	public static void buildKlassenlehrerScene(RolpApplication app, FossaAuthorizer authorizer) {
		initializeContainers();
		initializeSettings();
		initializeFachdefinitionen();
		
		FossaUserLaso klassenlehreruser = createLehrerUser("klmustermann", getRandomVorname(), "Musterklassenlehrer");
		FossaUserLaso fachlehreruser = createLehrerUser("flmustermann", getRandomVorname(), "Musterfachlehrer");
		FossaUserLaso andererKlassenlehreruser = createLehrerUser("aklmustermann", getRandomVorname(), "Andererklassenlehrer");
		
		buildEntities(klassenlehreruser, fachlehreruser, andererKlassenlehreruser);
		
		app.setUser(klassenlehreruser);
		authorizer.unlockApplication(klassenlehreruser);
	}

	public static void buildFachlehrerScene(RolpApplication app, FossaAuthorizer authorizer) {
		initializeContainers();
		initializeSettings();
		initializeFachdefinitionen();
		
		FossaUserLaso klassenlehreruser = createLehrerUser("klmustermann", getRandomVorname(), "Musterklassenlehrer");
		FossaUserLaso fachlehreruser = createLehrerUser("flmustermann", getRandomVorname(), "Musterfachlehrer");
		FossaUserLaso andererKlassenlehreruser = createLehrerUser("aklmustermann", getRandomVorname(), "Andererklassenlehrer");
		
		buildEntities(klassenlehreruser, fachlehreruser, andererKlassenlehreruser);
		
		app.setUser(fachlehreruser);
		authorizer.unlockApplication(fachlehreruser);
		
	}

	private static void buildEntities(FossaUserLaso klassenlehreruser, FossaUserLaso fachlehreruser, FossaUserLaso andererKlassenlehreruser) {
		KlasseLaso klasse = buildEineKlasse(klassenlehreruser, fachlehreruser, andererKlassenlehreruser);
		KlasseLaso andereKlasse = buildEineKlasse(andererKlassenlehreruser, fachlehreruser, klassenlehreruser);
		
		FachLaso kurs1 = createKurs(klassenlehreruser, fachlehreruser, "Fu�ball ");
		FachLaso kurs2 = createKurs(klassenlehreruser, fachlehreruser, "Gitarre ");
		ordneKurseZu(klasse, kurs1, kurs2);
		ordneKurseZu(andereKlasse, kurs1, kurs2);
	}

	private static void ordneKurseZu(KlasseLaso klasse, FachLaso kurs1,	FachLaso kurs2) {
		for (SchuelerLaso schueler : SchuelerContainer.getAllSchuelerOfKlasse(klasse.getPojo()).getItemIds()) {
			ZuordnungFachSchuelerLaso zuordnungFS = new ZuordnungFachSchuelerLaso();
			if (random(0,1)==0) {
				zuordnungFS.setFach(kurs1.getPojo());
			} else {
				zuordnungFS.setFach(kurs2.getPojo());
			}
			zuordnungFS.setSchueler(schueler.getPojo());
			if (random(0,5)<5) {
				EinschaetzungLaso facheinschaetzung = new EinschaetzungLaso();
				facheinschaetzung.setEinschaetzungstext(schueler.getVorname() + ", in " + zuordnungFS.getFach().getFachdefinition().getFachbezeichnung() + " hast du gro�es Talent bewiesen.");
				facheinschaetzung.setErledigt(true);
				zuordnungFS.setFacheinschaetzung(facheinschaetzung);
			}
			ZuordnungFachSchuelerContainer.getInstance().addBean(zuordnungFS);

		}
	}

	private static KlasseLaso buildEineKlasse(FossaUserLaso klassenlehreruser, FossaUserLaso fachlehreruser, FossaUserLaso andererKlassenlehreruser) {
		KlasseLaso klasse = createKlasse(klassenlehreruser);
		createSchueler(klasse);
		createPflichtfaecher(klassenlehreruser, fachlehreruser, andererKlassenlehreruser, klasse);
		return klasse;
	}

	private static void createPflichtfaecher(FossaUserLaso klassenlehreruser, FossaUserLaso fachlehreruser, FossaUserLaso andererKlassenlehreruser, KlasseLaso klasse) {
		for (FachdefinitionPojo fachdefinition : FachdefinitionContainer.getAllFaecherOfFachtyp(FachdefinitionContainer.getInstance(), FachtypPojoContainer.getPflichtfach()).getItemIds()) {
			if (random(0,3)<3) {
				FachLaso fach = new FachLaso();
				fach.setFachdefinition(fachdefinition);
				setLehrers(klassenlehreruser, fachlehreruser, andererKlassenlehreruser, fach);
				FachContainer.getInstance().addBean(fach);
				for (SchuelerLaso schueler : SchuelerContainer.getAllSchuelerOfKlasse(klasse.getPojo()).getItemIds()) {
					ZuordnungFachSchuelerLaso zuordnungFS = new ZuordnungFachSchuelerLaso();
					zuordnungFS.setFach(fach.getPojo());
					zuordnungFS.setSchueler(schueler.getPojo());
					if (random(0,4)<4) {
						EinschaetzungLaso facheinschaetzung = new EinschaetzungLaso();
						facheinschaetzung.setEinschaetzungstext("In " + fach.getFachbezeichnung() + " hast du gro�e Fortschritte gemacht, " + schueler.getVorname() + ".");
						facheinschaetzung.setErledigt(true);
						zuordnungFS.setFacheinschaetzung(facheinschaetzung);
					}
					ZuordnungFachSchuelerContainer.getInstance().addBean(zuordnungFS);
				}
			}
		}
	}

	private static void createSchueler(KlasseLaso klasse) {
		for (int i=0;i<random(8,16);i++) {
			SchuelerLaso schuelerNext = new SchuelerLaso();
			schuelerNext.setKlasse(klasse.getPojo());
			schuelerNext.setVorname(getRandomVorname());
			schuelerNext.setName(getRandomNachname());
			int nextKlasse = KlassenstufenUtils.getKlassenstufe(schuelerNext.getKlasse().getKlassenname()) + 1;
			schuelerNext.setVersetzungsvermerk("wird versetzt nach Klasse " + nextKlasse + ".");
			if (random(0,1) == 0) {
				EinschaetzungLaso schuelereinschaetzung = new EinschaetzungLaso();
				schuelereinschaetzung.setEinschaetzungstext("Du hattest ein starkes Jahr. Mach weiter so!");
				schuelereinschaetzung.setErledigt(true);
				schuelerNext.setSchuelereinschaetzung(schuelereinschaetzung);
			}
			SchuelerContainer.getInstance().addBean(schuelerNext);
		}
	}

	private static FachLaso createKurs(FossaUserLaso klassenlehreruser,	FossaUserLaso fachlehreruser, String kursname) {
		int i=0;
		FachLaso kurs1;
		String kursvorschlag;
		do {
			i += 1;
			kursvorschlag = kursname + i;
		} while (!isValidKursname(kursvorschlag));
		kurs1 = new FachLaso();
		FachdefinitionLaso kursvorschlagDefinition = new FachdefinitionLaso();
		kursvorschlagDefinition.setFachtyp(FachtypPojoContainer.getKurs());
		kursvorschlagDefinition.setFachbezeichnung(kursvorschlag);
		FachdefinitionContainer.getInstance().addBean(kursvorschlagDefinition);
		FachbezeichnungLebLaso kursvorschlagLebDefinition = new FachbezeichnungLebLaso();
		kursvorschlagLebDefinition.setFachdefinition(kursvorschlagDefinition.getPojo());
		kursvorschlagLebDefinition.setBezeichnung("Kurs");
		FachbezeichnungLebContainer.getInstance().addBean(kursvorschlagLebDefinition);
		kurs1.setFachdefinition(kursvorschlagDefinition.getPojo());
		kurs1.setFachlehrerEins(LehrerContainer.getLehrerByUser(fachlehreruser).getPojo());
		FachContainer.getInstance().addBean(kurs1);
		return kurs1;
	}
	
	private static boolean isValidKursname(String kursvorschlag) {
		for (FachdefinitionPojo kurs : FachdefinitionContainer.getAllFaecherOfFachtyp(FachdefinitionContainer.getInstance(), FachtypPojoContainer.getKurs()).getItemIds()) {
			if (kursvorschlag.equals(kurs.getFachbezeichnung())) {
				return false;
			}
		}
		return true;
	}

	private static KlasseLaso createKlasse(FossaUserLaso fossaUserLaso) {
		KlasseLaso klasse = new KlasseLaso();
		Calendar cal = GregorianCalendar.getInstance();
		cal.setTime(new Date());
		int baseYear = cal.get(Calendar.YEAR);
		
		klasse.setKlassenname(KlassenstufenUtils.generateKlassennameForKlassenstufe(10-((int) (Math.random() * (baseYear+9 - baseYear) + baseYear)-baseYear), KlasseContainer.getInstance()));
		klasse.setKlassenlehrer(LehrerContainer.getLehrerByUser(fossaUserLaso).getPojo());
		klasse.setKlassentyp(KlassentypPojoContainer.getInstance().getKlassenstufenorientiert());
		KlasseContainer.getInstance().addBean(klasse);
		EinschaetzungLaso klasseneinschaetzung = new EinschaetzungLaso();
		klasseneinschaetzung.setEinschaetzungstext("Ihr ward eine gro�artige Klasse.");
		klasseneinschaetzung.setErledigt(true);
		klasse.setKlasseneinschaetzung(klasseneinschaetzung);
		return klasse;
	}

	private static FossaUserLaso createLehrerUser(String username, String firstname, String lastname) {
		LehrerPojo lehrer = new LehrerPojo();
		
		FossaUserPojo lehrerUser = new FossaUserPojo();
		lehrer.setUser(lehrerUser);
		
		LehrerLaso lehrerLaso = new LehrerLaso(lehrer);
		FossaUserLaso fossaUserLaso = new FossaUserLaso(lehrerUser);
		fossaUserLaso.setUsername(username);
		fossaUserLaso.setFirstname(firstname);
		fossaUserLaso.setLastname(lastname);
		lehrerLaso.setUser(fossaUserLaso);
		LehrerContainer.getInstance().addBean(lehrerLaso);
		return fossaUserLaso;
	}
	
	private static void setLehrers(FossaUserLaso klassenlehreruser,	FossaUserLaso fachlehreruser, FossaUserLaso andererKlassenlehreruser, FachLaso fach) {
		int factor = random(0,6);
		if (factor==0) {
			fach.setFachlehrerEins(LehrerContainer.getLehrerByUser(klassenlehreruser).getPojo());
		} else if (factor==1) {
			fach.setFachlehrerEins(LehrerContainer.getLehrerByUser(fachlehreruser).getPojo());
			fach.setFachlehrerZwei(LehrerContainer.getLehrerByUser(klassenlehreruser).getPojo());
		} else if (factor==2) {
			fach.setFachlehrerEins(LehrerContainer.getLehrerByUser(fachlehreruser).getPojo());
		} else if (factor==3) {
			fach.setFachlehrerEins(LehrerContainer.getLehrerByUser(fachlehreruser).getPojo());
			fach.setFachlehrerZwei(LehrerContainer.getLehrerByUser(andererKlassenlehreruser).getPojo());
		} else if (factor==4) {
			fach.setFachlehrerEins(LehrerContainer.getLehrerByUser(andererKlassenlehreruser).getPojo());
		} else if (factor==5) {
			fach.setFachlehrerEins(LehrerContainer.getLehrerByUser(klassenlehreruser).getPojo());
			fach.setFachlehrerZwei(LehrerContainer.getLehrerByUser(andererKlassenlehreruser).getPojo());
		} else if (factor==6) {
			fach.setFachlehrerEins(LehrerContainer.getLehrerByUser(klassenlehreruser).getPojo());
			fach.setFachlehrerZwei(LehrerContainer.getLehrerByUser(fachlehreruser).getPojo());
			fach.setFachlehrerDrei(LehrerContainer.getLehrerByUser(andererKlassenlehreruser).getPojo());
		}
	}
	
	private static void initializeContainers() {
		FachContainer.getInstance();
		KlasseContainer.getInstance();
		LehrerContainer.getInstance();
		SchuelerContainer.getInstance();
		ZuordnungFachSchuelerContainer.getInstance();
		FachdefinitionContainer.getInstance();
		FachbezeichnungLebContainer.getInstance();
	}
	
	private static void initializeSettings() {
		LebSettingsLaso lebSettings = LebSettingsContainer.getLebSettings();
		if (lebSettings.getId() == null) {
			lebSettings = new LebSettingsLaso();
		}
		lebSettings.setAnzahlErsteKlassen(2);
		lebSettings.setHalbjahr(HalbjahrPojoContainer.getInstance().lastItemId());
		lebSettings.setLetzteKlassenstufe(8);
		lebSettings.setZeugnisausgabedatum(new Date());
		LebSettingsContainer.getInstance().addItem(lebSettings);
	}
	
	private static void initializeFachdefinitionen() {
		if (FachdefinitionContainer.getInstance().getItemIds().isEmpty()) {
			FachdefinitionLaso deutschDefinition = new FachdefinitionLaso();
			deutschDefinition.setFachtyp(FachtypPojoContainer.getPflichtfach());
			deutschDefinition.setFachbezeichnung("Deutsch");
			FachdefinitionContainer.getInstance().addBean(deutschDefinition);
			FachbezeichnungLebLaso deutschLebDefinition = new FachbezeichnungLebLaso();
			deutschLebDefinition.setFachdefinition(deutschDefinition.getPojo());
			deutschLebDefinition.setBezeichnung("Deutschunterricht");
			FachbezeichnungLebContainer.getInstance().addBean(deutschLebDefinition);
			
			FachdefinitionLaso matheDefinition = new FachdefinitionLaso();
			matheDefinition.setFachtyp(FachtypPojoContainer.getPflichtfach());
			matheDefinition.setFachbezeichnung("Mathematik");
			FachdefinitionContainer.getInstance().addBean(matheDefinition);
			FachbezeichnungLebLaso matheLebDefinition = new FachbezeichnungLebLaso();
			matheLebDefinition.setFachdefinition(matheDefinition.getPojo());
			matheLebDefinition.setBezeichnung("Mathe");
			FachbezeichnungLebContainer.getInstance().addBean(matheLebDefinition);
			FachbezeichnungLebLaso matheLebDefinition2 = new FachbezeichnungLebLaso();
			matheLebDefinition2.setFachdefinition(matheDefinition.getPojo());
			matheLebDefinition2.setBezeichnung("Mathematikunterricht");
			FachbezeichnungLebContainer.getInstance().addBean(matheLebDefinition2);
			
			FachdefinitionLaso englischDefinition = new FachdefinitionLaso();
			englischDefinition.setFachtyp(FachtypPojoContainer.getPflichtfach());
			englischDefinition.setFachbezeichnung("Englisch");
			FachdefinitionContainer.getInstance().addBean(englischDefinition);
			FachbezeichnungLebLaso englischLebDefinition = new FachbezeichnungLebLaso();
			englischLebDefinition.setFachdefinition(englischDefinition.getPojo());
			englischLebDefinition.setBezeichnung("Englischunterricht");
			FachbezeichnungLebContainer.getInstance().addBean(englischLebDefinition);
		
			FachdefinitionLaso geschichteDefinition = new FachdefinitionLaso();
			geschichteDefinition.setFachtyp(FachtypPojoContainer.getPflichtfach());
			geschichteDefinition.setFachbezeichnung("Geschichte");
			FachdefinitionContainer.getInstance().addBean(geschichteDefinition);
		}
	}

	

	private static String getRandomNachname() {
		int slot = random(0, lastnames.length-1);
		return lastnames[slot];
	}

	private static int random(int min, int max) {
		return (int) (Math.random() * (max+1 - min) + min);
	}

	private static String getRandomVorname() {
		int slot = random(0, firstnames.length-1);
		return firstnames[slot];
	}
}

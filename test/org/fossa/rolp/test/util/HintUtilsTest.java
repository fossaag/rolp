package org.fossa.rolp.test.util;

import static org.junit.Assert.assertTrue;

import org.fossa.rolp.data.fach.FachLaso;
import org.fossa.rolp.data.fach.FachPojo;
import org.fossa.rolp.data.fach.fachtyp.FachtypPojo;
import org.fossa.rolp.data.klasse.KlassePojo;
import org.fossa.rolp.data.schueler.SchuelerLaso;
import org.fossa.rolp.data.schueler.SchuelerPojo;
import org.fossa.rolp.data.zuordnung.fachschueler.ZuordnungFachSchuelerLaso;
import org.fossa.rolp.data.zuordnung.fachschueler.ZuordnungFachSchuelerPojo;
import org.fossa.rolp.util.HintUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.vaadin.data.util.BeanItemContainer;

public class HintUtilsTest {
	
	class TestZuordnungFachSchuelerLaso extends ZuordnungFachSchuelerLaso {		
		private static final long serialVersionUID = -2180080600495210535L;
		public TestZuordnungFachSchuelerLaso() {
			super();
		}		
		public TestZuordnungFachSchuelerLaso(ZuordnungFachSchuelerPojo zuordnungFachSchuelerPojo) {
			super(zuordnungFachSchuelerPojo);
		}
		
		@Override
		public FachPojo getFach() {
			return zuordnungFachSchueler.getFach();
		}
		
		@Override
		public SchuelerPojo getSchueler() {
			return zuordnungFachSchueler.getSchueler();
		}
		
		@Override
		protected void writeToDatabase() {
		}
	}
	
	SchuelerLaso schueler1;
	SchuelerLaso schueler2;
	BeanItemContainer<ZuordnungFachSchuelerLaso> fachSchuelerZuordnungen = new BeanItemContainer<ZuordnungFachSchuelerLaso>(ZuordnungFachSchuelerLaso.class);
	BeanItemContainer<FachLaso> pflichtfaecherKeine = new BeanItemContainer<FachLaso>(FachLaso.class);
	BeanItemContainer<FachLaso> pflichtfaecherWelche = new BeanItemContainer<FachLaso>(FachLaso.class);
	FachLaso pflichtfach1;
	FachLaso kurs1;
	FachLaso kurs2;
	private KlassePojo klasse;

	@Before
	public void setUp() throws Exception {
		SchuelerPojo schueler1Pojo = new SchuelerPojo();
		schueler1Pojo.setVorname("Lukas");
		schueler1Pojo.setName("Podolski");
		schueler1Pojo.setId(1L);
		schueler1 = new SchuelerLaso(schueler1Pojo);
		
		SchuelerPojo schueler2Pojo = new SchuelerPojo();
		schueler2Pojo.setVorname("Bastian");
		schueler2Pojo.setName("Schweinsteiger");
		schueler2Pojo.setId(2L);
		schueler2 = new SchuelerLaso(schueler2Pojo);
		
		FachtypPojo pflichtfachTyp = new FachtypPojo();
		pflichtfachTyp.setId(1L);
		FachPojo pflichtfach1Pojo = new FachPojo();
		pflichtfach1Pojo.setFachtyp(pflichtfachTyp);
		pflichtfach1 = new FachLaso(pflichtfach1Pojo);
		
		FachtypPojo kursTyp = new FachtypPojo();
		kursTyp.setId(2L);
		
		FachPojo kurs1Pojo = new FachPojo();
		kurs1Pojo.setFachtyp(kursTyp);
		kurs1 = new FachLaso(kurs1Pojo);
		
		FachPojo kurs2Pojo = new FachPojo();
		kurs2Pojo.setFachtyp(kursTyp);
		kurs2 = new FachLaso(kurs2Pojo);
		
		ZuordnungFachSchuelerPojo zuordnungFS1Pojo = new ZuordnungFachSchuelerPojo();
		zuordnungFS1Pojo.setSchueler(schueler1Pojo);
		zuordnungFS1Pojo.setFach(pflichtfach1Pojo);
		
		ZuordnungFachSchuelerPojo zuordnungFS2Pojo = new ZuordnungFachSchuelerPojo();
		zuordnungFS2Pojo.setSchueler(schueler2Pojo);
		zuordnungFS2Pojo.setFach(pflichtfach1Pojo);
		
		ZuordnungFachSchuelerPojo zuordnungFS3Pojo = new ZuordnungFachSchuelerPojo();
		zuordnungFS3Pojo.setSchueler(schueler1Pojo);
		zuordnungFS3Pojo.setFach(kurs1Pojo);
		
		ZuordnungFachSchuelerPojo zuordnungFS4Pojo = new ZuordnungFachSchuelerPojo();
		zuordnungFS4Pojo.setSchueler(schueler1Pojo);
		zuordnungFS4Pojo.setFach(kurs2Pojo);
		
		ZuordnungFachSchuelerPojo zuordnungFS5Pojo = new ZuordnungFachSchuelerPojo();
		zuordnungFS5Pojo.setSchueler(schueler2Pojo);
		zuordnungFS5Pojo.setFach(kurs1Pojo);
		
		ZuordnungFachSchuelerPojo zuordnungFS6Pojo = new ZuordnungFachSchuelerPojo();
		zuordnungFS6Pojo.setSchueler(schueler2Pojo);
		zuordnungFS6Pojo.setFach(kurs2Pojo);
			
		fachSchuelerZuordnungen.addBean(new TestZuordnungFachSchuelerLaso(zuordnungFS1Pojo));
		fachSchuelerZuordnungen.addBean(new TestZuordnungFachSchuelerLaso(zuordnungFS2Pojo));
		fachSchuelerZuordnungen.addBean(new TestZuordnungFachSchuelerLaso(zuordnungFS5Pojo));
		fachSchuelerZuordnungen.addBean(new TestZuordnungFachSchuelerLaso(zuordnungFS6Pojo));
		
		klasse = new KlassePojo();
		klasse.setKlassenname("5b");
		
		pflichtfaecherWelche.addBean(pflichtfach1);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testHinweistextSchuelerKeineKurse() {
		String hinweis = HintUtils.createHinweistextSchuelerKeineKurse(fachSchuelerZuordnungen, schueler1);
		assertTrue(hinweis.contains(schueler1.getVorname()));
		assertTrue(hinweis.contains(schueler1.getName()));
		assertTrue(hinweis.contains(HintUtils.HEADER_WARNING));
		assertTrue(hinweis.contains(HintUtils.SEPERATOR));
	}
	
	@Test
	public void testHinweistextSchuelerHatKurse() {
		String hinweis = HintUtils.createHinweistextSchuelerKeineKurse(fachSchuelerZuordnungen, schueler2);
		assertTrue(!hinweis.contains(schueler2.getVorname()));
		assertTrue(!hinweis.contains(schueler2.getName()));
		assertTrue(!hinweis.contains(HintUtils.HEADER_WARNING));
		assertTrue(!hinweis.contains(HintUtils.SEPERATOR));
	}
	
	@Test
	public void testHinweistextKlasseKeinePflichtfaecher() {
		String hinweis = HintUtils.createHinweistextKlasseKeinePflichtfaecher(pflichtfaecherKeine, klasse);
		assertTrue(hinweis.contains(klasse.getKlassenname()));
		assertTrue(hinweis.contains(HintUtils.HEADER_WARNING));
		assertTrue(hinweis.contains(HintUtils.SEPERATOR));
	}
	
	@Test
	public void testHinweistextKlasseHatPflichtfaecher() {
		String hinweis = HintUtils.createHinweistextKlasseKeinePflichtfaecher(pflichtfaecherWelche, klasse);
		assertTrue(!hinweis.contains(klasse.getKlassenname()));
		assertTrue(!hinweis.contains(HintUtils.HEADER_WARNING));
		assertTrue(!hinweis.contains(HintUtils.SEPERATOR));
	}

}

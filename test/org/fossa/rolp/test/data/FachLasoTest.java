package org.fossa.rolp.test.data;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.fossa.rolp.data.fach.FachLaso;
import org.fossa.rolp.data.fach.FachPojo;
import org.fossa.rolp.data.fach.fachtyp.FachtypPojo;
import org.fossa.rolp.data.lehrer.LehrerPojo;
import org.fossa.vaadin.auth.data.FossaUserPojo;
import org.fossa.vaadin.test.testutils.LasoTestUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class FachLasoTest {

	private static final String firstnameEins = "Anne";
	private static final String lastnameEins = "Lachnitt";
	private static final String firstnameZwei = "Frank";
	private static final String lastnameZwei = "Kaddereit";
	public boolean triedToWrite = false;
	private TestFachLaso fachLaso;
	private Long id;
	private String fachbezeichnung;
	private FachtypPojo fachtyp;
	private LehrerPojo fachlehrerEins;
	private LehrerPojo fachlehrerZwei;
	private FossaUserPojo userEins;
	private FossaUserPojo userZwei;


	class TestFachLaso extends FachLaso {		
		private static final long serialVersionUID = -5817503987586227692L;
		public TestFachLaso() {
			super();
		}		
		public TestFachLaso(FachPojo fachPojo) {
			super(fachPojo);
		}
		@Override
		protected void writeToDatabase() {
			triedToWrite   = true;
		}
	}

	@Before
	public void setUp() throws Exception {
		fachLaso = new TestFachLaso();
		id = 87L;
		fachbezeichnung = "Quantenmechanik_17alpha";
		fachtyp = new FachtypPojo();
		fachtyp.setId(11L);
		userEins = new FossaUserPojo();
		userEins.setFirstname(firstnameEins);
		userEins.setLastname(lastnameEins);
		fachlehrerEins = new LehrerPojo();
		fachlehrerEins.setId(27L);
		fachlehrerEins.setUser(userEins);
		userZwei = new FossaUserPojo();
		userZwei.setFirstname(firstnameZwei);
		userZwei.setLastname(lastnameZwei);
		fachlehrerZwei = new LehrerPojo();
		fachlehrerZwei.setId(26L);
		fachlehrerZwei.setUser(userZwei);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testId() {
		assertTrue(fachLaso.getId() == null);
		assertFalse(triedToWrite);
		fachLaso.setId(id);
		assertTrue(triedToWrite);
		assertTrue(fachLaso.getId().equals(id));
	}

	@Test
	public void testFachLaso() {
		TestFachLaso fachLasoTest = new TestFachLaso();
		assertTrue(fachLasoTest.getPojo() != null);
		assertTrue(fachLasoTest.getFachtyp() == null);
		assertFalse(triedToWrite);
	}

	@Test
	public void testFachLasoFachPojo() {
		FachPojo fachPojo = new FachPojo();
		fachPojo.setId(id);
		assertTrue(new TestFachLaso(fachPojo).getFachtyp() == null);
		fachPojo.setFachtyp(fachtyp);
		TestFachLaso fachLasoTest = new TestFachLaso(fachPojo);
		assertTrue(fachLasoTest.getPojo().getId().equals(id));
		assertTrue(fachLasoTest.getFachtyp().equals(fachtyp));
		assertFalse(triedToWrite);
	}

	@Test
	public void testGetPojo() {
		FachPojo fachPojo = new FachPojo();
		fachPojo.setId(id);
		TestFachLaso fachLasoTest = new TestFachLaso(fachPojo);
		assertTrue(fachLasoTest.getPojo().getId().equals(id));
		assertFalse(triedToWrite);
	}

	@Test
	public void testFachbezeichnung() {
		assertTrue(fachLaso.getFachbezeichnung().equals(""));
		assertFalse(triedToWrite);
		fachLaso.setFachbezeichnung(fachbezeichnung);
		assertTrue(triedToWrite);
		assertTrue(fachLaso.getFachbezeichnung().equals(fachbezeichnung));
	}

	@Test
	public void testFachtyp() {
		assertTrue(fachLaso.getFachtyp() == null);
		assertFalse(triedToWrite);
		fachLaso.setFachtyp(fachtyp);
		assertTrue(triedToWrite);
		assertTrue(fachLaso.getFachtyp().equals(fachtyp));
	}
	
	@Test
	public void testFachlehrerEins() {
		assertTrue(fachLaso.getFachlehrerEins() == null);
		assertTrue(fachLaso.getFachlehrerEinsString().equals(" - "));
		assertFalse(triedToWrite);
		fachLaso.setFachlehrerEins(fachlehrerEins);
		assertTrue(triedToWrite);
		assertTrue(fachLaso.getFachlehrerEinsString().equals(firstnameEins + " " + lastnameEins));
		assertTrue(fachLaso.getFachlehrerEins().equals(fachlehrerEins));
		assertTrue(LasoTestUtils.checkIfClassHasMatchingGetMethod(FachLaso.class, FachLaso.FACHLEHRER_EINS_COLUMN));
	}
	
	@Test
	public void testFachlehrerZwei() {
		assertTrue(fachLaso.getFachlehrerZwei() == null);
		assertTrue(fachLaso.getFachlehrerZweiString().equals(" - "));
		assertFalse(triedToWrite);
		fachLaso.setFachlehrerZwei(fachlehrerZwei);
		assertTrue(triedToWrite);
		assertTrue(fachLaso.getFachlehrerZweiString().equals(firstnameZwei + " " + lastnameZwei));
		assertTrue(fachLaso.getFachlehrerZwei().equals(fachlehrerZwei));
		assertTrue(LasoTestUtils.checkIfClassHasMatchingGetMethod(FachLaso.class, FachLaso.FACHLEHRER_ZWEI_COLUMN));
	}
	
	@Test
	public void testZugewieseneSchueler() {
		assertTrue(LasoTestUtils.checkIfClassHasMatchingGetMethod(FachLaso.class, FachLaso.ZUGEWIESENE_SCHUELER_COLUMN));
	}
	
	@Test
	public void testErledigteFacheinschaetzungen() {
		assertTrue(LasoTestUtils.checkIfClassHasMatchingGetMethod(FachLaso.class, FachLaso.ERLEDIGTE_FACHEINSCHAETZUNGEN_COLUMN));
	}
	
	@Test
	public void testGetKlasse() {
		FachtypPojo fachtypKurs = new FachtypPojo();
		fachtypKurs.setId(2L);
		fachLaso.setFachtyp(fachtypKurs);		
		assertTrue(fachLaso.getKlasse().equals(" - "));
	}
}

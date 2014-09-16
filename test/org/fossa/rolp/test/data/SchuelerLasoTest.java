package org.fossa.rolp.test.data;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.fossa.rolp.data.einschaetzung.EinschaetzungLaso;
import org.fossa.rolp.data.einschaetzung.EinschaetzungPojo;
import org.fossa.rolp.data.klasse.KlassePojo;
import org.fossa.rolp.data.schueler.SchuelerLaso;
import org.fossa.rolp.data.schueler.SchuelerPojo;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class SchuelerLasoTest {

	SchuelerLaso schuelerLaso;
	private long id;
	public boolean triedToWrite = false;
	private String vorname;
	private String name;
	private String versetzungsvermerk;
	private KlassePojo klasse;
	private EinschaetzungLaso schuelereinschaetzung;
	
	class TestSchuelerLaso extends SchuelerLaso {		
		private static final long serialVersionUID = -5703353659674369783L;
		public TestSchuelerLaso() {
			super();
		}		
		public TestSchuelerLaso(SchuelerPojo schuelerPojo) {
			super(schuelerPojo);
		}
		public TestSchuelerLaso(KlassePojo klasse) {
			super(klasse);
		}
		@Override
		protected void writeToDatabase() {
			triedToWrite  = true;
		}
	}

	@Before
	public void setUp() throws Exception {
		schuelerLaso = new TestSchuelerLaso();
		id = 99L;
		vorname = "Tester";
		name = "Testfamily";
		versetzungsvermerk = "wird versetzt";
		klasse = new KlassePojo();
		klasse.setId(78L);
		EinschaetzungPojo schuelereinschaetzungPojo = new EinschaetzungPojo();
		schuelereinschaetzungPojo.setId(17L);
		schuelereinschaetzungPojo.setErledigt(true);
		schuelereinschaetzung = new EinschaetzungLaso(schuelereinschaetzungPojo);
	}

	@After
	public void tearDown() throws Exception {
	}
	
	@Test
	public void testId() {
		assertTrue(schuelerLaso.getId() == null);
		assertFalse(triedToWrite);
		schuelerLaso.setId(id);
		assertTrue(triedToWrite);
		assertTrue(schuelerLaso.getId().equals(id));
	}

	@Test
	public void testSchuelerLasoSchuelerPojo() {
		SchuelerPojo schuelerPojo = new SchuelerPojo();
		schuelerPojo.setId(id);
		assertTrue(new TestSchuelerLaso(schuelerPojo).getSchuelereinschaetzung() == null);
		schuelerPojo.setSchuelereinschaetzung(schuelereinschaetzung.getPojo());
		TestSchuelerLaso schuelerLasoTest = new TestSchuelerLaso(schuelerPojo);
		assertTrue(schuelerLasoTest.getPojo().getId().equals(id));
		assertTrue(schuelerLasoTest.getSchuelereinschaetzung().getPojo().equals(schuelereinschaetzung.getPojo()));
		assertFalse(triedToWrite);
	}

	@Test
	public void testSchuelerLaso() {
		TestSchuelerLaso schuelerLasoTest = new TestSchuelerLaso();
		assertTrue(schuelerLasoTest.getPojo() != null);
		assertTrue(schuelerLasoTest.getSchuelereinschaetzung() == null);
		assertFalse(triedToWrite);
	}

	@Test
	public void testSchuelerLasoKlasse() {		
		TestSchuelerLaso schuelerLasoTest = new TestSchuelerLaso(klasse);
		assertTrue(schuelerLasoTest.getPojo() != null);
		assertTrue(schuelerLasoTest.getSchuelereinschaetzung() == null);
		assertTrue(schuelerLasoTest.getKlasse() != null);
		assertFalse(triedToWrite);
	}

	@Test
	public void testGetPojo() {
		SchuelerPojo schuelerPojo = new SchuelerPojo();
		schuelerPojo.setId(id);
		TestSchuelerLaso schuelerLasoTest = new TestSchuelerLaso(schuelerPojo);
		assertTrue(schuelerLasoTest.getPojo().getId().equals(id));
		assertFalse(triedToWrite);
	}

	@Test
	public void testVorname() {
		assertTrue(schuelerLaso.getVorname().equals(""));
		assertFalse(triedToWrite);
		schuelerLaso.setVorname(vorname);
		assertTrue(triedToWrite);
		assertTrue(schuelerLaso.getVorname().equals(vorname));
	}

	@Test
	public void testName() {
		assertTrue(schuelerLaso.getName().equals(""));
		assertFalse(triedToWrite);
		schuelerLaso.setName(name);
		assertTrue(triedToWrite);
		assertTrue(schuelerLaso.getName().equals(name));
	}

	@Test
	public void testVersetzungsvermerk() {
		assertTrue(schuelerLaso.getVersetzungsvermerk().equals(""));
		assertFalse(triedToWrite);
		schuelerLaso.setVersetzungsvermerk(versetzungsvermerk);
		assertTrue(triedToWrite);
		assertTrue(schuelerLaso.getVersetzungsvermerk().equals(versetzungsvermerk));
	}

	@Test
	public void testKlasse() {
		assertTrue(schuelerLaso.getKlasse() == null);
		assertFalse(triedToWrite);
		schuelerLaso.setKlasse(klasse);
		assertTrue(triedToWrite);
		assertTrue(schuelerLaso.getKlasse().equals(klasse));
	}

	@Test
	public void testSchuelereinschaetzung() {
		assertTrue(schuelerLaso.getSchuelereinschaetzung() == null);
		assertFalse(triedToWrite);
		schuelerLaso.setSchuelereinschaetzung(schuelereinschaetzung);
		assertTrue(triedToWrite);
		assertTrue(schuelerLaso.getSchuelereinschaetzung().equals(schuelereinschaetzung));
	    System.out.println();
	}

	@Test
	public void testSchuelereinschaetzungNull() {
		assertTrue(schuelerLaso.getSchuelereinschaetzung() == null);
		assertFalse(triedToWrite);
		schuelerLaso.setSchuelereinschaetzung(null);
		assertTrue(triedToWrite);
		assertTrue(schuelerLaso.getSchuelereinschaetzung() == null);
	    System.out.println();
	}
	
	@Test
	public void testGetErledigt() {
		assertTrue(schuelerLaso.getErledigt() == false);
		assertFalse(triedToWrite);
		schuelerLaso.setSchuelereinschaetzung(schuelereinschaetzung);
		assertTrue(triedToWrite);
		assertTrue(schuelerLaso.getErledigt() == true);
	}

}

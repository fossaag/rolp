package org.fossa.rolp.test.data;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.fossa.rolp.data.einschaetzung.EinschaetzungLaso;
import org.fossa.rolp.data.einschaetzung.EinschaetzungPojo;
import org.fossa.rolp.data.fach.FachPojo;
import org.fossa.rolp.data.schueler.SchuelerPojo;
import org.fossa.rolp.data.zuordnung.fachschueler.ZuordnungFachSchuelerLaso;
import org.fossa.rolp.data.zuordnung.fachschueler.ZuordnungFachSchuelerPojo;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ZuordnungFachSchuelerLasoTest {

	private TestZuordnungFachSchuelerLaso zuordnungFachSchuelerLaso;
	public boolean triedToWrite;
	private long id;
	private SchuelerPojo schueler;
	private FachPojo fach;
	private EinschaetzungLaso facheinschaetzung;

	class TestZuordnungFachSchuelerLaso extends ZuordnungFachSchuelerLaso {		
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
			triedToWrite   = true;
		}
	}

	@Before
	public void setUp() throws Exception {
		zuordnungFachSchuelerLaso = new TestZuordnungFachSchuelerLaso();
		id = 81L;
		fach = new FachPojo();
		fach.setId(12L);
		fach.setFachbezeichnung("Madde");
		schueler = new SchuelerPojo();
		schueler.setVorname("Chris");
		schueler.setName("Debirg");
		schueler.setId(54L);
		EinschaetzungPojo facheinschaetzungPojo = new EinschaetzungPojo();
		facheinschaetzungPojo.setId(77L);
		facheinschaetzungPojo.setErledigt(true);
		facheinschaetzung = new EinschaetzungLaso(facheinschaetzungPojo);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testId() {
		assertTrue(zuordnungFachSchuelerLaso.getId() == null);
		assertFalse(triedToWrite);
		zuordnungFachSchuelerLaso.setId(id);
		assertTrue(triedToWrite);
		assertTrue(zuordnungFachSchuelerLaso.getId().equals(id));
	}

	@Test
	public void testZuordnungFachSchuelerLaso() {
		TestZuordnungFachSchuelerLaso zuordnungFachSchuelerLasoTest = new TestZuordnungFachSchuelerLaso();
		assertTrue(zuordnungFachSchuelerLasoTest.getPojo() != null);
		assertFalse(triedToWrite);
	}

	@Test
	public void testZuordnungFachSchuelerLasoZuordnungFachSchuelerPojo() {
		ZuordnungFachSchuelerPojo zuordnungFachSchuelerPojo = new ZuordnungFachSchuelerPojo();
		zuordnungFachSchuelerPojo.setId(id);
		assertTrue(new TestZuordnungFachSchuelerLaso(zuordnungFachSchuelerPojo).getFacheinschaetzung() == null);
		zuordnungFachSchuelerPojo.setFacheinschaetzung(facheinschaetzung.getPojo());
		TestZuordnungFachSchuelerLaso zuordnungFachSchuelerLasoTest = new TestZuordnungFachSchuelerLaso(zuordnungFachSchuelerPojo);
		assertTrue(zuordnungFachSchuelerLasoTest.getPojo().getId().equals(id));
		assertTrue(zuordnungFachSchuelerLasoTest.getFacheinschaetzung().getPojo().equals(facheinschaetzung.getPojo()));
		assertFalse(triedToWrite);
	}

	@Test
	public void testGetPojo() {
		ZuordnungFachSchuelerPojo zuordnungFachSchuelerPojo = new ZuordnungFachSchuelerPojo();
		zuordnungFachSchuelerPojo.setId(id);
		TestZuordnungFachSchuelerLaso zuordnungFachSchuelerLasoTest = new TestZuordnungFachSchuelerLaso(zuordnungFachSchuelerPojo);
		assertTrue(zuordnungFachSchuelerLasoTest.getPojo().getId().equals(id));
		assertFalse(triedToWrite);
	}
	
	@Test
	public void testSchueler() {
		assertTrue(zuordnungFachSchuelerLaso.getSchueler() == null);
		assertFalse(triedToWrite);
		zuordnungFachSchuelerLaso.setSchueler(schueler);
		assertTrue(triedToWrite);
		assertTrue(zuordnungFachSchuelerLaso.getSchueler().equals(schueler));
	}
	
	@Test
	public void testFach() {
		assertTrue(zuordnungFachSchuelerLaso.getFach() == null);
		assertFalse(triedToWrite);
		zuordnungFachSchuelerLaso.setFach(fach);
		assertTrue(triedToWrite);
		assertTrue(zuordnungFachSchuelerLaso.getFach().equals(fach));
	}
	
	@Test
	public void testFacheinschaetzung() {
		assertTrue(zuordnungFachSchuelerLaso.getFacheinschaetzung() == null);
		assertFalse(triedToWrite);
		zuordnungFachSchuelerLaso.setFacheinschaetzung(facheinschaetzung);
		assertTrue(triedToWrite);
		assertTrue(zuordnungFachSchuelerLaso.getFacheinschaetzung().equals(facheinschaetzung));
	}
	
	@Test
	public void testGetVorname() {
		assertTrue(zuordnungFachSchuelerLaso.getVorname().isEmpty());
		assertFalse(triedToWrite);
		zuordnungFachSchuelerLaso.setSchueler(schueler);
		assertTrue(triedToWrite);
		assertTrue(zuordnungFachSchuelerLaso.getVorname().equals(schueler.getVorname()));
	}	
	
	@Test
	public void testGetName() {
		assertTrue(zuordnungFachSchuelerLaso.getName().isEmpty());
		assertFalse(triedToWrite);
		zuordnungFachSchuelerLaso.setSchueler(schueler);
		assertTrue(triedToWrite);
		assertTrue(zuordnungFachSchuelerLaso.getName().equals(schueler.getName()));
	}
	
	@Test
	public void testGetErledigt() {
		assertTrue(zuordnungFachSchuelerLaso.getErledigt() == false);
		assertFalse(triedToWrite);
		zuordnungFachSchuelerLaso.setFacheinschaetzung(facheinschaetzung);
		assertTrue(triedToWrite);
		assertTrue(zuordnungFachSchuelerLaso.getErledigt() == true);
	}
	
	@Test	
	public void testGetFachbezeichnung() {
		assertTrue(zuordnungFachSchuelerLaso.getFachbezeichnung().equals(""));
		assertFalse(triedToWrite);
		zuordnungFachSchuelerLaso.setFach(fach);
		assertTrue(triedToWrite);
		assertTrue(zuordnungFachSchuelerLaso.getFachbezeichnung().equals(fach.getFachbezeichnung()));
	}

}

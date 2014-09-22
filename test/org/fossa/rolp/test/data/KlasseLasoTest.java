package org.fossa.rolp.test.data;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.fossa.rolp.data.einschaetzung.EinschaetzungLaso;
import org.fossa.rolp.data.einschaetzung.EinschaetzungPojo;
import org.fossa.rolp.data.klasse.KlasseLaso;
import org.fossa.rolp.data.klasse.KlassePojo;
import org.fossa.rolp.data.klasse.klassentyp.KlassentypPojo;
import org.fossa.rolp.data.lehrer.LehrerPojo;
import org.fossa.vaadin.test.testutils.LasoTestUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class KlasseLasoTest {

	KlasseLaso klasseLaso;
	private long id;
	public boolean triedToWrite = false;
	private String klassenname;
	private KlassentypPojo klassentyp;
	private LehrerPojo klassenlehrer;
	private EinschaetzungLaso klasseneinschaetzung;
	
	class TestKlasseLaso extends KlasseLaso {		
		private static final long serialVersionUID = 8075586396823198343L;
		public TestKlasseLaso() {
			super();
		}		
		public TestKlasseLaso(KlassePojo klasse) {
			super(klasse);
		}
		@Override
		protected void writeToDatabase() {
			triedToWrite  = true;
		}
	}

	@Before
	public void setUp() throws Exception {
		klasseLaso = new TestKlasseLaso();
		id = 99L;
		klassenname = "5a";
		klassentyp = new KlassentypPojo();
		klassentyp.setId(11L);
		klassenlehrer = new LehrerPojo();
		klassenlehrer.setId(78L);
		EinschaetzungPojo klasseneinschaetzungPojo = new EinschaetzungPojo();
		klasseneinschaetzungPojo.setId(47L);
		klasseneinschaetzung = new EinschaetzungLaso(klasseneinschaetzungPojo);
	}

	@After
	public void tearDown() throws Exception {
	}
	
	@Test
	public void testId() {
		assertTrue(klasseLaso.getId() == null);
		assertFalse(triedToWrite);
		klasseLaso.setId(id);
		assertTrue(triedToWrite);
		assertTrue(klasseLaso.getId().equals(id));
	}

	@Test
	public void testKlasseLasoKlassePojo() {
		KlassePojo klassePojo = new KlassePojo();
		klassePojo.setId(id);
		assertTrue(new TestKlasseLaso(klassePojo).getKlassentyp() == null);
		klassePojo.setKlassentyp(klassentyp);
		assertTrue(new TestKlasseLaso(klassePojo).getKlasseneinschaetzung() == null);
		klassePojo.setKlasseneinschaetzung(klasseneinschaetzung.getPojo());
		TestKlasseLaso klasseLasoTest = new TestKlasseLaso(klassePojo);
		assertTrue(klasseLasoTest.getPojo().getId().equals(id));
		assertTrue(klasseLasoTest.getKlassentyp().equals(klassentyp));
		assertTrue(klasseLasoTest.getKlasseneinschaetzung().getPojo().equals(klasseneinschaetzung.getPojo()));
		assertFalse(triedToWrite);
	}

	@Test
	public void testKlasseLaso() {
		TestKlasseLaso klasseLasoTest = new TestKlasseLaso();
		assertTrue(klasseLasoTest.getPojo() != null);
		assertTrue(klasseLasoTest.getKlassentyp() == null);
		assertFalse(triedToWrite);
	}

	@Test
	public void testGetPojo() {
		klasseLaso.setId(id);
		assertTrue(klasseLaso.getPojo().getId().equals(id));
	}

	@Test
	public void testKlassenname() {
		assertTrue(klasseLaso.getKlassenname().equals(""));
		assertFalse(triedToWrite);
		klasseLaso.setKlassenname(klassenname);
		assertTrue(triedToWrite);
		assertTrue(klasseLaso.getKlassenname().equals(klassenname));
	}
	
	@Test
	public void testKlassentyp() {
		assertTrue(klasseLaso.getKlassentypString().equals(" - "));
		assertTrue(klasseLaso.getKlassentyp() == null);
		assertFalse(triedToWrite);
		klasseLaso.setKlassentyp(klassentyp);
		assertTrue(triedToWrite);
		assertTrue(klasseLaso.getKlassentyp().equals(klassentyp));
		assertTrue(klasseLaso.getKlassentypString().equals(klassentyp.getKlassentyp()));
		assertTrue(LasoTestUtils.checkIfClassHasMatchingGetMethod(KlasseLaso.class, KlassentypPojo.KLASSENTYP_COLUMN));
	}


	@Test
	public void testKlassenlehrer() {
		assertTrue(klasseLaso.getKlassenlehrer() == null);
		assertFalse(triedToWrite);
		klasseLaso.setKlassenlehrer(klassenlehrer);
		assertTrue(triedToWrite);
		assertTrue(klasseLaso.getKlassenlehrer().equals(klassenlehrer));
	}
	
	@Test
	public void testKlasseneinschaetzung() {
		assertTrue(klasseLaso.getKlasseneinschaetzung() == null);
		assertFalse(triedToWrite);
		klasseLaso.setKlasseneinschaetzung(klasseneinschaetzung);
		assertTrue(triedToWrite);
		assertTrue(klasseLaso.getKlasseneinschaetzung().equals(klasseneinschaetzung));
	}
	
	@Test
	public void testKlasseneinschaetzungNull() {
		assertTrue(klasseLaso.getKlasseneinschaetzung() == null);
		assertFalse(triedToWrite);
		klasseLaso.setKlasseneinschaetzung(null);
		assertTrue(triedToWrite);
		assertTrue(klasseLaso.getKlasseneinschaetzung() == null);
	}
}

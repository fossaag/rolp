package org.fossa.rolp.test.data;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.fossa.rolp.data.einschaetzung.EinschaetzungLaso;
import org.fossa.rolp.data.einschaetzung.EinschaetzungPojo;
import org.fossa.rolp.data.klasse.KlasseLaso;
import org.fossa.rolp.data.klasse.KlassePojo;
import org.fossa.rolp.data.lehrer.LehrerPojo;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class KlasseLasoTest {

	KlasseLaso klasseLaso;
	private long id;
	public boolean triedToWrite = false;
	private String klassenname;
	private int abgangsjahr;
	private LehrerPojo klassenlehrer;
	private EinschaetzungLaso klasseneinschaetzung;
	
	class TestKlasseLaso extends KlasseLaso {		
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
		abgangsjahr = 1945;
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
		assertTrue(new TestKlasseLaso(klassePojo).getKlasseneinschaetzung() == null);
		klassePojo.setKlasseneinschaetzung(klasseneinschaetzung.getPojo());
		TestKlasseLaso klasseLasoTest = new TestKlasseLaso(klassePojo);
		assertTrue(klasseLasoTest.getPojo().getId().equals(id));
		assertTrue(klasseLasoTest.getKlasseneinschaetzung().getPojo().equals(klasseneinschaetzung.getPojo()));
		assertFalse(triedToWrite);
	}

	@Test
	public void testKlasseLaso() {
		TestKlasseLaso klasseLasoTest = new TestKlasseLaso();
		assertTrue(klasseLasoTest.getPojo() != null);
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
	public void testAbgangsjahr() {
		assertTrue(klasseLaso.getAbgangsjahr()!=(1945));
		assertFalse(triedToWrite);
		klasseLaso.setAbgangsjahr(abgangsjahr);
		assertTrue(triedToWrite);
		assertTrue(klasseLaso.getAbgangsjahr()==(abgangsjahr));
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

package org.fossa.rolp.test.data;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Date;

import org.fossa.rolp.data.klasse.halbjahr.HalbjahrPojo;
import org.fossa.rolp.data.leb.LebSettingsLaso;
import org.fossa.rolp.data.leb.LebSettingsPojo;
import org.fossa.rolp.util.DateUtil;
import org.fossa.vaadin.test.testutils.LasoTestUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class LebSettingsLasoTest {

	LebSettingsLaso lebsettingsLaso;
	private long id;
	public boolean triedToWrite = false;
	private Date zeugnisausgabedatum;
	private HalbjahrPojo halbjahr;
	private int anzahlErsteKlassen;
	private int letzteKlassenstufe;
	
	class TestLebSettingsLaso extends LebSettingsLaso {		
		private static final long serialVersionUID = -8142640747495140753L;
		public TestLebSettingsLaso() {
			super();
		}		
		public TestLebSettingsLaso(LebSettingsPojo lebsettings) {
			super(lebsettings);
		}
		@Override
		protected void writeToDatabase() {
			triedToWrite  = true;
		}
	}

	@Before
	public void setUp() throws Exception {
		lebsettingsLaso = new TestLebSettingsLaso();
		id = 99L;
		halbjahr = new HalbjahrPojo();
		halbjahr.setId(13L);
		zeugnisausgabedatum = new Date();
		anzahlErsteKlassen = 2;
		letzteKlassenstufe = 8;
	}

	@After
	public void tearDown() throws Exception {
	}
	
	@Test
	public void testId() {
		assertTrue(lebsettingsLaso.getId() == null);
		assertFalse(triedToWrite);
		lebsettingsLaso.setId(id);
		assertTrue(triedToWrite);
		assertTrue(lebsettingsLaso.getId().equals(id));
	}

	@Test
	public void testLebSettingsLasoLebSettingsPojo() {
		LebSettingsPojo lebsettingsPojo = new LebSettingsPojo();
		lebsettingsPojo.setId(id);
		lebsettingsPojo.setHalbjahr(halbjahr);
		TestLebSettingsLaso lebsettingsLasoTest = new TestLebSettingsLaso(lebsettingsPojo);
		assertTrue(lebsettingsLasoTest.getPojo().getId().equals(id));
		assertTrue(lebsettingsLasoTest.getHalbjahr().equals(halbjahr));
		assertFalse(triedToWrite);
	}

	@Test
	public void testLebSettingsLaso() {
		TestLebSettingsLaso lebsettingsLasoTest = new TestLebSettingsLaso();
		assertTrue(lebsettingsLasoTest.getPojo() != null);
		assertFalse(triedToWrite);
	}

	@Test
	public void testGetPojo() {
		lebsettingsLaso.setId(id);
		assertTrue(lebsettingsLaso.getPojo().getId().equals(id));
	}

	@Test
	public void testHalbjahr() {
		assertTrue(lebsettingsLaso.getHalbjahr() == null);
		assertTrue(lebsettingsLaso.getHalbjahrString().equals(" - "));
		assertFalse(triedToWrite);
		lebsettingsLaso.setHalbjahr(halbjahr);
		assertTrue(triedToWrite);
		assertTrue(lebsettingsLaso.getHalbjahrString().equals(halbjahr.getHalbjahr()));
		assertTrue(lebsettingsLaso.getHalbjahr().equals(halbjahr));
		assertTrue(LasoTestUtils.checkIfClassHasMatchingGetMethod(LebSettingsLaso.class, LebSettingsLaso.HALBJAHR_COLUMN));
	}

	@Test
	public void testZeugnisausgabedatum() {
		assertTrue(lebsettingsLaso.getZeugnisausgabedatum() == null);
		assertTrue(lebsettingsLaso.getZeugnisausgabedatumString().equals(" - "));
		assertFalse(triedToWrite);
		lebsettingsLaso.setZeugnisausgabedatum((Date) zeugnisausgabedatum.clone());
		assertTrue(triedToWrite);
		assertTrue(lebsettingsLaso.getZeugnisausgabedatumString().equals(DateUtil.showDateString(zeugnisausgabedatum)));
		assertTrue(lebsettingsLaso.getZeugnisausgabedatum().equals(DateUtil.cutTimeOffDate(zeugnisausgabedatum)));
		assertTrue(LasoTestUtils.checkIfClassHasMatchingGetMethod(LebSettingsLaso.class, LebSettingsLaso.ZEUGNISAUSGABEDATUM_COLUMN));
	}
	
	@Test
	public void testAnzahlErsteKlassen() {
		assertTrue(lebsettingsLaso.getAnzahlErsteKlassen() == null);
		assertFalse(triedToWrite);
		lebsettingsLaso.setAnzahlErsteKlassen(anzahlErsteKlassen);
		assertTrue(triedToWrite);
		assertTrue(lebsettingsLaso.getAnzahlErsteKlassen().equals(anzahlErsteKlassen));
	}
	
	@Test
	public void testLetzteKlassenstufe() {
		assertTrue(lebsettingsLaso.getLetzteKlassenstufe() == null);
		assertFalse(triedToWrite);
		lebsettingsLaso.setLetzteKlassenstufe(letzteKlassenstufe);
		assertTrue(triedToWrite);
		assertTrue(lebsettingsLaso.getLetzteKlassenstufe().equals(letzteKlassenstufe));
	}
}

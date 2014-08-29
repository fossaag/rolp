package org.fossa.rolp.test.util;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.fossa.rolp.data.klasse.KlasseLaso;
import org.fossa.rolp.data.klasse.KlassePojo;
import org.fossa.rolp.util.KlassenstufenUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.vaadin.data.util.BeanItemContainer;

public class KlassenstufenUtilsTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testGetKlassenstufeValidNameOneDigit() {
		assertTrue("must be identical", KlassenstufenUtils.getKlassenstufe("9a") == 9);
	}

	@Test
	public void testGetKlassenstufeValidNameTenClass() {
		assertTrue("must be identical", KlassenstufenUtils.getKlassenstufe("10c") == 10);
	}

	@Test
	public void testGetKlassenstufeInvalidEntry() {
		try {
			KlassenstufenUtils.getKlassenstufe("ca12");
			fail("Exception not thrown");
		} catch (NumberFormatException e) {
			
		}
	}

	@Test
	public void testErhoeheKlassenstufeValidNameOneDigit() {
		assertTrue("must be identical", KlassenstufenUtils.erhoeheKlassenstufe("9a").equals("10a"));
	}

	@Test
	public void testErhoeheKlassenstufeValidNameTenClass() {
		assertTrue("must be identical", KlassenstufenUtils.erhoeheKlassenstufe("10c").equals("11c"));
	}
	
	@Test
	public void testErhoeheKlassenstufeValidNameNoSubclass() {
		assertTrue("must be identical", KlassenstufenUtils.erhoeheKlassenstufe("8").equals("9"));
	}

	@Test
	public void testErhoeheKlassenstufeInvalidEntry() {
		try {
			KlassenstufenUtils.erhoeheKlassenstufe("ca12");
			fail("Exception not thrown");
		} catch (NumberFormatException e) {
			
		}
	}
	
	@Test
	public void testGenerateKlassennameForKlassenstufeFirst() {
		BeanItemContainer<KlasseLaso> klassenContainer = new BeanItemContainer<KlasseLaso>(KlasseLaso.class);
		assertTrue("must be identical", KlassenstufenUtils.generateKlassennameForKlassenstufe(2, klassenContainer).equals("2a"));
	}
	
	@Test
	public void testGenerateKlassennameForKlassenstufeRaised() {
		BeanItemContainer<KlasseLaso> klassenContainer = new BeanItemContainer<KlasseLaso>(KlasseLaso.class);
		KlassePojo klasse = new KlassePojo();
		klasse.setKlassenname("7a");
		klassenContainer.addBean(new KlasseLaso(klasse));
		assertTrue("must be identical", KlassenstufenUtils.generateKlassennameForKlassenstufe(7, klassenContainer).equals("7b"));
	}
	
	@Test
	public void testGenerateKlassennameForKlassenstufeMaximum() {
		BeanItemContainer<KlasseLaso> klassenContainer = new BeanItemContainer<KlasseLaso>(KlasseLaso.class);
		for (int i=0;i<26;i++) {
			KlassePojo klasse = new KlassePojo();
			assertTrue("must not be null", KlassenstufenUtils.generateKlassennameForKlassenstufe(7, klassenContainer) != null);
			klasse.setKlassenname(KlassenstufenUtils.generateKlassennameForKlassenstufe(7, klassenContainer));
			klassenContainer.addBean(new KlasseLaso(klasse));
		}
		assertTrue("must be null", KlassenstufenUtils.generateKlassennameForKlassenstufe(7, klassenContainer) == null);
	}
	
	@Test
	public void testGetLebFontSize10() {
		assertTrue("must be identical", KlassenstufenUtils.getLebFontSize("5d") == 10);
	}

	@Test
	public void testGetLebFontSize12() {
		assertTrue("must be identical", KlassenstufenUtils.getLebFontSize("4c") == 12);
	}
	
	@Test
	public void testGetLebZeilenabstandAsFactor150() {
		assertTrue("must be identical", KlassenstufenUtils.getLebZeilenabstandAsFactor("5d") == 1.5f);
	}

	@Test
	public void testGetLebZeilenabstandAsFactor130() {
		assertTrue("must be identical", KlassenstufenUtils.getLebZeilenabstandAsFactor("4c") == 1.3f);
	}

}

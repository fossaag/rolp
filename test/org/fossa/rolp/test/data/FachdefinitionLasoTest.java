package org.fossa.rolp.test.data;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.fossa.rolp.data.fach.fachdefinition.FachdefinitionLaso;
import org.fossa.rolp.data.fach.fachdefinition.FachdefinitionPojo;
import org.fossa.rolp.data.fach.fachtyp.FachtypPojo;
import org.fossa.vaadin.test.testutils.LasoTestUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class FachdefinitionLasoTest {

	public boolean triedToWrite = false;
	private TestFachdefinitionLaso fachdefinitionLaso;
	private Long id;
	private String fachbezeichnung;
	private FachtypPojo fachtyp;

	class TestFachdefinitionLaso extends FachdefinitionLaso {		
		private static final long serialVersionUID = -7878313333362030072L;
		public TestFachdefinitionLaso() {
			super();
		}		
		public TestFachdefinitionLaso(FachdefinitionPojo fachdefinitionPojo) {
			super(fachdefinitionPojo);
		}
		@Override
		protected void writeToDatabase() {
			triedToWrite   = true;
		}
	}

	@Before
	public void setUp() throws Exception {
		fachdefinitionLaso = new TestFachdefinitionLaso();
		id = 87L;
		fachbezeichnung = "Quantenmechanik_17alpha";
		fachtyp = new FachtypPojo();
		fachtyp.setId(11L);
		fachtyp.setFachtyp("Pflichtfach");
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testId() {
		assertTrue(fachdefinitionLaso.getId() == null);
		assertFalse(triedToWrite);
		fachdefinitionLaso.setId(id);
		assertTrue(triedToWrite);
		assertTrue(fachdefinitionLaso.getId().equals(id));
	}

	@Test
	public void testFachLaso() {
		TestFachdefinitionLaso fachdefinitionLasoTest = new TestFachdefinitionLaso();
		assertTrue(fachdefinitionLasoTest.getPojo() != null);
		assertTrue(fachdefinitionLasoTest.getFachtyp() == null);
		assertFalse(triedToWrite);
	}

	@Test
	public void testFachLasoFachdefinitionPojo() {
		FachdefinitionPojo fachdefinitionPojo = new FachdefinitionPojo();
		fachdefinitionPojo.setId(id);
		assertTrue(new TestFachdefinitionLaso(fachdefinitionPojo).getFachtyp() == null);
		fachdefinitionPojo.setFachtyp(fachtyp);
		TestFachdefinitionLaso fachdefinitionLasoTest = new TestFachdefinitionLaso(fachdefinitionPojo);
		assertTrue(fachdefinitionLasoTest.getPojo().getId().equals(id));
		assertTrue(fachdefinitionLasoTest.getFachtyp().equals(fachtyp));
		assertFalse(triedToWrite);
	}

	@Test
	public void testGetPojo() {
		FachdefinitionPojo fachdefinitionPojo = new FachdefinitionPojo();
		fachdefinitionPojo.setId(id);
		TestFachdefinitionLaso fachdefinitionLasoTest = new TestFachdefinitionLaso(fachdefinitionPojo);
		assertTrue(fachdefinitionLasoTest.getPojo().getId().equals(id));
		assertFalse(triedToWrite);
	}

	@Test
	public void testFachbezeichnung() {
		assertTrue(fachdefinitionLaso.getFachbezeichnung().equals(""));
		assertFalse(triedToWrite);
		fachdefinitionLaso.setFachbezeichnung(fachbezeichnung);
		assertTrue(triedToWrite);
		assertTrue(fachdefinitionLaso.getFachbezeichnung().equals(fachbezeichnung));
	}

	@Test
	public void testFachtyp() {
		assertTrue(fachdefinitionLaso.getFachtyp() == null);
		assertFalse(triedToWrite);
		fachdefinitionLaso.setFachtyp(fachtyp);
		assertTrue(triedToWrite);
		assertTrue(fachdefinitionLaso.getFachtyp().equals(fachtyp));
		assertTrue(fachdefinitionLaso.getFachtypString().equals(fachtyp.getFachtyp()));
		assertTrue(LasoTestUtils.checkIfClassHasMatchingGetMethod(FachdefinitionLaso.class, FachdefinitionPojo.FACHTYP_COLUMN));
	}
}

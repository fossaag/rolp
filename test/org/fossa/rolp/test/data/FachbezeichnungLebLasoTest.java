package org.fossa.rolp.test.data;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.fossa.rolp.data.fach.fachdefinition.FachdefinitionPojo;
import org.fossa.rolp.data.fach.fachdefinition.leb.FachbezeichnungLebLaso;
import org.fossa.rolp.data.fach.fachdefinition.leb.FachbezeichnungLebPojo;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class FachbezeichnungLebLasoTest {

	public boolean triedToWrite = false;
	private TestFachbezeichnungLebLaso fachbezeichnungLebLaso;
	private Long id;
	private String bezeichnung;
	private FachdefinitionPojo fachdefinition;

	class TestFachbezeichnungLebLaso extends FachbezeichnungLebLaso {		
		private static final long serialVersionUID = -5229640312128420422L;
		public TestFachbezeichnungLebLaso() {
			super();
		}		
		public TestFachbezeichnungLebLaso(FachbezeichnungLebPojo fachbezeichnungLebPojo) {
			super(fachbezeichnungLebPojo);
		}
		@Override
		protected void writeToDatabase() {
			triedToWrite   = true;
		}
	}

	@Before
	public void setUp() throws Exception {
		fachbezeichnungLebLaso = new TestFachbezeichnungLebLaso();
		id = 17L;
		bezeichnung = "Mechanik";
		fachdefinition = new FachdefinitionPojo();
		fachdefinition.setId(231L);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testId() {
		assertTrue(fachbezeichnungLebLaso.getId() == null);
		assertFalse(triedToWrite);
		fachbezeichnungLebLaso.setId(id);
		assertTrue(triedToWrite);
		assertTrue(fachbezeichnungLebLaso.getId().equals(id));
	}

	@Test
	public void testFachLaso() {
		TestFachbezeichnungLebLaso fachbezeichnungLebLasoTest = new TestFachbezeichnungLebLaso();
		assertTrue(fachbezeichnungLebLasoTest.getPojo() != null);
		assertTrue(fachbezeichnungLebLasoTest.getFachdefinition() == null);
		assertFalse(triedToWrite);
	}

	@Test
	public void testFachLasoFachbezeichnungLebPojo() {
		FachbezeichnungLebPojo fachbezeichnungLebPojo = new FachbezeichnungLebPojo();
		fachbezeichnungLebPojo.setId(id);
		assertTrue(new TestFachbezeichnungLebLaso(fachbezeichnungLebPojo).getFachdefinition() == null);
		fachbezeichnungLebPojo.setFachdefinition(fachdefinition);
		TestFachbezeichnungLebLaso fachbezeichnungLebLasoTest = new TestFachbezeichnungLebLaso(fachbezeichnungLebPojo);
		assertTrue(fachbezeichnungLebLasoTest.getPojo().getId().equals(id));
		assertTrue(fachbezeichnungLebLasoTest.getFachdefinition().equals(fachdefinition));
		assertFalse(triedToWrite);
	}

	@Test
	public void testGetPojo() {
		FachbezeichnungLebPojo fachbezeichnungLebPojo = new FachbezeichnungLebPojo();
		fachbezeichnungLebPojo.setId(id);
		TestFachbezeichnungLebLaso fachbezeichnungLebLasoTest = new TestFachbezeichnungLebLaso(fachbezeichnungLebPojo);
		assertTrue(fachbezeichnungLebLasoTest.getPojo().getId().equals(id));
		assertFalse(triedToWrite);
	}

	@Test
	public void testBezeichnung() {
		assertTrue(fachbezeichnungLebLaso.getBezeichnung().equals(""));
		assertFalse(triedToWrite);
		fachbezeichnungLebLaso.setBezeichnung(bezeichnung);
		assertTrue(triedToWrite);
		assertTrue(fachbezeichnungLebLaso.getBezeichnung().equals(bezeichnung));
	}

	@Test
	public void testFachdefinition() {
		assertTrue(fachbezeichnungLebLaso.getFachdefinition() == null);
		assertFalse(triedToWrite);
		fachbezeichnungLebLaso.setFachdefinition(fachdefinition);
		assertTrue(triedToWrite);
		assertTrue(fachbezeichnungLebLaso.getFachdefinition().equals(fachdefinition));
	}
	
}

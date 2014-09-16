package org.fossa.rolp.test.data;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.fossa.rolp.data.einschaetzung.EinschaetzungLaso;
import org.fossa.rolp.data.einschaetzung.EinschaetzungPojo;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class EinschaetzungLasoTest {

	public boolean triedToWrite;
	private long id;
	private String einschaetzungstext;
	private Boolean erledigt;
	private TestEinschaetzungLaso einschaetzungLaso;

	class TestEinschaetzungLaso extends EinschaetzungLaso {		
		private static final long serialVersionUID = -2162428687327258376L;
		public TestEinschaetzungLaso() {
			super();
		}		
		public TestEinschaetzungLaso(EinschaetzungPojo einschaetzungPojo) {
			super(einschaetzungPojo);
		}
		@Override
		protected void writeToDatabase() {
			triedToWrite   = true;
		}
	}

	@Before
	public void setUp() throws Exception {
		einschaetzungLaso = new TestEinschaetzungLaso();
		id = 87L;
		einschaetzungstext = "deppert";
		erledigt = true;
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testId() {
		assertTrue(einschaetzungLaso.getId() == null);
		assertFalse(triedToWrite);
		einschaetzungLaso.setId(id);
		assertTrue(triedToWrite);
		assertTrue(einschaetzungLaso.getId().equals(id));
	}

	@Test
	public void testEinschaetzungLaso() {
		TestEinschaetzungLaso einschaetzungLasoTest = new TestEinschaetzungLaso();
		assertTrue(einschaetzungLasoTest.getPojo() != null);
		assertFalse(triedToWrite);
	}

	@Test
	public void testEinschaetzungLasoEinschaetzungPojo() {
		EinschaetzungPojo einschaetzungPojo = new EinschaetzungPojo();
		einschaetzungPojo.setId(id);
		TestEinschaetzungLaso einschaetzungLasoTest = new TestEinschaetzungLaso(einschaetzungPojo);
		assertTrue(einschaetzungLasoTest.getPojo().getId().equals(id));
		assertFalse(triedToWrite);
	}

	@Test
	public void testGetPojo() {
		EinschaetzungPojo einschaetzungPojo = new EinschaetzungPojo();
		einschaetzungPojo.setId(id);
		TestEinschaetzungLaso einschaetzungLasoTest = new TestEinschaetzungLaso(einschaetzungPojo);
		assertTrue(einschaetzungLasoTest.getPojo().getId().equals(id));
		assertFalse(triedToWrite);
	}

	@Test
	public void testEinschaetzungstext() {
		assertTrue(einschaetzungLaso.getEinschaetzungstext().equals(""));
		assertFalse(triedToWrite);
		einschaetzungLaso.setEinschaetzungstext(einschaetzungstext);
		assertTrue(triedToWrite);
		assertTrue(einschaetzungLaso.getEinschaetzungstext().equals(einschaetzungstext));
	}

	@Test
	public void testErledigt() {
		assertTrue(einschaetzungLaso.getErledigt() == false);
		assertFalse(triedToWrite);
		einschaetzungLaso.setErledigt(erledigt);
		assertTrue(triedToWrite);
		assertTrue(einschaetzungLaso.getErledigt() == true);
	}
}

package org.fossa.rolp.test.data;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Date;

import org.fossa.rolp.data.lehrer.LehrerPojo;
import org.fossa.rolp.data.lehrer.lehrerblog.LehrerBlogLaso;
import org.fossa.rolp.data.lehrer.lehrerblog.LehrerBlogPojo;
import org.fossa.rolp.util.DateUtil;
import org.fossa.vaadin.auth.data.FossaUserPojo;
import org.fossa.vaadin.test.testutils.LasoTestUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class LehrerBlogLasoTest {

	private static final String firstname = "Anne";
	private static final String lastname = "Lachnitt";
	public boolean triedToWrite = false;
	private TestLehrerBlogLaso lehrerBlogLaso;
	private Long id;
	private String ereignis;
	private Date timestamp;
	private LehrerPojo lehrer;
	private FossaUserPojo user;


	class TestLehrerBlogLaso extends LehrerBlogLaso {		
		private static final long serialVersionUID = 1534289749754405990L;
		public TestLehrerBlogLaso() {
			super();
		}		
		public TestLehrerBlogLaso(LehrerBlogPojo lehrerBlogPojo) {
			super(lehrerBlogPojo);
		}
		@Override
		protected void writeToDatabase() {
			triedToWrite   = true;
		}
	}

	@Before
	public void setUp() throws Exception {
		lehrerBlogLaso = new TestLehrerBlogLaso();
		id = 87L;
		ereignis = "Fach zugewiesen";
		timestamp = new Date();
		user = new FossaUserPojo();
		user.setFirstname(firstname);
		user.setLastname(lastname);
		lehrer = new LehrerPojo();
		lehrer.setId(72L);
		lehrer.setUser(user);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testId() {
		assertTrue(lehrerBlogLaso.getId() == null);
		assertFalse(triedToWrite);
		lehrerBlogLaso.setId(id);
		assertTrue(triedToWrite);
		assertTrue(lehrerBlogLaso.getId().equals(id));
	}

	@Test
	public void testLehrerBlogLaso() {
		TestLehrerBlogLaso lehrerBlogLasoTest = new TestLehrerBlogLaso();
		assertTrue(lehrerBlogLasoTest.getPojo() != null);
		assertFalse(triedToWrite);
	}

	@Test
	public void testGetPojo() {
		LehrerBlogPojo lehrerBlogPojo = new LehrerBlogPojo();
		lehrerBlogPojo.setId(id);
		TestLehrerBlogLaso lehrerBlogLasoTest = new TestLehrerBlogLaso(lehrerBlogPojo);
		assertTrue(lehrerBlogLasoTest.getPojo().getId().equals(id));
		assertFalse(triedToWrite);
	}

	@Test
	public void testEregnis() {
		assertTrue(lehrerBlogLaso.getEreignis() == null);
		assertFalse(triedToWrite);
		lehrerBlogLaso.setEreignis(ereignis);
		assertTrue(triedToWrite);
		assertTrue(lehrerBlogLaso.getEreignis().equals(ereignis));
	}
	
	@Test
	public void testLehrerBloglehrerEins() {
		assertTrue(lehrerBlogLaso.getLehrer() == null);
		assertFalse(triedToWrite);
		lehrerBlogLaso.setLehrer(lehrer);
		assertTrue(triedToWrite);
		assertTrue(lehrerBlogLaso.getLehrer().equals(lehrer));
	}
	
	@Test
	public void testTimestamp() {
		assertTrue(lehrerBlogLaso.getTimestamp() == null);
		assertTrue(lehrerBlogLaso.getZeitstempel().equals(" - "));
		assertFalse(triedToWrite);
		lehrerBlogLaso.setTimestamp((Date) timestamp.clone());
		assertTrue(triedToWrite);
		assertTrue(lehrerBlogLaso.getZeitstempel().equals(DateUtil.showDateString(timestamp)));
		assertTrue(lehrerBlogLaso.getTimestamp().equals(timestamp));
		assertTrue(LasoTestUtils.checkIfClassHasMatchingGetMethod(LehrerBlogLaso.class, LehrerBlogLaso.ZEITSTEMPEL_COLUMN));
	}
}

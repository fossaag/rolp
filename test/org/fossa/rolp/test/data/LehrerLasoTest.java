package org.fossa.rolp.test.data;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.fossa.rolp.data.lehrer.LehrerLaso;
import org.fossa.rolp.data.lehrer.LehrerPojo;
import org.fossa.vaadin.auth.data.FossaUserLaso;
import org.fossa.vaadin.auth.data.FossaUserPojo;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class LehrerLasoTest {

	LehrerLaso lehrerLaso;
	private long id;
	public boolean triedToWrite = false;
	private String username;
	private String password;
	private String formPwOne;
	private String formPwTwo;
	private String firstname;
	private String lastname;
	private String mail;
	private FossaUserLaso user;
	
	class TestLehrerLaso extends LehrerLaso {		
		private static final long serialVersionUID = -7386648083093165125L;
		public TestLehrerLaso(LehrerPojo lehrer) {
			super(lehrer);
			if (lehrer.getUser() != null) {
				this.userLaso = new TestFossaUserLaso(lehrer.getUser());
			} else {
				this.userLaso = new TestFossaUserLaso();
			}
		}
		public TestLehrerLaso() {
			super();
			userLaso = new TestFossaUserLaso();
		}
		@Override
		protected void writeToDatabase() {
			triedToWrite  = true;
		}
	}
	
	class TestFossaUserLaso extends FossaUserLaso {		
		private static final long serialVersionUID = 8536096301511175118L;
		public TestFossaUserLaso() {
			super();
		}		
		public TestFossaUserLaso(FossaUserPojo user) {
			super(user);
		}
		@Override
		protected void writeToDatabase() {
		}
	}

	@Before
	public void setUp() throws Exception {
		lehrerLaso = new TestLehrerLaso();
		id = 66L;
		FossaUserPojo userPojo = new FossaUserPojo();
		username = "ncibo";
		password = "chibo";
		firstname = "Nick";
		lastname = "Ciborra";
		mail = "nick@cibo.de";
		userPojo.setId(12L);
		user = new FossaUserLaso(userPojo);
		formPwOne = "test";
		formPwTwo = "test";
	}

	@After
	public void tearDown() throws Exception {
	}
	
	@Test
	public void testId() {
		assertTrue(lehrerLaso.getId() == null);
		assertFalse(triedToWrite);
		lehrerLaso.setId(id);
		assertTrue(triedToWrite);
		assertTrue(lehrerLaso.getId().equals(id));
	}

	@Test
	public void testLehrerLasoLehrerPojo() {
		LehrerPojo lehrerPojo = new LehrerPojo();
		lehrerPojo.setId(id);
		TestLehrerLaso lehrerLasoTest = new TestLehrerLaso(lehrerPojo);
		assertTrue(lehrerLasoTest.getPojo().getId().equals(id));
		assertFalse(triedToWrite);
	}

	@Test
	public void testLehrerLaso() {
		TestLehrerLaso lehrerLasoTest = new TestLehrerLaso();
		assertTrue(lehrerLasoTest.getPojo() != null);
		assertFalse(triedToWrite);
	}

	@Test
	public void testGetPojo() {
		lehrerLaso.setId(id);
		assertTrue(lehrerLaso.getPojo().getId().equals(id));
	}

	@Test
	public void testUsername() {
		assertTrue(lehrerLaso.getUsername().equals(""));
		assertFalse(triedToWrite);
		lehrerLaso.setUsername(username);
		assertTrue(triedToWrite);
		assertTrue(lehrerLaso.getUsername().equals(username));
	}

	@Test
	public void testPassword() {
		assertTrue(lehrerLaso.getPassword().equals(""));
		assertFalse(triedToWrite);
		lehrerLaso.setPassword(password);
		assertTrue(triedToWrite);
		assertTrue(lehrerLaso.getPassword().equals(password));
	}

	@Test
	public void testFirstname() {
		assertTrue(lehrerLaso.getFirstname().equals(""));
		assertFalse(triedToWrite);
		lehrerLaso.setFirstname(firstname);
		assertTrue(triedToWrite);
		assertTrue(lehrerLaso.getFirstname().equals(firstname));
	}

	@Test
	public void testLastname() {
		assertTrue(lehrerLaso.getLastname().equals(""));
		assertFalse(triedToWrite);
		lehrerLaso.setLastname(lastname);
		assertTrue(triedToWrite);
		assertTrue(lehrerLaso.getLastname().equals(lastname));
	}
	
	@Test
	public void testMail() {
		assertTrue(lehrerLaso.getMail().equals(""));
		assertFalse(triedToWrite);
		lehrerLaso.setMail(mail);
		assertTrue(triedToWrite);
		assertTrue(lehrerLaso.getMail().equals(mail));
	}
	
	@Test
	public void testUser() {
		assertTrue(lehrerLaso.getUser().getId() == null);
		assertFalse(triedToWrite);
		lehrerLaso.setUser(user);
		assertTrue(triedToWrite);
		assertTrue(lehrerLaso.getUser().equals(user));
	}
	
	@Test
	public void testFormPwOne() {
		assertTrue(lehrerLaso.getFormPwOne() == "");
		assertFalse(triedToWrite);
		lehrerLaso.setFormPwOne(formPwOne);
		assertFalse(triedToWrite);
		assertTrue(lehrerLaso.getFormPwOne().equals(formPwOne));
	}
	
	@Test
	public void testFormPwTwo() {
		assertTrue(lehrerLaso.getFormPwTwo() == "");
		assertFalse(triedToWrite);
		lehrerLaso.setFormPwTwo(formPwTwo);
		assertFalse(triedToWrite);
		assertTrue(lehrerLaso.getFormPwTwo().equals(formPwTwo));
	}
	
	@Test
	public void testCompareFormPws() {
		assertTrue(lehrerLaso.getFormPwOne().equals(lehrerLaso.getFormPwTwo()));
	}
}

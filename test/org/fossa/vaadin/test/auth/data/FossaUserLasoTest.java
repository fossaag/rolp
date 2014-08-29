package org.fossa.vaadin.test.auth.data;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.fossa.vaadin.auth.data.FossaUserLaso;
import org.fossa.vaadin.auth.data.FossaUserPojo;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class FossaUserLasoTest {
	private long id;
	public boolean triedToWrite = false;
	private String username;
	private String password;
	private String formPwOne;
	private String formPwTwo;
	private String firstname;
	private String lastname;
	private TestFossaUserLaso userLaso;
	
	class TestFossaUserLaso extends FossaUserLaso {		
		
		public TestFossaUserLaso() {
			super();
		}		
		public TestFossaUserLaso(FossaUserPojo user) {
			super(user);
		}
		@Override
		protected void writeToDatabase() {
			triedToWrite  = true;
		}
	}

	@Before
	public void setUp() throws Exception {
		userLaso = new TestFossaUserLaso();
		id = 65L;
		username = "ncibo";
		password = "chibo";
		firstname = "Nick";
		lastname = "Ciborra";
		formPwOne = "test";
		formPwTwo = "test";
	}

	@After
	public void tearDown() throws Exception {
	}
	
	@Test
	public void testId() {
		assertTrue(userLaso.getId() == null);
		assertFalse(triedToWrite);
		userLaso.setId(id);
		assertTrue(triedToWrite);
		assertTrue(userLaso.getId().equals(id));
	}

	@Test
	public void testFossaUserLasoFossaUserPojo() {
		FossaUserPojo userPojo = new FossaUserPojo();
		userPojo.setId(id);
		TestFossaUserLaso userLasoTest = new TestFossaUserLaso(userPojo);
		assertTrue(userLasoTest.getPojo().getId().equals(id));
		assertFalse(triedToWrite);
	}

	@Test
	public void testFossaUserLaso() {
		TestFossaUserLaso userLasoTest = new TestFossaUserLaso();
		assertTrue(userLasoTest.getPojo() != null);
		assertFalse(triedToWrite);
	}

	@Test
	public void testGetPojo() {
		userLaso.setId(id);
		assertTrue(userLaso.getPojo().getId().equals(id));
	}

	@Test
	public void testUsername() {
		assertTrue(userLaso.getUsername().equals(""));
		assertFalse(triedToWrite);
		userLaso.setUsername(username);
		assertTrue(triedToWrite);
		assertTrue(userLaso.getUsername().equals(username));
	}

	@Test
	public void testPassword() {
		assertTrue(userLaso.getPassword().equals(""));
		assertFalse(triedToWrite);
		userLaso.setPassword(password);
		assertTrue(triedToWrite);
		assertTrue(userLaso.getPassword().equals(password));
	}

	@Test
	public void testFirstname() {
		assertTrue(userLaso.getFirstname().equals(""));
		assertFalse(triedToWrite);
		userLaso.setFirstname(firstname);
		assertTrue(triedToWrite);
		assertTrue(userLaso.getFirstname().equals(firstname));
	}

	@Test
	public void testLastname() {
		assertTrue(userLaso.getLastname().equals(""));
		assertFalse(triedToWrite);
		userLaso.setLastname(lastname);
		assertTrue(triedToWrite);
		assertTrue(userLaso.getLastname().equals(lastname));
	}
	
	@Test
	public void testFormPwOne() {
		assertTrue(userLaso.getFormPwOne().equals(""));
		assertFalse(triedToWrite);
		userLaso.setFormPwOne(formPwOne);
		assertFalse(triedToWrite);
		assertTrue(userLaso.getFormPwOne().equals(formPwOne));
	}
	
	@Test
	public void testFormPwTwo() {
		assertTrue(userLaso.getFormPwTwo().equals(""));
		assertFalse(triedToWrite);
		userLaso.setFormPwTwo(formPwTwo);
		assertFalse(triedToWrite);
		assertTrue(userLaso.getFormPwTwo().equals(formPwTwo));
	}
	
	@Test
	public void testCompareFormPws() {
		assertTrue(userLaso.getFormPwOne().equals(userLaso.getFormPwTwo()));
	}
}

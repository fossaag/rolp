package org.fossa.vaadin.test.laso;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import org.fossa.vaadin.test.testutils.TestLaso;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class FossaLasoTest {
	TestLaso testLaso;

	@Before
	public void setUp() throws Exception {
		testLaso = new TestLaso();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testTestLasoLockingNew() {
		assertFalse("must not be locked", testLaso.isLocked());
	}

	@Test
	public void testTestLasoLockingLocked() {
		testLaso.lock();
		assertTrue("must be locked", testLaso.isLocked());
	}

	@Test
	public void testTestLasoLockingUnlocked() {
		testLaso.lock();
		testLaso.unlock();
		assertFalse("must not be locked", testLaso.isLocked());
	}
	
	@Test
	public void testLasoGetAll() {
		assertTrue(TestLaso.getAll().equals(Arrays.asList(TestLaso.TEST_STRING)));		
	}
	
	@Test
	public void testLasoGet() {	
		assertTrue(testLaso.getTestString().equals(TestLaso.TEST_STRING));
		assertTrue(testLaso.getId().equals(TestLaso.TEST_ID));
	}
	
	@Test
	public void testLasoSet() {
		String aString = "aString";
		testLaso.setTestString(aString);
		assertTrue(aString.equals(testLaso.testString));
		assertTrue(testLaso.getTestString().equals(testLaso.testString));
		Long anId = 17L;
		testLaso.setId(anId);
		assertTrue(anId.equals(testLaso.testId));
		assertTrue(testLaso.getId().equals(testLaso.testId));
	}
	
	@Test
	public void testLasoWriteToDatabase() {
		assertFalse(testLaso.triedToWrite);
		testLaso.setTestString("");
		assertTrue(testLaso.triedToWrite);
	}

	@Test
	public void testLasoGetPojo() {
		assertTrue(testLaso.getPojo().equals(testLaso.testObject));
	}

}

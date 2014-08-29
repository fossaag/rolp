package org.fossa.rolp.test.util;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import org.fossa.rolp.util.DateUtil;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class DateUtilTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testCutTimeOffDate() {
		Date date = DateUtil.cutTimeOffDate(new Date());
		Calendar gc = GregorianCalendar.getInstance();
		gc.setTime(date);
		assertTrue(gc.get(Calendar.MINUTE) == 0);
		assertTrue(gc.get(Calendar.HOUR_OF_DAY) == 0);
		assertTrue(gc.get(Calendar.SECOND) == 0);
		assertTrue(gc.get(Calendar.MILLISECOND) == 0);		
	}
	
	@Test
	public void testCutTimeOffDateNULL() {
		Date date = DateUtil.cutTimeOffDate(null);
		assertTrue(date == null);
	}
	
	@Test
	public void testDateIsInvalidHigh() {
		Calendar gc = GregorianCalendar.getInstance();
		gc.setTime(new Date());
		gc.set(Calendar.YEAR, 3000);
		assertTrue(DateUtil.dateIsInvalid(gc.getTime()));
	}
	
	@Test
	public void testDateIsInvalidLow() {
		Calendar gc = GregorianCalendar.getInstance();
		gc.setTime(new Date());
		gc.set(Calendar.YEAR, 1900);
		assertTrue(DateUtil.dateIsInvalid(gc.getTime()));
	}
	
	@Test
	public void testDateIsInvalidFalse() {
		assertFalse(DateUtil.dateIsInvalid(new Date()));
	}
	
	@Test
	public void testDateIsInvalidNULL() {
		assertFalse(DateUtil.dateIsInvalid(null));
	}
	
}

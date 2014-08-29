package org.fossa.rolp.test.util;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.fossa.rolp.util.PasswortEncryptUtil;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class PasswortEncryptUtilTest {

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testEncryptPasswordTrue() {
		assertTrue("must be identical", PasswortEncryptUtil.encryptPassword("test").compareTo("098f6bcd4621d373cade4e832627b4f6")==0);
	}

	@Test
	public void testEncryptPasswordFalse() {
		assertFalse("must not be identical", PasswortEncryptUtil.encryptPassword("test1").compareTo("098f6bcd4621d373cade4e832627b4f6")==0);
	}

}

package org.fossa.rolp.test;

import org.fossa.rolp.test.data.AllDataTests;
import org.fossa.rolp.test.util.AllUtilTests;
import org.fossa.vaadin.test.laso.AllLasoTests;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({AllUtilTests.class, AllDataTests.class, AllLasoTests.class})
public class AllTests {

}

package org.jboss.tools.teiid.ui.bot.test.suite;

import org.jboss.reddeer.junit.runner.RedDeerSuite;
import org.jboss.tools.teiid.ui.bot.test.E2eAudioBooksVdbExecutionTest;
import org.jboss.tools.teiid.ui.bot.test.E2eRecursiveXmlTextTest;
import org.jboss.tools.teiid.ui.bot.test.RestCallTest;
import org.jboss.tools.teiid.ui.bot.test.ServerManagementTest;
import org.jboss.tools.teiid.ui.bot.test.TopDownWsdlTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite.SuiteClasses;

/**
 * E2e tests
 * Run at least locally
 * 
 * @author lfabriko
 * 
 */
@SuiteClasses({//TODO update tests
	E2eRecursiveXmlTextTest.class,
	TopDownWsdlTest.class,
	E2eAudioBooksVdbExecutionTest.class,
	RestCallTest.class,
	ServerManagementTest.class
})
@RunWith(RedDeerSuite.class)
public class E2eTests {

}

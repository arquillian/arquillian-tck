package org.arquillian.tck.container.cdi_1_0;

import static org.arquillian.tck.container.Deployments.cdiArchive;
import static org.junit.Assert.assertEquals;

import javax.inject.Inject;

import org.arquillian.tck.group.CDI;
import org.arquillian.tck.group.EE6;
import org.arquillian.tck.group.War;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;

/**
 * In order to test CDI beans
 * As a Tester
 * I want to be able to access CDI beans from the TestClass via injection 
  */
@Category({EE6.class, CDI.class, War.class})
@RunWith(Arquillian.class)
public class CDIBeanLookupWarTestCase {

	/**
	 * Given a single CDI Bean in a single WebArchive
	 * ; And the Test run in Container 
	 */
	@Deployment
	public static WebArchive deploy() {
		return cdiArchive(EchoBean.class);
	}
	
	/**
	 * ; And an Injected CDI Bean 
	 */
	@Inject 
	private EchoBean bean;
	
	
	/**
	 * Then the CDI Bean should be accessible
	 * ; And be injectable as an Instance Variable  
	 */
	@Test
	public void shouldInjectToInstance() throws Exception {
		assertEquals("123", this.bean.echo("123"));
	}

	/**
	 * Then the CDI Bean should be accessible
	 * ; And be injectable as an Method Argument
	 */
	@Test
	public void shouldInjectToMethodArgument(EchoBean bean) throws Exception {
		assertEquals("123", bean.echo("123"));
	}
}
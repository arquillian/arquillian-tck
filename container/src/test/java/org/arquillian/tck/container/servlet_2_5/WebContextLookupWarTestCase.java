package org.arquillian.tck.container.servlet_2_5;

import static org.arquillian.tck.container.Asserts.assertResponseEquals;
import static org.arquillian.tck.container.Deployments.servlet25Archive;

import java.net.URL;

import org.arquillian.tck.group.EE5;
import org.arquillian.tck.group.Servlet;
import org.arquillian.tck.group.War;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;

/**
 * In order to avoid duplicate configuration
 * As a Tester
 * I want to be able to reuse the information the container has available 
  */
@Category({EE5.class, Servlet.class, War.class})
@RunWith(Arquillian.class)
public class WebContextLookupWarTestCase {

	/**
	 * Given a single Servlet in a single WebArchive
	 * ; And the Servlet is configured via web.xml
	 * ; And the Test run on the Client side 
	 */
	@Deployment(testable = false)
	public static WebArchive deploy() {
		return servlet25Archive(EchoServlet.class, "/echo");
	}
	
	/**
	 * ; And a non targeted URL Resource Lookup
	 */
	@ArquillianResource
	private URL nonTargetedBase;
	
	/**
	 * ; And a targeted URL Resource lookup for the Servlet in the WebArchive
	 */
	@ArquillianResource(EchoServlet.class)
	private URL targetedBase;
	
	/**
	 * Then the Non Targeted URL should reference the root of the only Web Context
	 * ; And be injectable as an Instance Variable 
	 */
	@Test
	public void shouldInjectNonTargetedServletToInstance() throws Exception {
		assertResponseEquals("123", new URL(this.nonTargetedBase, "echo?text=123"));
	}

	/**
	 * Then the Non Targeted URL should reference the root of the only Web Context
	 * ; And be injectable as a Method Argument Variable
	 */
	@Test
	public void shouldInjectNonTargetedServletToMethodArgument(@ArquillianResource URL nonTargetedBase) throws Exception {
		assertResponseEquals("123", new URL(nonTargetedBase, "echo?text=123"));
	}
	
	/**
	 * Then the Targeted URL should reference the root of the Web Context where the Target is located
	 * ; And be injectable as an Instance Variable
	 */
	@Test
	public void shouldInjectTargetedServletToInstance() throws Exception {
		assertResponseEquals("123", new URL(this.targetedBase, "echo?text=123"));
	}

	/**
	 * Then the Targeted URL should reference the root of the Web Context where the Target is located
	 * ; And be injectable as a Method Argument Variable
	 */
	@Test
	public void shouldInjectTargetedServletToMethodArgument(@ArquillianResource(EchoServlet.class) URL targetedBase) throws Exception {
		assertResponseEquals("123", new URL(targetedBase, "echo?text=123"));
	}
}
package org.arquillian.tck.container.servlet_3_0;

import static org.arquillian.tck.container.Asserts.assertResponseEquals;
import static org.arquillian.tck.container.Deployments.servlet30Archive;
import static org.junit.Assert.assertEquals;

import java.net.URL;

import org.arquillian.tck.group.EE6;
import org.arquillian.tck.group.Ear;
import org.arquillian.tck.group.Servlet;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.EnterpriseArchive;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;

/**
 * In order to avoid duplicate configuration
 * As a Tester
 * I want to be able to reuse the information the container has available 
  */
@Category({EE6.class, Servlet.class, Ear.class})
@RunWith(Arquillian.class)
public class MultipleWebContextLookupEarTestCase {

	/**
	 * Given two different Servlets in two different WebArchives inside an EnterpriseArchive
	 * ; And the Servlet is configured via Annotations
	 * ; And the Test run on the Client side 
	 */
	@Deployment(testable = false)
	public static EnterpriseArchive deploy() {
		return ShrinkWrap.create(EnterpriseArchive.class)
				.addAsModule(servlet30Archive(EchoServlet.class))
				.addAsModule(servlet30Archive(ReverseEchoServlet.class));
	}
	
	/**
	 * ; And a non targeted URL Resource Lookup
	 */
	@ArquillianResource
	private URL nonTargetedBase;
	
	/**
	 * ; And a targeted URL Resource lookup for the Servlet in the first WebArchive
	 */
	@ArquillianResource(EchoServlet.class)
	private URL targetedEchoBase;
	
	/**
	 * ; And a targeted URL Resource lookup for the Servlet in the second WebArchive 
	 */
	@ArquillianResource(ReverseEchoServlet.class)
	private URL targetedReverseEchoBase;

	/**
	 * Then the Non Targeted URL should reference the root of the Servers Web Context
	 * ; And be injectable as an Instance Variable  
	 */
	@Test
	public void shouldInjectNonTargetedServletToInstance() throws Exception {
		assertEquals("", this.nonTargetedBase.getPath());
	}

	/**
	 * Then the Non Targeted URL should reference the root of the Servers Web Context
	 * ; And be injectable as a Method Argument Variable
	 */
	@Test
	public void shouldInjectNonTargetedServletToMethodArgument(@ArquillianResource URL nonTargetedBase) throws Exception {
		assertEquals("", nonTargetedBase.getPath());
	}
	
	/**
	 * Then the Targeted URL should reference the root of the Web Context where the Target is located
	 * ; And be injectable as an Instance Variable 
	 */
	@Test
	public void shouldInjectTargetedEchoServletToInstance() throws Exception {
		assertResponseEquals("123", new URL(this.targetedEchoBase, "echo?text=123"));
	}

	/**
	 * Then the Targeted URL should reference the root of the Web Context where the Target is located
	 * ; And be injectable as a Method Argument Variable
	 */
	@Test
	public void shouldInjectTargetedEchoServletToMethodArgument(@ArquillianResource(EchoServlet.class) URL targetedEchoBase) throws Exception {
		assertResponseEquals("123", new URL(targetedEchoBase, "echo?text=123"));
	}

	/**
	 * Then the Targeted URL should reference the root of the Web Context where the Target is located
	 * ; And be injectable as an Instance Variable 
	 */
	@Test
	public void shouldInjectTargetedReverseEchoServletToInstance() throws Exception {
		assertResponseEquals("321", new URL(this.targetedReverseEchoBase, "echo?text=123"));
	}

	/**
	 * Then the Targeted URL should reference the root of the Web Context where the Target is located
	 * ; And be injectable as a Method Argument Variable
	 */
	@Test
	public void shouldInjectTargetedReverseEchoServletToMethodArgument(@ArquillianResource(ReverseEchoServlet.class) URL targetedEchoBase) throws Exception {
		assertResponseEquals("321", new URL(targetedReverseEchoBase, "echo?text=123"));
	}
}
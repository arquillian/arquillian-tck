package org.arquillian.tck.container.servlet_3_0;

import static org.arquillian.tck.container.Asserts.assertResponseEquals;
import static org.arquillian.tck.container.Deployments.servlet30Archive;

import java.net.URL;

import org.arquillian.tck.group.EE6;
import org.arquillian.tck.group.MultiDeployment;
import org.arquillian.tck.group.Servlet;
import org.arquillian.tck.group.War;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.OperateOnDeployment;
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
@Category({EE6.class, Servlet.class, War.class, MultiDeployment.class})
@RunWith(Arquillian.class)
public class MultipleWebContextLookupMultiWarTestCase {

	/**
	 * Given two different Servlets in two separate deployed WebArchives
	 * ; And the Servlet is configured via Annotations
	 * ; And the Test run on the Client side
	 * ; And one WebArchive is default and the one is named
	 */
	@Deployment(testable = false)
	public static WebArchive deployOne() {
		return servlet30Archive(EchoServlet.class);
	}

	@Deployment(name = "dep_2", testable = false)
	public static WebArchive deployTwo() {
		return servlet30Archive(ReverseEchoServlet.class);
	}

	/**
	 * ; And a non targeted URL Resource Lookup
	 */
	@ArquillianResource
	private URL nonTargetedBase;

	/**
	 * ; And a non targeted URL Resource lookup Operating in the context of the second WebArchive
	 */
	@ArquillianResource @OperateOnDeployment("dep_2")
	private URL nonTargetedOperatesOnSecondBase;

	/**
	 * ; And a targeted URL Resource lookup for the Servlet in the second WebArchive 
	 */
	@ArquillianResource(ReverseEchoServlet.class) @OperateOnDeployment("dep_2")
	private URL targetedReverseEchoBase;

	/**
	 * Then the Non Targeted URL should reference the root of the first WebArchive Context
	 * ; When running in the default Context
	 * ; And be injectable as an Instance Variable 
	 */
	@Test
	public void shouldInjectNonTargetedServletToInstance() throws Exception {
		assertResponseEquals("123", new URL(this.nonTargetedBase, "echo?text=123"));
	}

	/**
	 * Then the Non Targeted URL should reference the root of the second WebArchive 
	 * ; When running in the named Context
	 * ; And be injectable as an Instance Variable 
	 */
	@Test @OperateOnDeployment("dep_2")
	public void shouldInjectNonTargetedServletToInstanceFromSameContext() throws Exception {
		assertResponseEquals("321", new URL(this.nonTargetedBase, "echo?text=123"));
	}

	/**
	 * Then the Non Targeted URL should reference the root of the first WebArchive 
	 * ; When running in the default Context
	 * ; And be injectable as a Method Argument Variable
	 */
	@Test
	public void shouldInjectNonTargetedServletToMethodArgument(@ArquillianResource URL nonTargetedBase) throws Exception {
		assertResponseEquals("123", new URL(nonTargetedBase, "echo?text=123"));
	}
	
	/**
	 * Then the Non Targeted URL that Operates in the context of the second WebArchive should reference the root of the second WebArchive
	 * ; When running in the default Context
	 * ; And be injectable as an Instance Variable 
	 */
	@Test
	public void shouldInjectNonTargetedServletToInstanceFromNamedContext() throws Exception {
		assertResponseEquals("321", new URL(this.nonTargetedOperatesOnSecondBase, "echo?text=123"));
	}

	/**
	 * Then the Non Targeted URL that Operates in the context of the second WebArchive should reference the root of the second WebArchive
	 * ; When running in the default Context
	 * ; And be injectable as a Method Argument Variable
	 */
	@Test
	public void shouldInjectNonTargetedServletToMethodArgumentFromNamedContext(@ArquillianResource @OperateOnDeployment("dep_2") URL nonTargetedOperatesOnSecondBase) throws Exception {
		assertResponseEquals("321", new URL(nonTargetedOperatesOnSecondBase, "echo?text=123"));
	}

	/**
	 * Then the Targeted URL that Operates in the context of the second WebArchive should reference the Web Context where the Target is located 
	 * ; When running in the default Context
	 * ; And be injectable as an Instance Variable 
	 */
	@Test
	public void shouldInjectTargetedReverseServletToInstanceFromNamedContext() throws Exception {
		assertResponseEquals("321", new URL(this.targetedReverseEchoBase, "echo?text=123"));
	}

	/**
	 * Then the Targeted URL that Operates in the context of the second WebArchive should reference the Web Context where the Target is located 
	 * ; When running in the default Context
	 * ; And be injectable as a Method Argument Variable 
	 */
	@Test
	public void shouldInjectTargetedReverseServletToMethodArgumentFromNamedContext(@ArquillianResource(ReverseEchoServlet.class) @OperateOnDeployment("dep_2") URL targetedReverseEchoBase) throws Exception {
		assertResponseEquals("321", new URL(targetedReverseEchoBase, "echo?text=123"));
	}
	
	/**
	 * Then the Targeted URL that Operates in the context of the second WebArchive should reference the Web Context where the Target is located 
	 * ; When running in the named Context
	 * ; And be injectable as a Method Argument Variable 
	 */
	@Test @OperateOnDeployment("dep_2")
	public void shouldInjectTargetedReverseServletToMethodArgumentFromSameContext(@ArquillianResource(ReverseEchoServlet.class) URL targetedReverseEchoBase) throws Exception {
		assertResponseEquals("321", new URL(targetedReverseEchoBase, "echo?text=123"));
	}
}
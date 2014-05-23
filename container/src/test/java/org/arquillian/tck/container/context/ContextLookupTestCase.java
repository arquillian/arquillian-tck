package org.arquillian.tck.container.context;

import static org.arquillian.tck.container.Deployments.webarchive;
import static org.junit.Assert.assertNotNull;

import javax.naming.Context;
import javax.naming.InitialContext;

import org.arquillian.tck.group.EE5;
import org.arquillian.tck.group.EE6;
import org.arquillian.tck.group.Naming;
import org.arquillian.tck.group.War;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
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
@Category({EE5.class, EE6.class, Naming.class, War.class})
@RunWith(Arquillian.class)
public class ContextLookupTestCase {

	/**
	 * Given any Deployment
	 */
	@Deployment
	public static WebArchive deploy() {
		return webarchive(); 
	}
	
	/**
	 * ; And a Context Resource Lookup
	 */
	@ArquillianResource
	private Context context;

	/**
	 * ; And a InitialContext Resource Lookup
	 */
	@ArquillianResource
	private InitialContext initialContext;
	
	/**
	 * Then the Context should reference the Container
	 * ; When running in Container
	 * ; And be injectable as an Instance Variable 
	 */
	@Test
	public void shouldInjectContextToInstanceContainer() throws Exception {
		assertNotNull(this.context);
	}

	/**
	 * Then the Context should reference the Container
	 * ; When running in Container
	 * ; And be injectable a Method Argument Variable 
	 */
	@Test
	public void shouldInjectContextToMethodArgumentContainer(@ArquillianResource Context context) throws Exception {
		assertNotNull(context);
	}

	/**
	 * Then the Context should reference the Container
	 * ; When running in Container
	 * ; And be injectable as an Instance Variable 
	 */
	@Test
	public void shouldInjectInitialContextToInstanceContainer() throws Exception {
		assertNotNull(this.initialContext);
	}

	/**
	 * Then the InitialContext should reference the Container
	 * ; When running in Container
	 * ; And be injectable a Method Argument Variable 
	 */
	@Test
	public void shouldInjectInitialContextToMethodArgumentContainer(@ArquillianResource InitialContext initialContext) throws Exception {
		assertNotNull(initialContext);
	}

	/**
	 * Then the Context should reference the Container
	 * ; When running on the Client side
	 * ; And be injectable as an Instance Variable 
	 */
	@Test @RunAsClient
	public void shouldInjectContextToInstanceClient() throws Exception {
		assertNotNull(this.context);
	}

	/**
	 * Then the Context should reference the Container
	 * ; When running on the Client side
	 * ; And be injectable a Method Argument Variable 
	 */
	@Test @RunAsClient
	public void shouldInjectContextToMethodArgumentClient(@ArquillianResource Context context) throws Exception {
		assertNotNull(context);
	}

	/**
	 * Then the Context should reference the Container
	 * ; When running on the Client side
	 * ; And be injectable as an Instance Variable 
	 */
	@Test @RunAsClient
	public void shouldInjectInitialContextToInstanceClient() throws Exception {
		assertNotNull(this.initialContext);
	}

	/**
	 * Then the InitialContext should reference the Container
	 * ; When running on the Client side
	 * ; And be injectable a Method Argument Variable 
	 */
	@Test @RunAsClient
	public void shouldInjectInitialContextToMethodArgumentClient(@ArquillianResource InitialContext initialContext) throws Exception {
		assertNotNull(initialContext);
	}
}

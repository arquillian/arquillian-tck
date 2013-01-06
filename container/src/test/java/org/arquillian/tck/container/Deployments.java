package org.arquillian.tck.container;

import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.asset.StringAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;

public final class Deployments {

	private Deployments() {}
	
	public static WebArchive cdiArchive(Class<?>... beans) {
		return webarchive()
				.addClasses(beans)
				.addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
	}

	public static WebArchive servlet30Archive(Class<?> servlet) {
		return webarchive(servlet);
	}
	
	public static WebArchive servlet25Archive(Class<?> servlet, String mapping) {
		return webarchive(servlet)
				.setWebXML(new StringAsset( // TODO: Missing 2.5 WebAppDescriptor
						"<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" + 
						"<web-app version=\"2.5\" xmlns=\"http://java.sun.com/xml/ns/javaee\"\n" + 
						"xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"\n" + 
						"xsi:schemaLocation=\"http://java.sun.com/xml/ns/javaee http://java.sun.com/xml/ns/javaee/web-app_2_5.xsd\">\n" + 
						" \n" + 
						" <display-name>Arquillian Container TCK</display-name>" +
						" <description>Arquillian Container TCK</description>" +
						" <servlet>\n" + 
						"   <servlet-name>" + servlet.getSimpleName() + "</servlet-name>\n" + 
						"   <servlet-class>" + servlet.getName() + "</servlet-class>\n" + 
						" </servlet>\n" + 
						" \n" + 
						" <servlet-mapping>\n" + 
						"   <servlet-name>" + servlet.getSimpleName() + "</servlet-name>\n" + 
						"   <url-pattern>" + mapping + "</url-pattern>\n" + 
						" </servlet-mapping>" +
						"</web-app>"));
		
	}

	public static WebArchive webarchive(Class<?> clazz) {
		return webarchive()
				.addClass(clazz);
	}
	
	public static WebArchive webarchive() {
		return ShrinkWrap.create(WebArchive.class);
	}
}

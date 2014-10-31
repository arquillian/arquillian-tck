package org.arquillian.tck.group;

import org.jboss.arquillian.container.test.spi.client.deployment.AuxiliaryArchiveAppender;
import org.jboss.arquillian.core.spi.LoadableExtension;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;

/**
 * Simple Arquillian Extension to auto package the JUnit Category classes.
 *
 */
public class AppendGroupsToArchiveExtension implements LoadableExtension {

	@Override
	public void register(ExtensionBuilder builder) {
		builder.service(AuxiliaryArchiveAppender.class, GroupAppender.class);
	}

	public static class GroupAppender implements AuxiliaryArchiveAppender {

		@Override
		public Archive<?> createAuxiliaryArchive() {
			return ShrinkWrap.create(JavaArchive.class, "arquillian-tck-groups.jar")
					.addPackage(EE4.class.getPackage());
		}
	}
}

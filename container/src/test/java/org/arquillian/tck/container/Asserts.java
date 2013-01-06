package org.arquillian.tck.container;

import java.io.IOException;
import java.net.URL;

import org.junit.Assert;

public final class Asserts {

	private Asserts() {}
	
	public static void assertResponseEquals(String expected, URL target) {
		try {
			Assert.assertEquals(expected, IOUtil.asUTF8String(target.openStream()));
		} catch (IOException e) {
			throw new RuntimeException("Failed to assertEquals [" + expected + "]", e);
		}
	}
}

package org.arquillian.tck.container.cdi_1_0;

import javax.enterprise.context.RequestScoped;

@RequestScoped
public class EchoBean {

	public String echo(String text) {
		return text;
	}
}

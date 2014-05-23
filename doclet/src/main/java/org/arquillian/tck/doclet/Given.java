package org.arquillian.tck.doclet;

public class Given {

	private String text;
	private String source;
	
	public Given(String text, String source) {
		this.text = text;
		this.source = source;
	}

	public String getText() {
		return text;
	}
	
	public String getSource() {
		return source;
	}
}

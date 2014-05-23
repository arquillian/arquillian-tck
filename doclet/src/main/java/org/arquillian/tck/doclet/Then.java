package org.arquillian.tck.doclet;

public class Then {
	
	private String methodName;
	private String methodSignature;
	private String text;
	private String source;
	
	public Then(String methodName, String methodSignature, String text, String source) {
		this.methodName = methodName;
		this.methodSignature = methodSignature;
		this.text = text;
		this.source = source;
	}
	
	public String getText() {
		return text;
	}
	
	public String getMethodSignature() {
		return methodSignature;
	}
	
	public String getMethodName() {
		return methodName;
	}
	
	public String getSource() {
		return source;
	}
}

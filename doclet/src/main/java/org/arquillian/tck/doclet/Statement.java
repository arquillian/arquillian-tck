package org.arquillian.tck.doclet;

import java.util.ArrayList;
import java.util.List;

public class Statement {
	
	private String text;
	private String className;
	private List<String> categories;
	
	private List<Given> givens;
	private List<Then> thens;
	
	public Statement() {
		this.givens = new ArrayList<Given>();
		this.thens = new ArrayList<Then>();
		this.categories = new ArrayList<String>();
	}
	
	public String getText() {
		return text;
	}
	
	public String getClassName() {
		return className;
	}
	
	public List<String> getCategories() {
		return categories;
	}
	
	public List<Given> getGivens() {
		return givens;
	}
	
	public List<Then> getThens() {
		return thens;
	}

	public void addGiven(Given given) {
		this.givens.add(given);
	}
	
	public void addThen(Then then) {
		this.thens.add(then);
	}
	
	public void addCategory(String group) {
		this.categories.add(group);
	}

	public void setText(String text) {
		this.text = text;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public boolean hasCategories() {
		return categories.size() > 0;
	}
}

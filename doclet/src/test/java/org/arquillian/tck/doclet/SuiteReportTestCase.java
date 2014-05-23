package org.arquillian.tck.doclet;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.google.gson.GsonBuilder;

public class SuiteReportTestCase {

	@Test
	public void should() throws Exception {
		
		ReportSummaryGenerator generator = new ReportSummaryGenerator(
				new File("../container/target/surefire-reports/"));

		System.out.println(generator.generate());
	}
	
	public class ReportSummaryGenerator {
		private File dir;
		
		public ReportSummaryGenerator(File dir) {
			this.dir = dir;
		}
		
		public String generate() throws Exception {
			
			Container contianer = new Container("JBoss AS Remote 6");
			
			File[] files = dir.listFiles(new FilenameFilter() {
				@Override
				public boolean accept(File dir, String name) {
					return name.matches("TEST.*");
				}
			});
			
			for(File file : files) {
				
				DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
				Document doc = db.parse(file);

				XPathFactory xf = XPathFactory.newInstance();
				XPath xpath = xf.newXPath();
				
				NodeList suites = (NodeList)xpath.evaluate("//testsuite", doc, XPathConstants.NODESET);
				for(int i = 0; i < suites.getLength(); i++) {
					Node suite = suites.item(i);
					Statement statement = new Statement(suite.getAttributes().getNamedItem("name").getTextContent());
					contianer.addStatement(statement);
					
					NodeList testcases = (NodeList)xpath.evaluate(".//testcase", doc, XPathConstants.NODESET);
					for(int n = 0; n < testcases.getLength(); n++) {
						Node testcase = testcases.item(n);
						Verification verification = new Verification(
								testcase.getAttributes().getNamedItem("name").getTextContent()
								, "success");
						statement.addVerification(verification);
					}
				}
			}
			return new GsonBuilder().setPrettyPrinting().create().toJson(contianer);
		}
	}
	
	public class Container {
		private String name;

		private List<Statement> statements;
		
		public Container(String name) {
			this.statements = new ArrayList<Statement>();
			this.name = name;
		}
	
		public String getName() {
			return name;
		}
		
		public void addStatement(Statement testMethod) {
			this.statements.add(testMethod);
		}
	}
	
	public class Statement {
		private String name;
		private List<Verification> verifications;
		
		public Statement(String name) {
			this.verifications = new ArrayList<Verification>();
			this.name = name;
		}
		
		public String getName() {
			return name;
		}
		
		public void addVerification(Verification verification) {
			this.verifications.add(verification);
		}
	}
	
	public class Verification {
		private String name;
		private String status;

		public Verification(String name, String status) {
			super();
			this.name = name;
			this.status = status;
		}
		
		public String getName() {
			return name;
		}
		
		public String getStatus() {
			return status;
		}
	}
}

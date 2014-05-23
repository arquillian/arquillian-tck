package org.arquillian.tck.doclet;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.jdt.core.formatter.CodeFormatter;
import org.eclipse.jdt.internal.formatter.DefaultCodeFormatter;
import org.eclipse.jdt.internal.formatter.DefaultCodeFormatterOptions;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.Document;
import org.eclipse.jface.text.IDocument;
import org.eclipse.text.edits.TextEdit;

import com.google.gson.GsonBuilder;
import com.sun.javadoc.AnnotationDesc;
import com.sun.javadoc.AnnotationDesc.ElementValuePair;
import com.sun.javadoc.AnnotationValue;
import com.sun.javadoc.ClassDoc;
import com.sun.javadoc.FieldDoc;
import com.sun.javadoc.MethodDoc;
import com.sun.javadoc.RootDoc;
import com.sun.tools.javadoc.ProgramElementDocImpl;

public class TCKDoclet {

	public static boolean start(RootDoc root) throws Exception {
		System.out.println("Calledo");

		List<Statement> statements = new ArrayList<Statement>();
		// HACK: Need to access tree variable to access the source code
		Field tree = ProgramElementDocImpl.class.getDeclaredField("tree");
		if(!tree.isAccessible()) {
			tree.setAccessible(true);
		}
		
		for(ClassDoc classDoc : root.classes()) {
			Statement statement = new Statement();
			
			if(classDoc.annotations().length == 0) {
				continue;
			}
			for(AnnotationDesc annotationDesc : classDoc.annotations()) {
				if("Category".equals(annotationDesc.annotationType().name())) {
					System.out.println(annotationDesc.annotationType());
					for(ElementValuePair pair : annotationDesc.elementValues()) {
						if("value".equals(pair.element().name())) {
							if(pair.value().value() instanceof AnnotationValue[]) {
								AnnotationValue[] values = (AnnotationValue[])pair.value().value();
								for(AnnotationValue annotationValue : values) {
									statement.addCategory(((ClassDoc) annotationValue.value()).name());
								}
							}
						}
					}
				}
			}
			if(!statement.hasCategories()) {
				continue;
			}
			statement.setClassName(classDoc.qualifiedName());
			statement.setText(classDoc.commentText());
			
			for(MethodDoc methodDoc : classDoc.methods()) {
				String source = formatSource(clean(tree.get(methodDoc).toString()));
				
				if(methodDoc.isStatic()) {
					statement.addGiven(new Given(methodDoc.commentText(), source));
				} else {
					statement.addThen(new Then(methodDoc.name(), methodDoc.signature(), methodDoc.commentText(), source));
				}
			}
			for(FieldDoc fieldDoc : classDoc.fields()) {
				String source = clean(tree.get(fieldDoc).toString());
				statement.addGiven(new Given(fieldDoc.commentText(), source));
			}
			
			statements.add(statement);
		}
		
		String json = new GsonBuilder().setPrettyPrinting().create().toJson(statements);
		System.out.println(json);
		
		return true;
	}

	private static String formatSource(String source) throws BadLocationException {
		DefaultCodeFormatterOptions options = DefaultCodeFormatterOptions.getJavaConventionsSettings();
		options.getMap().put(options.tab_char, ' ');
		options.getMap().put(options.tab_size, '4');
		
		CodeFormatter cf = new DefaultCodeFormatter(options);
		TextEdit te = cf.format(CodeFormatter.K_UNKNOWN, source, 0,source.length(),0,null);
		IDocument dc = new Document(source);
		te.apply(dc);
		return dc.get();
	}
	
	private static String clean(String source) {
		if(source.charAt(0) == '\n') {
			return source.substring(1);
		}
		return source;
	}
}

package kirstein.source.java;

import java.io.File;

import kirstein.source.LanguageDescriptionCodeFactory;
import kirstein.uml.UMLDocument;
import kirstein.umlet.UMLetDocument;

public class Test {
	public static void main(String[] args) throws Throwable{
		File rootFolder = new File("test");
		LanguageDescriptionCodeFactory javaCodeFactory = new LanguageDescriptionCodeFactory("java", new JavaDescription());
		UMLetDocument umlet = new UMLetDocument(new File("C:\\Users\\Ernest\\Documents\\ActiveJavaProjects\\umlet\\test2.uxf"));
		UMLDocument uml = new UMLDocument(umlet);
		javaCodeFactory.produceCode(uml, rootFolder);
	}
}

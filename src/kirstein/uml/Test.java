package kirstein.uml;

import java.io.File;

import kirstein.umlet.UMLetDocument;

public class Test {
	public static void main(String[] args) throws Throwable{
		UMLetDocument uml = new UMLetDocument(new File("C:\\Users\\Ernest\\Documents\\ActiveJavaProjects\\umlet\\test2.uxf"));
		UMLDocument doc = new UMLDocument(uml);
		System.out.println(doc.toXML());
	}
}

package kirstein.source.php;

import java.io.File;

import kirstein.source.LanguageDescriptionCodeFactory;
import kirstein.source.NClassesPerFile;
import kirstein.uml.UMLDocument;
import kirstein.umlet.UMLetDocument;

public class Test {
	public static void main(String[] args) throws Throwable{
		System.out.println("Starting.");
		File rootFolder = new File("test");
		LanguageDescriptionCodeFactory phpCodeFactory = new LanguageDescriptionCodeFactory("php", new PHPDescription());
		phpCodeFactory.setSourceDistribution(new NClassesPerFile("php","File_",2));
		phpCodeFactory.setOrdering(new PHPAllignment());
		UMLetDocument umlet = new UMLetDocument(new File("C:\\Users\\Ernest\\Documents\\ActiveJavaProjects\\umlet\\test3.uxf"));
		UMLDocument uml = new UMLDocument(umlet);
		phpCodeFactory.produceCode(uml, rootFolder);
		System.out.println("Done.");
	}
}

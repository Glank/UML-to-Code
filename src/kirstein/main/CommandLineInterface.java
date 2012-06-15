package kirstein.main;

import java.io.File;

import kirstein.source.LanguageDescriptionCodeFactory;
import kirstein.source.NClassesPerFile;
import kirstein.source.java.JavaDescription;
import kirstein.source.php.PHPAllignment;
import kirstein.source.php.PHPDescription;
import kirstein.uml.UMLDocument;
import kirstein.umlet.UMLetDocument;

public class CommandLineInterface {
	public static void main(String[] args) throws Throwable{
		if(args.length != 3){
			printUsage();
			return;
		}
		else{
			String lang = args[0];
			String uxf_file = args[1];
			String out_dir = args[2];
			
			//initiate the code factory
			LanguageDescriptionCodeFactory codeFactory = null;
			if(lang.equals("JAVA")){
				codeFactory = new LanguageDescriptionCodeFactory("java", new JavaDescription());
			}
			else if(lang.equals("PHP")){
				codeFactory = new LanguageDescriptionCodeFactory("php", new PHPDescription());
				codeFactory.setSourceDistribution(new NClassesPerFile("php","File_",5));
				codeFactory.setOrdering(new PHPAllignment());
			}
			else{
				System.out.println("Invalid Language: " + lang);
				printUsage();
				return;
			}
			
			//parse umlet doc
			UMLetDocument umlet = new UMLetDocument(new File(uxf_file));
			UMLDocument uml = new UMLDocument(umlet);
			
			//init root folder/output directory
			File rootFolder = new File(out_dir);
			System.out.println();
			if(!rootFolder.exists())
				rootFolder.mkdirs();
			
			//produce code
			codeFactory.produceCode(uml, rootFolder);
		}
	}
	
	public static void printUsage(){
		System.out.println("Usage: java kirstein.main.CommandLineInterface [LANG] [uxf file] [output dir]");
		System.out.println("Possible Languages:");
		System.out.println("\tJAVA");
		System.out.println("\tPHP");
	}
}

package kirstein.source;

import java.io.File;
import java.util.Iterator;
import java.util.LinkedList;

import kirstein.uml.UMLClass;
import kirstein.uml.UMLDocument;

public class NClassesPerFile implements SourceFileDistributionMethod{

	private int n;
	private String extension;
	private String names;
	
	public NClassesPerFile(String extension, String names, int n){
		this.n = n;
		this.extension = extension;
		this.names = names;
	}
	
	@Override
	public Iterable<SourceFile> getFiles(UMLDocument document, File rootFolder) {
		Iterator<UMLClass> classes = document.iterator();
		int c = 0;
		int f = 0;
		LinkedList<SourceFile> ret = new LinkedList<SourceFile>();
		LinkedList<UMLClass> curClasses = new LinkedList<UMLClass>();
		ret.add(new SourceFile(rootFolder.getAbsolutePath()+File.separator+names+f+"."+extension,curClasses));
		while(classes.hasNext()){
			if(c>=n){
				c=0;
				f++;
				curClasses = new LinkedList<UMLClass>();
				ret.add(new SourceFile(rootFolder.getAbsolutePath()+File.separator+names+f+"."+extension,curClasses));
			}
			else
				curClasses.add(classes.next());
			c++;
		}
		return ret;
	}
}

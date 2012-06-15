package kirstein.source;

import java.io.File;
import java.util.LinkedList;

import kirstein.uml.UMLClass;
import kirstein.uml.UMLDocument;

/**
 * @author Glank
 * 
 * The default SourceFileDistributionMethod
 */
public class OneClassPerFile implements SourceFileDistributionMethod{

	private String extention;
	
	public OneClassPerFile(String extention){
		this.extention = extention;
	}
	
	@Override
	public Iterable<SourceFile> getFiles(UMLDocument document, File rootFolder) {
		LinkedList<SourceFile> files = new LinkedList<SourceFile>();
		for(UMLClass c:document){
			LinkedList<UMLClass> oneClass = new LinkedList<UMLClass>();
			oneClass.add(c);
			files.add(new SourceFile(rootFolder.getAbsolutePath()+File.separator+c.getName()+"."+extention,oneClass));
		}
		return files;
	}

}

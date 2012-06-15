package kirstein.source;

import java.io.File;
import java.util.Iterator;

import kirstein.uml.UMLClass;

/**
 * @author Ernest Kirstein
 * 
 * A 'File' wrapper which keeps track of which class go to which file.
 */
public class SourceFile extends File implements Iterable<UMLClass>{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8590239361001521623L;
	private Iterable<UMLClass> classes;
	private int classCount = -1;
	
	public SourceFile(String name, Iterable<UMLClass> classes){
		super(name);
		this.classes = classes;
	}
	
	public int countClasses(){
		//if the classes have already been counted
		if(classCount!=-1)
			return classCount;
		
		classCount = 0;
		//for each class
		for(@SuppressWarnings("unused") UMLClass c:classes)
			classCount++;
		return classCount;
	}
	
	public Iterator<UMLClass> iterator(){
		return classes.iterator();
	}
}

package kirstein.uml;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;

import kirstein.umlet.UMLetClass;
import kirstein.umlet.UMLetDocument;
import kirstein.xml.XMLNode;

public class UMLDocument implements Iterable<UMLClass>{
	private Vector<UMLClass> classes = new Vector<UMLClass>();
	private HashMap<UMLetClass, UMLClass> classMap = new HashMap<UMLetClass, UMLClass>();
	
	public UMLDocument(UMLetDocument uml) throws UMLetToUMLParsingException{
		for(UMLetClass c:uml){
			classes.add(new UMLClass(c));
			classMap.put(c, classes.lastElement());
		}
		ClassLinkerUtil.linkClasses(uml, classMap);
	}
	
	public Iterator<UMLClass> iterator(){
		return classes.iterator();
	}
	
	public XMLNode toXML(){
		XMLNode root = new XMLNode("UMLDocument","");
		for(UMLClass c:classes)
			root.addChild(c.toXML());
		return root;
	}
}

package kirstein.umlet;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.Vector;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import kirstein.xml.XMLNode;

public class UMLetDocument implements Iterable<UMLetClass>{
	
	private Vector<UMLetClass> classes = new Vector<UMLetClass>();
	private Vector<UMLetRelation> relations = new Vector<UMLetRelation>();
	
	public UMLetDocument(File f) throws ParserConfigurationException, SAXException, IOException{
		XMLNode root = XMLNode.getRoot(f);
		Vector<XMLNode> elements = root.getAllChildren("element");
		for(XMLNode node:elements){
			String name = node.getAllChildren("type").firstElement().getText();
			if(name.equals("com.umlet.element.Class")){
				classes.add(new UMLetClass(node));
			}
			else if(name.equals("com.umlet.element.Relation")){
				relations.add(new UMLetRelation(node));
			}
		}
	}
	
	public UMLetClass getClass(int i){
		return classes.get(i);
	}
	
	public int countClasses(){
		return classes.size();
	}
	
	public Iterator<UMLetClass> iterator(){
		return classes.iterator();
	}
	
	public UMLetRelation getRelation(int i){
		return relations.get(i);
	}
	
	public int countRelations(){
		return relations.size();
	}
	
	public void print(){
		for(UMLetClass c:classes)
			System.out.println(c+"\n");
		for(UMLetRelation r:relations)
			System.out.println(r+"\n");
	}
	
	public String toString(){
		String ret = "";
		for(UMLetClass c:classes)
			ret+=c.toString()+"\n\n";
		for(UMLetRelation r:relations)
			ret+=r.toString()+"\n\n";
		return ret;
	}
}

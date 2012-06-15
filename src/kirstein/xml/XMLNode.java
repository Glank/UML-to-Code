package kirstein.xml;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.Vector;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class XMLNode {
	private Vector<XMLNode> children = new Vector<XMLNode>();
	private Vector<Attribute> attributes = new Vector<Attribute>();
	private String name;
	private String text;
	
	public XMLNode(Node n){
		name = n.getNodeName();
		text = "";
		//add attributes
		NamedNodeMap nnm = n.getAttributes();
		if(nnm!=null){
			for(int j = 0; j < nnm.getLength(); j++){
				attributes.add(new Attribute(nnm.item(j)));
			}
		}
		//add children and text
		NodeList nl = n.getChildNodes();
		for(int i = 0; i < nl.getLength(); i++){
			if(nl.item(i).getNodeName().equals("#text")){
				if(!text.isEmpty())
					text+='\n';
				text+=nl.item(i).getNodeValue().trim();
			}
			else
				children.add(new XMLNode(nl.item(i)));
		}
	}
	
	public XMLNode(String name, String text){
		this.name = name;
		this.text = text;
	}
	
	public static XMLNode getRoot(File f) throws ParserConfigurationException, SAXException, IOException{
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		Document dom = db.parse(f.getAbsolutePath());
		return new XMLNode(dom.getDocumentElement());
	}
	
	public Vector<XMLNode> getAllChildren(String name){
		Vector<XMLNode> all = new Vector<XMLNode>();
		for(XMLNode n:children){
			if(n.getName().equals(name))
				all.add(n);
		}
		return all;
	}
	
	public Attribute getAttribute(String name){
		for(Attribute a:attributes)
			if(a.getName().equals(name))
				return a;
		return null;
	}
	
	public String getName(){
		return name;
	}
	
	public String getText(){
		return text;
	}
	
	public int countChildren(){
		return children.size();
	}
	
	public XMLNode getChild(int i){
		return children.get(i);
	}
	
	public void addChild(XMLNode child){
		children.add(child);
	}
	
	public void addChild(int i, XMLNode child){
		children.add(i,child);
	}
	
	public int countAttributes(){
		return attributes.size();
	}
	
	public Attribute getAttribute(int i){
		return attributes.get(i);
	}
	
	public void addAttribute(Attribute attribute){
		attributes.add(attribute);
	}
	
	public void addAttribute(int i, Attribute attribute){
		attributes.add(i,attribute);
	}
	
	public String toString(){
		return toString(0);
	}
	
	public String toString(int depth){
		String ret = "";
		for(int i = 0; i < depth; i++)
			ret+="   ";
		ret+='<'+name;
		for(int i = 0; i < attributes.size(); i++)
			ret+=" "+attributes.get(i);
		ret+=">";
		for(int j = 0; j < children.size(); j++)
			ret+="\n"+children.get(j).toString(depth+1);
		ret+="\n";
		if(!text.isEmpty()){
			Scanner s = new Scanner(text);
			while(s.hasNextLine()){
				for(int i = -1; i < depth; i++)
					ret+="   ";
				ret+=s.nextLine()+"\n";
			}
		}
		for(int i = 0; i < depth; i++)
			ret+="   ";
		ret+="</"+name+">";
		return ret;
	}
	
	public static void main(String[] args) throws Throwable{
		XMLNode n = XMLNode.getRoot(new File("test//xml_test_0.dat"));
		System.out.println(n);
	}
}

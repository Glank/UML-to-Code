package kirstein.uml;

import java.util.Iterator;
import java.util.Scanner;
import java.util.Vector;

import kirstein.xml.XMLNode;

public class UMLMethod implements Iterable<UMLMethodParameter>{
	private AccessType access;
	private boolean isStatic;
	private boolean isAbstract;
	private String name;
	private String returnType;
	private Vector<UMLMethodParameter> parameters = new Vector<UMLMethodParameter>();
	private UMLClass parent;
	private Vector<String> comments;
	
	public UMLMethod(String definition, UMLClass parent, Vector<String> comments) throws UMLetToUMLParsingException{
		this.parent = parent;
		this.comments = comments;
		String initialDef = definition;
		definition = definition.trim();
		
		//parse static and abstract definitions
		if(definition.matches("_.*_")){
			isStatic = true;
			definition = definition.substring(1,definition.length()-1).trim();
		}
		else
			isStatic = false;
		if(definition.matches("/.*/")){
			isAbstract = true;
			definition = definition.substring(1,definition.length()-1).trim();
			if(definition.matches("_.*_")){
				isStatic = true;
				definition = definition.substring(1,definition.length()-1).trim();
			}
		}
		else
			isAbstract = false;
		
		//check access
		if(definition.startsWith("+"))
			access = AccessType.PUBLIC;
		else if(definition.startsWith("-"))
			access = AccessType.PRIVATE;
		else if(definition.startsWith("#"))
			access = AccessType.PROTECTED;
		else if(definition.startsWith("~"))
			access = AccessType.PACKAGE;
		else
			throw new UMLetToUMLParsingException("Invalid Method Access Definition: "+initialDef);
		//remove access modifier
		definition = definition.substring(1).trim();
		
		//separate name, parameters, and return type
		int openParenIndex = definition.indexOf('(');
		int closeParenIndex = definition.indexOf(')');
		if(openParenIndex==-1 || closeParenIndex==-1)
			throw new UMLetToUMLParsingException("Invalid Method Parentheses: "+initialDef);
		name = definition.substring(0,openParenIndex).trim();
		String preParameters = definition.substring(openParenIndex+1, closeParenIndex);
		String preReturnType = definition.substring(closeParenIndex+1);
		
		//parse parameters
		Scanner paramBreaker = new Scanner(preParameters);
		paramBreaker.useDelimiter(",");
		while(paramBreaker.hasNext()){
			parameters.add(new UMLMethodParameter(paramBreaker.next().trim()));
		}
		
		//parse return type
		int colon = preReturnType.indexOf(':');
		//the only way that there isn't a colon is if it is a constructor (so no return type)
		if(colon==-1)
			returnType = "";
		else{
			//if there's more than just whitespace before the return-type-colon
			if(!preReturnType.substring(0,colon).trim().isEmpty())
				throw new UMLetToUMLParsingException("Invalid Return Type: "+initialDef);
			returnType = preReturnType.substring(colon+1).trim();
		}
		
		//check for an invalid name (empty or containing whitespace)
		if(name.matches(".*[ \t].*"))
			throw new UMLetToUMLParsingException("Method Name Contains Whitespace: "+initialDef);
		if(name.isEmpty())
			throw new UMLetToUMLParsingException("Method Name is Empty: "+initialDef);
	}
	
	public UMLClass getParent(){
		return parent;
	}
	
	public AccessType getAccessType(){
		return access;
	}
	
	public boolean isStatic(){
		return isStatic;
	}
	
	public boolean isAbstract(){
		if(isAbstract)
			return true;
		return parent.getType()==ClassType.INTERFACE;
	}
	
	public String getName(){
		return name;
	}
	
	public String getReturnType(){
		return returnType;
	}
	
	public int countParameters(){
		return parameters.size();
	}
	
	public UMLMethodParameter getParameter(int i){
		return parameters.get(i);
	}
	
	public XMLNode toXML(){
		XMLNode root = new XMLNode("UMLMethod", "");
		root.addChild(new XMLNode("access", access.toString()));
		root.addChild(new XMLNode("static", Boolean.toString(isStatic)));
		root.addChild(new XMLNode("abstract", Boolean.toString(isAbstract)));
		root.addChild(new XMLNode("name",name));
		root.addChild(new XMLNode("return",returnType));
		for(UMLMethodParameter p:parameters)
			root.addChild(p.toXML());
		return root;
	}

	@Override
	public Iterator<UMLMethodParameter> iterator() {
		return parameters.iterator();
	}

	public Vector<String> getComments() {
		return comments;
	}
}

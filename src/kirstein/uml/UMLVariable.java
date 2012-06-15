package kirstein.uml;

import java.util.Vector;

import kirstein.xml.XMLNode;

public class UMLVariable {
	private AccessType access;
	private boolean isStatic;
	private String name;
	private String type;
	private Vector<String> comments;
	
	public UMLVariable(String definition, Vector<String> comments) throws UMLetToUMLParsingException{
		this.comments = comments;
		String initialDef = definition;
		definition = definition.trim();
		
		//check for static
		if(definition.matches("_.*_")){
			isStatic = true;
			definition = definition.substring(1,definition.length()-1).trim();
		}
		else
			isStatic = false;
		
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
			throw new UMLetToUMLParsingException("Invalid Variable Access Definition: "+initialDef);
		//remove access modifier
		definition = definition.substring(1).trim();
		
		//divide name and type
		int colonIndex = definition.indexOf(':');
		if(colonIndex==-1)
			throw new UMLetToUMLParsingException("No Variable Name Division: "+initialDef);
		name = definition.substring(0,colonIndex).trim();
		type = definition.substring(colonIndex+1).trim();
		
		//check for an invalid name or type (empty or containing whitespace)
		if(name.matches(".*[ \t].*"))
			throw new UMLetToUMLParsingException("Variable Name Contains Whitespace: "+initialDef);
		if(name.isEmpty())
			throw new UMLetToUMLParsingException("Variable Name is Empty: "+initialDef);
		if(type.matches(".*[ \t].*"))
			throw new UMLetToUMLParsingException("Variable Type Contains Whitespace: "+initialDef);
		if(type.isEmpty())
			throw new UMLetToUMLParsingException("Variable Type is Empty: "+initialDef);
	}
	
	public AccessType getAccessType(){
		return access;
	}
	
	public boolean isStatic(){
		return isStatic;
	}
	
	public String getName(){
		return name;
	}
	
	public String getType(){
		return type;
	}
	
	public XMLNode toXML(){
		XMLNode root = new XMLNode("UMLVariable", "");
		root.addChild(new XMLNode("access", access.toString()));
		root.addChild(new XMLNode("static", Boolean.toString(isStatic)));
		root.addChild(new XMLNode("name",name));
		root.addChild(new XMLNode("type",type));
		return root;
	}

	public Vector<String> getComments() {
		return comments;
	}
}

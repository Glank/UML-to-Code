package kirstein.uml;

import kirstein.xml.XMLNode;

public class UMLMethodParameter {
	private String name;
	private String type;
	
	public UMLMethodParameter(String definition) throws UMLetToUMLParsingException{
		String initialDef = definition;
		definition = definition.trim();

		int colon = definition.indexOf(':');
		if(colon==-1)
			throw new UMLetToUMLParsingException("Invalid Method Parameter Definition: "+initialDef);
		
		name = definition.substring(0,colon).trim();
		type = definition.substring(colon+1).trim();
		
		//check for an invalid name or type (empty or containing whitespace)
		if(name.matches(".*[ \t].*"))
			throw new UMLetToUMLParsingException("Method Parameter Name Contains Whitespace: "+initialDef);
		if(name.isEmpty())
			throw new UMLetToUMLParsingException("Method Parameter Name is Empty: "+initialDef);
		if(type.matches(".*[ \t].*"))
			throw new UMLetToUMLParsingException("Method Parameter Type Contains Whitespace: "+initialDef);
		if(type.isEmpty())
			throw new UMLetToUMLParsingException("Method Parameter Type is Empty: "+initialDef);
	}
	
	public String getName(){
		return name;
	}
	
	public String getType(){
		return type;
	}
	
	public XMLNode toXML(){
		XMLNode root = new XMLNode("UMLMethodParameter", "");
		root.addChild(new XMLNode("name",name));
		root.addChild(new XMLNode("type",type));
		return root;
	}
}

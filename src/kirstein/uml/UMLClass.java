package kirstein.uml;

import java.util.Scanner;
import java.util.Vector;

import kirstein.umlet.UMLetClass;
import kirstein.xml.XMLNode;

public class UMLClass {
	private ClassType type;
	private Vector<UMLClass> extends_ = new Vector<UMLClass>();
	private Vector<UMLClass> implements_ = new Vector<UMLClass>();
	private String name;
	private Vector<UMLVariable> variables = new Vector<UMLVariable>();
	private Vector<UMLMethod> methods = new Vector<UMLMethod>();
	private Vector<String> comments;
	
	public UMLClass(UMLetClass uml) throws UMLetToUMLParsingException{
		Vector<String> classDefinition = new Vector<String>();
		Vector<String> classComments = new Vector<String>();
		Vector<String> variableDefinitions = new Vector<String>();
		Vector<String> methodDefinitions = new Vector<String>();
		Vector<Vector<String>> variableComments = new Vector<Vector<String>>();
		Vector<Vector<String>> methodComments = new Vector<Vector<String>>();
		Scanner scanner = new Scanner(uml.getTotalText());
		int type = 0;
		while(scanner.hasNextLine()){
			String line = scanner.nextLine();
			if(line.trim().isEmpty())
				continue;
			else if(line.startsWith("//")){ //is comment
				switch(type){
				case 0: classComments.add(line.substring(2)); break;
				case 1:
					while(variableComments.size()<=variableDefinitions.size())
						variableComments.add(new Vector<String>());
					variableComments.lastElement().add(line.substring(2));
					break;
				case 2:
					while(methodComments.size()<= methodDefinitions.size())
						methodComments.add(new Vector<String>());
					methodComments.lastElement().add(line.substring(2));
					break;					
				}
			}
			else if(line.equals("--")){
				type++;
			}
			else{
				switch(type){
				case 0: classDefinition.add(line); break;
				case 1: variableDefinitions.add(line); break;
				case 2: methodDefinitions.add(line); break;
				default: throw new UMLetToUMLParsingException("Extranious Class Break");
				}
			}
		}
		parseClassDef(classDefinition, classComments);
		parseVarDef(variableDefinitions, variableComments);
		parseMethDef(methodDefinitions, methodComments);
	}
	
	public Vector<UMLClass> getExtends(){
		return extends_;
	}
	
	public Vector<UMLClass> getImplements(){
		return implements_;
	}
	
	public ClassType getType(){
		return type;
	}
	
	public void extendOrImplement(UMLClass clazz){
		if(clazz.getType()==ClassType.INTERFACE)
			implements_.add(clazz);
		else
			extends_.add(clazz);
	}
	
	public String getName(){
		return name;
	}
	
	public Vector<UMLVariable> getVariables(){
		return variables;
	}
	
	public Vector<UMLMethod> getMethods(){
		return methods;
	}
	
	private void parseClassDef(Vector<String> lines, Vector<String> classComments) throws UMLetToUMLParsingException{
		//check for invalid definition sizes
		if(lines.size()==0)
			throw new UMLetToUMLParsingException("Empty Class Definition");
		else if(lines.size()>2)
			throw new UMLetToUMLParsingException("Overflow Class Definition");
		//parse a single-line definition
		else if(lines.size()==1){
			name = lines.get(0).trim();
			if(name.matches("/.*/")){
				type = ClassType.ABSTRACT_CLASS;
				name = name.substring(1,name.length()-1).trim();
			}
			else
				type = ClassType.NORMAL_CLASS;
		}
		//parse a two-line definition
		else{
			String typeDef = lines.get(0).trim();
			name = lines.get(1).trim();
			if(typeDef.equals("<<interface>>"))
				type = ClassType.INTERFACE;
			else if(typeDef.equals("<<abstract>>"))
				type = ClassType.ABSTRACT_CLASS;
			else
				throw new UMLetToUMLParsingException("Invalid Class Type: "+typeDef);
		}
		//catch empty names and names with spaces
		if(name.matches(".*[ \t].*"))
			throw new UMLetToUMLParsingException("Class Name Contains Whitespace: "+name);
		if(name.isEmpty())
			throw new UMLetToUMLParsingException("Class Name is Empty");
		comments = classComments;
	}
	
	private void parseVarDef(Vector<String> lines, Vector<Vector<String>> variableComments) throws UMLetToUMLParsingException{
		while(variableComments.size()<lines.size())
			variableComments.add(new Vector<String>());
		for(int v = 0; v < lines.size(); v++){
			try{
				variables.add(new UMLVariable(lines.get(v), variableComments.get(v)));
			} catch (UMLetToUMLParsingException ex){
				throw new UMLetToUMLParsingException(ex.getMessage() + " in " + name + " variable " + v);
			}
		}
	}
	
	private void parseMethDef(Vector<String> lines, Vector<Vector<String>> methodComments) throws UMLetToUMLParsingException{
		while(methodComments.size()<lines.size())
			methodComments.add(new Vector<String>());
		for(int m = 0; m < lines.size(); m++)
		{
			try{
				methods.add(new UMLMethod(lines.get(m), this, methodComments.get(m)));
			} catch (UMLetToUMLParsingException ex){
				throw new UMLetToUMLParsingException(ex.getMessage() + " in " + name + " method " + m);
			}
		}
	}
	
	public XMLNode toXML(){
		XMLNode root = new XMLNode("UMLClass","");
		root.addChild(new XMLNode("name", name));
		root.addChild(new XMLNode("type",type.toString()));
		for(UMLClass ex:extends_)
			root.addChild(new XMLNode("extends",ex.getName()));
		for(UMLClass im:implements_)
			root.addChild(new XMLNode("implements",im.getName()));
		for(UMLVariable v:variables)
			root.addChild(v.toXML());
		for(UMLMethod m:methods)
			root.addChild(m.toXML());
		return root;
	}

	public Vector<String> getComments() {
		return comments;
	}
}

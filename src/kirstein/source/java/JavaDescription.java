package kirstein.source.java;

import java.util.Iterator;
import java.util.LinkedList;

import kirstein.source.LanguageDescription;
import kirstein.source.LanguageFactoryException;
import kirstein.source.SourceFile;
import kirstein.uml.ClassType;
import kirstein.uml.UMLClass;
import kirstein.uml.UMLMethod;
import kirstein.uml.UMLMethodParameter;
import kirstein.uml.UMLVariable;

public class JavaDescription implements LanguageDescription{

	@Override
	public Iterable<String> getFileHeader(SourceFile file) throws LanguageFactoryException {
		LinkedList<String> lines = new LinkedList<String>();
		return lines;
	}

	@Override
	public Iterable<String> getClassHeader(UMLClass clazz) throws LanguageFactoryException {
		LinkedList<String> lines = new LinkedList<String>();
		for(int i = 0; i < clazz.getComments().size(); i++){
			lines.add("//"+clazz.getComments().get(i));
		}
		String header = "";
		switch(clazz.getType()){
		case ABSTRACT_CLASS:
		case NORMAL_CLASS:
			header = buildClassHeader(clazz);
			break;
		case INTERFACE:
			header = buildInterfaceHeader(clazz);
			break;
		}
		lines.add(header);
		return lines;
	}
	
	private String buildInterfaceHeader(UMLClass clazz) throws LanguageFactoryException{
		String header = "public interface "+clazz.getName();
		if(!clazz.getExtends().isEmpty()){
			header+=" extends";
			Iterator<UMLClass> next = clazz.getExtends().iterator();
			while(next.hasNext()){
				header+=" "+next.next().getName();
				if(next.hasNext())
					header+=",";
			}
		}
		if(!clazz.getImplements().isEmpty()){
			throw new LanguageFactoryException("Use extends, not implements.");
		}
		header+="{";
		return header;
	}
	
	private String buildClassHeader(UMLClass clazz) throws LanguageFactoryException{
		String header = "public ";
		if(clazz.getType()==ClassType.ABSTRACT_CLASS)
			header+="abstract ";
		header+="class "+clazz.getName();
		if(!clazz.getExtends().isEmpty()){
			if(clazz.getExtends().size()!=1)
				throw new LanguageFactoryException("Java does not support multiple inheritence.");
			header+=" extends "+clazz.getExtends().firstElement().getName();
		}
		if(!clazz.getImplements().isEmpty()){
			header+=" implements";
			Iterator<UMLClass> next = clazz.getImplements().iterator();
			while(next.hasNext()){
				header+=" "+next.next().getName();
				if(next.hasNext())
					header+=",";
			}
		}
		header+="{";
		return header;
	}

	@Override
	public Iterable<String> getVariableDeclaration(UMLVariable variable) throws LanguageFactoryException {
		LinkedList<String> lines = new LinkedList<String>();
		for(int i = 0; i < variable.getComments().size(); i++){
			lines.add("//"+variable.getComments().get(i));
		}
		String declaration = "";
		switch(variable.getAccessType()){
		case PACKAGE: break;
		case PRIVATE: declaration+="private "; break;
		case PROTECTED: declaration+="protected "; break;
		case PUBLIC: declaration+="public "; break;
		}
		if(variable.isStatic())
			declaration+="static ";
		declaration+=variable.getType()+" "+variable.getName()+";";
		lines.add(declaration);
		return lines;
	}

	@Override
	public Iterable<String> getMethodTemplate(UMLMethod method) throws LanguageFactoryException {
		LinkedList<String> lines = new LinkedList<String>();
		for(int i = 0; i < method.getComments().size(); i++){
			lines.add("//"+method.getComments().get(i));
		}
		String declaration = "";
		switch(method.getAccessType()){
		case PACKAGE: break;
		case PRIVATE: declaration+="private "; break;
		case PROTECTED: declaration+="protected "; break;
		case PUBLIC: declaration+="public "; break;
		}
		if(method.isStatic()){
			if(method.isAbstract())
				throw new LanguageFactoryException("A java method cannot be both static and abstract.");
			declaration+="static ";
		}
		else if(method.isAbstract())
			declaration+="abstract ";
		declaration+=method.getReturnType()+" "+method.getName()+"(";
		Iterator<UMLMethodParameter> parameters = method.iterator();
		while(parameters.hasNext()){
			UMLMethodParameter p = parameters.next();
			declaration+=p.getType()+" "+p.getName();
			if(parameters.hasNext())
				declaration+=", ";
		}
		if(method.isAbstract()){
			declaration+=");";
			lines.add(declaration);
		}
		else{
			declaration+="){";
			lines.add(declaration);
			lines.add("\t//TODO");
			if(!method.getReturnType().equals("void") && !method.getReturnType().isEmpty()){
				String ret = "\treturn ";
				if(Character.isUpperCase(method.getReturnType().charAt(0)))
					ret+="null";
				else if(method.getReturnType().equals("char"))
					ret+=' ';
				else if(method.getReturnType().equals("boolean"))
					ret+="false";
				else if(method.getReturnType().contains("["))
					ret+="null";
				else
					ret+="0";
				ret+=";";
				lines.add(ret);
			}
			lines.add("}");
		}
		return lines;
	}

	@Override
	public Iterable<String> getClassTerminator(UMLClass clazz) throws LanguageFactoryException {
		LinkedList<String> lines = new LinkedList<String>();
		lines.add("}");
		return lines;
	}

	@Override
	public Iterable<String> getFileTerminator(SourceFile file) throws LanguageFactoryException {
		LinkedList<String> lines = new LinkedList<String>();
		return lines;
	}

}

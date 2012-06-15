package kirstein.source.php;

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

public class PHPDescription implements LanguageDescription{

	@Override
	public Iterable<String> getFileHeader(SourceFile file) throws LanguageFactoryException {
		LinkedList<String> lines = new LinkedList<String>();
		lines.add("<?php");
		return lines;
	}

	@Override
	public Iterable<String> getClassHeader(UMLClass clazz) throws LanguageFactoryException {
		LinkedList<String> lines = new LinkedList<String>();
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
		String header = "interface "+clazz.getName();
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
		String header = "";
		if(clazz.getType()==ClassType.ABSTRACT_CLASS)
			header+="abstract ";
		header+="class "+clazz.getName();
		if(!clazz.getExtends().isEmpty()){
			if(clazz.getExtends().size()!=1)
				throw new LanguageFactoryException("PHP does not support multiple inheritence.");
			header+=" extends "+clazz.getExtends().firstElement();
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
		String declaration = "";
		if(method.isAbstract() && method.getParent().getType()!=ClassType.INTERFACE)
			declaration+="abstract ";
		switch(method.getAccessType()){
		case PACKAGE: break;
		case PRIVATE: declaration+="private "; break;
		case PROTECTED: declaration+="protected "; break;
		case PUBLIC: declaration+="public "; break;
		}
		if(method.isStatic()){
			if(method.isAbstract())
				throw new LanguageFactoryException("A PHP method cannot be both static and abstract.");
			declaration+="static ";
		}
		
		declaration+="function "+method.getName()+"(";
		Iterator<UMLMethodParameter> parameters = method.iterator();
		while(parameters.hasNext()){
			UMLMethodParameter p = parameters.next();
			declaration+="$"+p.getName();
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
				lines.add("\treturn null;");
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
		lines.add(">");
		return lines;
	}

}

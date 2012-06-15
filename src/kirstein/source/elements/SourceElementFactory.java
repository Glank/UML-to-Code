package kirstein.source.elements;

import java.util.Vector;

import kirstein.source.SourceFile;
import kirstein.uml.UMLClass;
import kirstein.uml.UMLMethod;
import kirstein.uml.UMLVariable;

public class SourceElementFactory {
	public static Vector<SourceElement<?>> getAllSourceElements(SourceFile file){
		Vector<SourceElement<?>> elements = new Vector<SourceElement<?>>();
		//load the file
		elements.add(new FileHeader(file));
		//load classes
		for(UMLClass c:file){
			elements.add(new ClassHeader(c));
			//load variables
			for(UMLVariable v:c.getVariables())
				elements.add(new VariableDeclaration(v));
			//load methods
			for(UMLMethod m:c.getMethods())
				elements.add(new MethodTemplate(m));
			elements.add(new ClassTerminator(c));
		}
		elements.add(new FileTerminator(file));
		return elements;
	}
}

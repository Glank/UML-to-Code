package kirstein.source.elements;

import kirstein.source.LanguageDescription;
import kirstein.source.LanguageFactoryException;
import kirstein.uml.UMLMethod;

public class MethodTemplate extends SourceElement<UMLMethod> {
	
	public MethodTemplate(UMLMethod describer) {
		super(describer);
		leadingTabs = 1;
	}

	@Override
	public Iterable<String> getSourceCode(LanguageDescription ld) throws LanguageFactoryException{
		return ld.getMethodTemplate(getDescriber());
	}
}
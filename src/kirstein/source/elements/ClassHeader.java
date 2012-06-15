package kirstein.source.elements;

import kirstein.source.LanguageDescription;
import kirstein.source.LanguageFactoryException;
import kirstein.uml.UMLClass;

public class ClassHeader extends SourceElement<UMLClass> {
	
	public ClassHeader(UMLClass describer) {
		super(describer);
	}

	@Override
	public Iterable<String> getSourceCode(LanguageDescription ld)  throws LanguageFactoryException{
		return ld.getClassHeader(getDescriber());
	}
}
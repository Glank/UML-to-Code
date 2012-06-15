package kirstein.source.elements;

import kirstein.source.LanguageDescription;
import kirstein.source.LanguageFactoryException;
import kirstein.uml.UMLClass;

public class ClassTerminator extends SourceElement<UMLClass> {
	
	public ClassTerminator(UMLClass describer) {
		super(describer);
	}

	@Override
	public Iterable<String> getSourceCode(LanguageDescription ld) throws LanguageFactoryException{
		return ld.getClassTerminator(getDescriber());
	}
}
package kirstein.source.elements;

import kirstein.source.LanguageDescription;
import kirstein.source.LanguageFactoryException;
import kirstein.uml.UMLVariable;

public class VariableDeclaration extends SourceElement<UMLVariable> {
	
	public VariableDeclaration(UMLVariable describer) {
		super(describer);
		leadingTabs = 1;
	}

	@Override
	public Iterable<String> getSourceCode(LanguageDescription ld) throws LanguageFactoryException{
		return ld.getVariableDeclaration(getDescriber());
	}
}
package kirstein.source.elements;

import kirstein.source.LanguageDescription;
import kirstein.source.LanguageFactoryException;

public abstract class SourceElement<D> {
	private D describer;
	public int leadingTabs = 0;
	public boolean leadingNewLine = true;
	
	public SourceElement(D describer){
		this.describer = describer;
	}
	
	public D getDescriber(){
		return describer;
	}
	
	public abstract Iterable<String> getSourceCode(LanguageDescription ld) throws LanguageFactoryException;
}

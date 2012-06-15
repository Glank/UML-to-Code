package kirstein.source.elements;

import kirstein.source.LanguageDescription;
import kirstein.source.LanguageFactoryException;
import kirstein.source.SourceFile;

public class FileTerminator extends SourceElement<SourceFile> {
	
	public FileTerminator(SourceFile describer) {
		super(describer);
	}

	@Override
	public Iterable<String> getSourceCode(LanguageDescription ld) throws LanguageFactoryException{
		return ld.getFileTerminator(getDescriber());
	}
}
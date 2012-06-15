package kirstein.source.elements;

import kirstein.source.LanguageDescription;
import kirstein.source.LanguageFactoryException;
import kirstein.source.SourceFile;

public class FileHeader extends SourceElement<SourceFile> {
	
	public FileHeader(SourceFile describer) {
		super(describer);
		leadingNewLine = false;
	}

	@Override
	public Iterable<String> getSourceCode(LanguageDescription ld) throws LanguageFactoryException{
		return ld.getFileHeader(getDescriber());
	}
}

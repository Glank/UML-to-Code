package kirstein.source;

import java.io.File;

import kirstein.uml.UMLDocument;

public interface SourceFileDistributionMethod {
	public Iterable<SourceFile> getFiles(UMLDocument document, File rootFolder);
}

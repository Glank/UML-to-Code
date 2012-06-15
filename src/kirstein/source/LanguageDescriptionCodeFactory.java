package kirstein.source;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.Vector;

import kirstein.source.elements.DefaultElementOrdering;
import kirstein.source.elements.SourceElement;
import kirstein.source.elements.SourceElementFactory;
import kirstein.source.elements.SourceElementOrdering;
import kirstein.uml.UMLDocument;

public class LanguageDescriptionCodeFactory extends CodeFactory{
	private String tabDelimiter;
	private LanguageDescription description;
	private SourceElementOrdering ordering;

	public LanguageDescriptionCodeFactory(String extension, LanguageDescription description){
		super(extension);
		tabDelimiter = "\t";
		this.description = description;
		ordering = new DefaultElementOrdering();
	}
	
	/**
	 * Produces source code for a given document
	 */
	public void produceCode(UMLDocument doc, File rootFolder) throws LanguageFactoryException, IOException{
		Iterable<SourceFile> files = getSourceDistribution().getFiles(doc, rootFolder);
		for(SourceFile f:files){
			Vector<SourceElement<?>> elements = SourceElementFactory.getAllSourceElements(f);
			Iterator<SourceElement<?>> elementIterator = ordering.organize(elements).iterator();
			StringBuilder sourceCode = new StringBuilder();
			while(elementIterator.hasNext()){
				SourceElement<?> e = elementIterator.next();
				if(e.leadingNewLine)
					sourceCode.append('\n');
				Iterator<String> lines = e.getSourceCode(description).iterator();
				while(lines.hasNext()){
					for(int t = 0; t < e.leadingTabs; t++)
						sourceCode.append(tabDelimiter);
					sourceCode.append(lines.next());
					if(lines.hasNext())
						sourceCode.append('\n');
				}
			}
			if(!f.exists())
				f.createNewFile();
			FileOutputStream fos = new FileOutputStream(f);
			fos.write(sourceCode.toString().getBytes());
			fos.close();
		}
	}
	
	public LanguageDescription getDescription() {
		return description;
	}

	public SourceElementOrdering getOrdering(){
		return ordering;
	}
	
	public void setTabSize(int size){
		tabDelimiter = "";
		//add a space 'spaces' times
		for(int i = 0; i < size; i++)
			tabDelimiter+=" ";
	}
	
	public void setTabDelimiter(String delimiter){
		tabDelimiter = delimiter;
	}
	
	public String getTabDelimiter(){
		return tabDelimiter;
	}
	
	public void setOrdering(SourceElementOrdering ordering) {
		this.ordering = ordering;
	}
}

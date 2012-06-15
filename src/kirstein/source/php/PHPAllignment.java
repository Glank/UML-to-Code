package kirstein.source.php;

import java.util.Vector;

import kirstein.source.SourceFile;
import kirstein.source.elements.SourceElement;
import kirstein.source.elements.SourceElementOrdering;

public class PHPAllignment implements SourceElementOrdering{

	@Override
	public Iterable<SourceElement<?>> organize(Vector<SourceElement<?>> elements) {
		for(SourceElement<?> element:elements){
			if(!(element.getDescriber() instanceof SourceFile)){
				element.leadingTabs++;
			}
		}
		return elements;
	}
}

package kirstein.source.elements;

import java.util.Vector;

public class DefaultElementOrdering implements SourceElementOrdering {

	@Override
	public Iterable<SourceElement<?>> organize(Vector<SourceElement<?>> elements) {
		return elements;
	}

}

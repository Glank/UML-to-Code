package kirstein.source.elements;

import java.util.Vector;

public interface SourceElementOrdering {
	public Iterable<SourceElement<?>> organize(Vector<SourceElement<?>> elements);
}

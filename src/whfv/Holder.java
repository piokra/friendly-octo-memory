package whfv;

import java.util.Collection;

public interface Holder {
	
	boolean tryAdding(Holdable h);
	boolean tryRemoving(Holdable h);
	void add(Holdable h);
	void remove(Holdable h);
	
	Collection<Holdable> asCollection();
}

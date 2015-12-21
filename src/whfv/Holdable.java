package whfv;

import java.util.Collection;

public interface Holdable {
	Collection<Holder> getParents();
	Integer tryGettingPosition(Holder parent);
	Integer getPositionInHolder(Holder parent);
	Integer getPositionOfHolder(Holder holder);
	Integer beHeld(Holder parent);
	boolean stopBeingHeld(Holder parent);
	boolean updatePosition(Holder holder, Integer position);
}

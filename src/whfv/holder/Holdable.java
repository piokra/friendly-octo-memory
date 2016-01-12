package whfv.holder;

import java.util.Collection;

/**
 * Implementations of this interface are supposed to allow for quick removal of an object from collection based on selected structure
 * @author Pan Piotr
 * @param <T> generic type
 */
public interface Holdable<T> {
    Holder getParent();
    T getObject();
}

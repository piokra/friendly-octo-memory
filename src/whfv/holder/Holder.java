package whfv.holder;

public interface Holder<T> {

    static void verifyHoldable(Holdable h, Class<? extends Holdable> type) {
        if(!h.getClass().isAssignableFrom(type))
            throw new ParentTypeMismatchException(h.getClass(), type);
        if(h.getParent()==null)
            throw new NullParentException();
    }
    Holdable<T> add(T t);
    void remove(Holdable<T> t);

}

package whfv.collision.qt;

import java.util.TreeSet;
import whfv.holder.Holdable;
import whfv.holder.PositionHolder;
import whfv.position.Position;
import whfv.position.Positionable;
import whfv.utill.Vector2d;

class QuadTreeNode implements PositionHolder {

    private final QuadTree mParent;
    protected final Vector2d mMinCorner;
    protected final Vector2d mMaxCorner;

    protected final Vector2d mMidPoint;
    protected final Vector2d mMinXMidYPoint;
    protected final Vector2d mMidXMinYPoint;
    protected final Vector2d mMaxXMidYPoint;
    protected final Vector2d mMidXMaxYPoint;

    private final int mMaxElements;
    private final TreeSet<QuadHoldable> mElements = new TreeSet<>();
    private final QuadTreeNode[] mKids = new QuadTreeNode[4];

    public QuadTreeNode(QuadTree mParent, Vector2d mMinCorner, Vector2d mMaxCorner, int mMaxElements) {
        this.mParent = mParent;
        this.mMinCorner = mMinCorner;
        this.mMaxCorner = mMaxCorner;
        this.mMaxElements = mMaxElements;
        mMidPoint = Vector2d.mul(0.5, Vector2d.add(mMinCorner, mMaxCorner));

        mMidXMinYPoint = new Vector2d(mMidPoint.x, this.mMinCorner.y);
        mMidXMaxYPoint = new Vector2d(mMidPoint.x, this.mMaxCorner.y);
        mMinXMidYPoint = new Vector2d(this.mMinCorner.x, mMidPoint.y);
        mMaxXMidYPoint = new Vector2d(this.mMaxCorner.x, mMidPoint.y);

    }

    protected boolean isInside(Vector2d p, Vector2d minCorner, Vector2d maxCorner) {
        return ((p.x > minCorner.x)
                && (p.y > minCorner.y)
                && (p.x <= maxCorner.x)
                && (p.y <= maxCorner.y));

    }

    protected boolean isInside(Vector2d p) {
        return isInside(p, mMinCorner, mMaxCorner);
    }

    protected void constructKid(int kidNum) {
        if (kidNum == 0) {
            if (mKids[0] == null) {
                mKids[0] = new QuadTreeNode(mParent, mMinCorner, mMidPoint, mMaxElements);
            }
        }
        if (kidNum == 1) {
            if (mKids[1] == null) {
                mKids[1] = new QuadTreeNode(mParent, mMidXMinYPoint, mMaxXMidYPoint, mMaxElements);
            }
        }
        if (kidNum == 2) {
            if (mKids[2] == null) {
                mKids[2] = new QuadTreeNode(mParent, mMinXMidYPoint, mMidXMaxYPoint, mMaxElements);
            }
        }
        if (kidNum == 3) {
            if (mKids[3] == null) {
                mKids[3] = new QuadTreeNode(mParent, mMidPoint, mMaxCorner, mMaxElements);
            }
        }
    }

    protected int getKidNumber(Vector2d p) {
        if (isInside(p, mMinCorner, mMidPoint)) {
            return 0;
        }
        if (isInside(p, mMidXMinYPoint, mMaxXMidYPoint)) {
            return 1;
        }
        if (isInside(p, mMinXMidYPoint, mMaxXMidYPoint)) {
            return 2;
        }
        if (isInside(p, mMidPoint, mMaxCorner)) {
            return 3;
        }
        return -1;
    }

    @Override
    public Holdable<Position> update(Holdable<Position> holdable) {
        QuadHoldable qh = (QuadHoldable) holdable;
        Vector2d pos = qh.position.getCoordinates();
        if (isInside(pos) && mElements.contains(qh)) {
            return holdable;
        } else {
            mElements.remove(qh);
            mParent.add();
        }
    }

    public Holdable<Position> add(Position p, Object owner) {
        Vector2d pos = p.getCoordinates();
        if (isInside(pos)) {
            if (mElements.size() < mMaxElements) {
                QuadHoldable qh = new QuadHoldable(this, p, owner);
                mElements.add(qh);
                return qh;
            } else {
                int kid = getKidNumber(pos);
                constructKid(kid);
                return mKids[kid].add(p, owner);
            }
        } else {
            return null;
        }
    }

    public Holdable<Position> add(QuadHoldable old) {
        return add(old.position, old.owner);
    }

    @Override
    public Holdable<Position> add(Position t) {
        return add(t, null);
    }

    @Override
    public void remove(Holdable<Position> t) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}

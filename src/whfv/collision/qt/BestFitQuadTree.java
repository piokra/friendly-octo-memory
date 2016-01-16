/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package whfv.collision.qt;

import java.util.Collection;
import java.util.LinkedList;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentLinkedQueue;
import whfv.collision.Collidable;
import whfv.holder.Holdable;
import whfv.holder.Holder;
import whfv.holder.SpatialHoldable;
import whfv.utill.Rect2D;

/**
 *
 * @author Pan Piotr
 *
 */
public class BestFitQuadTree implements QuadTree {

    private final ConcurrentLinkedQueue<Collidable> mElements = new ConcurrentLinkedQueue<>();
    private final int mMinElements;
    private final Rect2D mRectangle;
    private final BestFitQuadTree mRoot;
    private final BestFitQuadTree mParent;
    private final BestFitQuadTree[] mKids = new BestFitQuadTree[4];

    public BestFitQuadTree(int minElements, Rect2D rectangle) {
        this.mMinElements = minElements;
        this.mRectangle = rectangle;
        mRoot = this;
        mParent = this;
    }

    public BestFitQuadTree(int mMinElements, Rect2D mRectangle, BestFitQuadTree mRoot, BestFitQuadTree mParent) {
        this.mMinElements = mMinElements;
        this.mRectangle = mRectangle;
        this.mRoot = mRoot;
        this.mParent = mParent;
    }

    private class BFQTHolder implements SpatialHoldable<Collidable> {

        final BestFitQuadTree node;
        final Collidable collidable;

        public BFQTHolder(BestFitQuadTree node, Collidable collidable) {
            if (node == null || collidable == null) {
                throw new NullPointerException("Cant init BFQTHolder with null values");
            }
            this.node = node;
            this.collidable = collidable;
        }

        @Override
        public Holder getParent() {
            return node;
        }

        @Override
        public Collidable getObject() {
            return collidable;
        }

        @Override
        public Holdable<Collidable> update() {
            return node.update(this);
        }

 

    }

    @Override
    public Collection<Collidable> getLikelyCollisions(Collidable other) {
        LinkedList<Collidable> ret = new LinkedList<>();
        getLikelyCollisions(other, ret);
        return ret;
        
    }

    protected void getLikelyCollisions(Collidable other, Collection<Collidable> collection) {
        if(mRectangle.collides(other.getCollidingShape().getBoundingRectangle())) {
            collection.addAll(mElements);
            if(mKids[0]!=null) {
                for (BestFitQuadTree kid : mKids) {
                    kid.getLikelyCollisions(other,collection);
                }
            }
        }
    }

    @Override
    public Holdable<Collidable> update(Holdable<Collidable> collidable) {
        BFQTHolder qh = (BFQTHolder) collidable;
        if(mRectangle.fits(qh.collidable.getCollidingShape().getBoundingRectangle())) return qh;
        remove(collidable);
        return mRoot.add(qh.collidable);
    }

    @Override
    public Holdable<Collidable> add(Collidable t) {
        if (mRectangle.fits(t.getCollidingShape().getBoundingRectangle())) {
            if (mElements.size() < mMinElements) {
                mElements.add(t);
                return new BFQTHolder(this, t);
            } else {
                constructKids();
                int kid = 0;
                for (int i = 0; i < mKids.length; i++) {
                    BestFitQuadTree mKid = mKids[i];
                    if (mKid.mRectangle.fits(t.getCollidingShape().getBoundingRectangle())) {
                        kid = i;
                    }
                }
                return mKids[kid].add(t);

            }
        } else {
            return mParent.forceAdd(t);
        }
    }

    @Override
    public void remove(Holdable<Collidable> t) {
        BFQTHolder qh = (BFQTHolder) t;
        mElements.remove(qh);
    }

    protected Holdable<Collidable> forceAdd(Collidable c) {
        mElements.add(c);
        return new BFQTHolder(this, c);
    }

    protected void constructKids() {
        if (mKids[0] != null) {
            return;
        }
        Rect2D[] rects = mRectangle.split(mRectangle.getMidPoint());
        for (int i = 0; i < rects.length; i++) {
            mKids[i] = new BestFitQuadTree(mMinElements, rects[i], mRoot, this);
        }
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package whfv.collision.qt;

import java.util.Collection;
import whfv.collision.Collidable;
import whfv.holder.Holdable;
import whfv.holder.Holder;
import whfv.utill.Rect2D;

/**
 *
 * @author Pan Piotr
 * 
 */
public class BestFitQuadTree implements QuadTree {
    private final Collection<
    private final int mMinElements;
    private final Rect2D mRectangle;
    private final BestFitQuadTree mRoot;
    private final BestFitQuadTree mParent;
    private final BestFitQuadTree[] mKids = new BestFitQuadTree[4];

    public BestFitQuadTree(int minElements, Rect2D rectangle, Collection<Collidable> collection) {
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
    
    
    private class BFQTHolder {
        final BestFitQuadTree node;
        final Collidable collidable;

        public BFQTHolder(BestFitQuadTree node, Collidable collidable) {
            if(node==null||collidable==null) {
                throw new NullPointerException("Cant init BFQTHolder with null values");
            }
            this.node = node;
            this.collidable = collidable;
        }
        
        
    }
    
    @Override
    public Collection<Collidable> getLikelyCollisions(Collidable other) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Holdable<Collidable> update(Holder<Collidable> collidable) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Holdable<Collidable> add(Collidable t) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void remove(Holdable<Collidable> t) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    protected Holdable<Collidable> forceAdd(Collidable c) {
        
    }
    
}

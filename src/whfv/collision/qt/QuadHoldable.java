/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package whfv.collision.qt;

import whfv.holder.Holdable;
import whfv.holder.Holder;
import whfv.position.Position;
import whfv.position.Positionable;

/**
 *
 * @author Pan Piotr
 */

class QuadHoldable implements Holdable<Position> {

    public final QuadTreeNode node;
    public final Position position;
    public final Object owner;

    public QuadHoldable(QuadTreeNode node, Position position, Object owner) {
        this.node = node;
        this.position = position;
        this.owner = owner;
    }

    @Override
    public Holder getParent() {
        return node;
    }

    @Override
    public boolean equals(Object o) {
        if(o==null) return false;
        if(!(o instanceof QuadHoldable)) return false;
        QuadHoldable qh = (QuadHoldable) o;
        if((qh.owner!=null)&&(owner!=null))
            return qh.owner.equals(owner);
        return qh.position.equals(position);
    }
    
}

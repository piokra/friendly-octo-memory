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

/**
 *
 * @author Pan Piotr
 */
public interface QuadTree extends Holder<Collidable> {
    
    Collection<Collidable> getLikelyCollisions(Collidable other);
    Holdable<Collidable> update(Holdable<Collidable> collidable);
}

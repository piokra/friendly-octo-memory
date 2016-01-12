/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package whfv.collision;

/**
 *
 * @author Pan Piotr
 */
public class Collisions {

    private Collisions() {

    }

    /**
     * Checks for collision between two collidables. This returns
     * <code>Collision</code> not any of the subclasses. So when you need more
     * information about the collision you can use other methods in this class.
     *
     * @param one
     * @param other
     * @return collision or null if no collision or error
     */
    public static Collision getCollision(Collidable one, Collidable other) {
        ConvexCollidingShape first = (ConvexCollidingShape)one.getCollidingShape();
        ConvexCollidingShape second = (ConvexCollidingShape)other.getCollidingShape();
        if(first.getBoundingRectangle().collides(second.getBoundingRectangle())) {
            
        }
        return null;
    }
}

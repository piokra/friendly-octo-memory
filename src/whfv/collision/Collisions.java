/* 
 * Copyright (C) 2016 Pan Piotr
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package whfv.collision;

import java.util.logging.Level;
import java.util.logging.Logger;
import whfv.utill.Vector2d;

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
            if(first.collides(second)) {
                try {
                    return new Collision(one, other);
                } catch (CollisionException ex) {
                    return null;
                }
            }
        }
        return null;
    }
    
    public static CollisionWithNormals getCollisionWithNormals(Collision c) {
        ConvexCollidingShape cc = (ConvexCollidingShape)(c.first.getCollidingShape());
        ConvexCollidingShape cs = (ConvexCollidingShape)(c.second.getCollidingShape());
        Vector2d normal = cs.countMinimumDistanceNormal(cc);
        try {
            return new CollisionWithNormals(c.first, c.second, normal, Vector2d.neg(normal));
        } catch (CollisionException ex) {
            return null;
        }
    }
}

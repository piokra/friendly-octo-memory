/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package whfv.collision;

import whfv.collision.Collision;
import whfv.collision.Collidable;
import whfv.utill.Vector2d;

/**
 *
 * @author Pan Piotr
 */
public class CollisionWithNormals extends Collision {

    public final Vector2d firstNormal;
    public final Vector2d secondNormal;

    public CollisionWithNormals(Collidable first, Collidable second, Vector2d firstNormal, Vector2d secondNormal) throws CollisionException {
        super(first, second);
        
        if (firstNormal == secondNormal) {
            throw new CollisionException("First normal requal second normal");

        }
        
        this.firstNormal = firstNormal;
        this.secondNormal = secondNormal;
    }

}

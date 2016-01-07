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
public class Collision {
    public final Collidable first;
    public final Collidable second;

    Collision(Collidable first, Collidable second) throws CollisionException {
        if(first == second) throw new CollisionException("Same objects colliding");
        this.first = first;
        this.second = second;
    }
}

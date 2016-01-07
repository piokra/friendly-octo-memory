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
public interface Collidable {
    CollidingShape getCollidingShape();
    
    void onCollisionStart(Collision c);
    void onCollisionEnd(Collision c);
    
    void addCollision(Collision c);
    void resolveCollisions();
}

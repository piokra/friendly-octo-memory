/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package whfv.physics;

import whfv.collision.Collidable;
import whfv.utill.Vector2d;

/**
 *
 * @author Pan Piotr
 */
public interface Physical extends Collidable {
    
    double getMass();
    void addForce(Vector2d force);
    
    Vector2d getVelocity();
    Vector2d getAcceleration();
    
}

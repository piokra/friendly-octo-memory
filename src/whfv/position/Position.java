/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package whfv.position;

import whfv.utill.Vector2d;

/**
 *
 * @author Pan Piotr
 * 
 */
public interface Position {
    
    Vector2d getPosition();
    Vector2d getCoordinates();
    void move(Vector2d d);
}
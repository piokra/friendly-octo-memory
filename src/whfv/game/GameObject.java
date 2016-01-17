/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package whfv.game;

import whfv.Drawable;
import whfv.Processable;
import whfv.position.Positionable;
import whfv.utill.Transformable;

/**
 *
 * @author Pan Piotr
 */
public interface GameObject extends Positionable, Processable, Drawable, Transformable {
    void addMe(GameWorld world);
    void removeMe(GameWorld world);
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package whfv.UI;

import whfv.Drawable;
import whfv.EventProcessor;
import whfv.Processable;
import whfv.collision.Boundable;
import whfv.position.Positionable;
import whfv.utill.Transformable;

/**
 *
 * @author Pan Piotr
 */
public interface View extends Drawable, Processable, EventProcessor, Positionable, Boundable, Transformable {

}

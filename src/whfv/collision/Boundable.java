/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package whfv.collision;

import whfv.utill.Rect2D;

/**
 *
 * @author Pan Piotr
 */
public interface Boundable {
    Rect2D getBoundingRectangle();
}

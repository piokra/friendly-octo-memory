/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package whfv.holder;

/**
 *
 * @author Pan Piotr
 */
public interface SpatialHoldable<T> extends Holdable<T> {
    Holdable<T> update();
}
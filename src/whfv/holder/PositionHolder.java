/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package whfv.holder;

import whfv.position.Position;
import whfv.position.Positionable;

/**
 * Extension of holder supposed to hold positionable objects. This interface
 * gives methods to update the location in holder when position is changed.
 *
 * @author Pan Piotr
 */
public interface PositionHolder extends Holder<Position> {
    Holdable<Position> update(Holdable<Position> holdable);
}

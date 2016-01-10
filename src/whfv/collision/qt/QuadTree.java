/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package whfv.collision.qt;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.TreeSet;
import whfv.holder.Holdable;
import whfv.holder.Holder;
import whfv.holder.PositionHolder;
import whfv.position.Position;
import whfv.position.Positionable;
import whfv.utill.Vector2d;

/**
 *
 * @author Pan Piotr
 */
public class QuadTree implements PositionHolder {

    @Override
    public Holdable<Position> add(Position t) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void remove(Holdable<Position> t) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Holdable<Position> update(Holdable<Position> holdable) {
        Holder.verifyHoldable(holdable, QuadHoldable.class);
        QuadHoldable h = (QuadHoldable) holdable;
        //todo
        return null;
    }

}

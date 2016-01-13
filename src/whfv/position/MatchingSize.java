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
 */
public class MatchingSize implements Size {

    private final Size mParent;

    public MatchingSize(Size parent) {
        if (parent == null) {
            throw new NullPointerException("Cant init size with null parent");
        }
        this.mParent = parent;
    }

    @Override
    public Vector2d getSize() {
        return mParent.getSize();
    }

    @Override
    public void setSize(Vector2d size) {

    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package whfv.position;

import whfv.utill.Vector2d;

public class RelativePosition implements Position {

    private Vector2d mPosition = null;
    private final Position mParent;

    public RelativePosition(Vector2d relativePos, Position parent) {
        if(relativePos==null) {
            throw new NullPointerException("Cannot init with null pos");
        }
        mPosition = relativePos;
        mParent = parent;
    }
    
    @Override
    public Vector2d getPosition() {
        return mPosition;
    }

    @Override
    public Vector2d getCoordinates() {
        return (mParent == null) ? getPosition() : mParent.getCoordinates();
    }

    @Override
    public void move(Vector2d d) {
        
    }

}

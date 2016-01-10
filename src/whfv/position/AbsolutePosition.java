/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package whfv.position;

import whfv.utill.Vector2d;


public class AbsolutePosition implements Position {
    private Vector2d mPosition = null;
    @Override
    public Vector2d getPosition() {
        return mPosition;
    }

    @Override
    public Vector2d getCoordinates() {
        return mPosition;
    }

    @Override
    public void move(Vector2d d) {
        mPosition = Vector2d.add(mPosition, d);
    }
    
}

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
public class AbsoluteSize implements Size {
    private Vector2d mSize;

    public AbsoluteSize(Vector2d size) {

        if (size==null) {
            throw new NullPointerException("Cant init size with null");
        }
        this.mSize = size;
    }

    @Override
    public Vector2d getSize() {
        return mSize;
    }

    @Override
    public void setSize(Vector2d size) {
        mSize=size;
    }
    
    
    
}

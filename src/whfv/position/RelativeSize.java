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
public class RelativeSize implements Size {

    private Vector2d mRelativeSize;
    private Size mParent;

    public RelativeSize(Vector2d mRelativeSize, Size mParent) {
        this.mRelativeSize = mRelativeSize;
        this.mParent = mParent;
    }
    @Override
    public Vector2d getSize() {
        return Vector2d.add(mRelativeSize, mParent.getSize());
    }

    @Override
    public void setSize(Vector2d size) {
        mRelativeSize=size;
    }
    
}

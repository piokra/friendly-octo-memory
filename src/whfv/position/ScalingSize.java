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
public class ScalingSize implements Size{
    private Vector2d mScale;
    private Size mParent;

    public ScalingSize(Vector2d mScale, Size mParent) {
        this.mScale = mScale;
        this.mParent = mParent;
    }
    
    

    @Override
    public Vector2d getSize() {
        return Vector2d.componentwiseMul(mScale, mParent.getSize());
    }

    @Override
    public void setSize(Vector2d size) {
        mScale = size;
    }
    
    
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package whfv.position;

import whfv.utill.Matrix2x2d;
import whfv.utill.Matrix3x3d;
import whfv.utill.Vector2d;
import whfv.utill.Vector3d;

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
        return (mParent == null) ? getPosition() : Vector2d.add(mPosition, mParent.getCoordinates());
    }

    @Override
    public void move(Vector2d d) {
        mPosition = Vector2d.add(mPosition, d);
    }

    @Override
    public void transform(Matrix3x3d homoTransformation) {
        mPosition = Vector3d.fromHomogeneousVector(Matrix3x3d.matVecMul(homoTransformation, Vector2d.toHomogenousVector(mPosition)));
    }

    @Override
    public void transform(Matrix2x2d transformation) {
        mPosition = Matrix2x2d.matVecMul(transformation, mPosition);
    }

}

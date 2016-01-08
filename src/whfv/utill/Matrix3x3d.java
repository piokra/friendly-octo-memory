/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package whfv.utill;

import static whfv.utill.Vector3d.*;

/**
 *
 * @author Pan Piotr
 */
public final class Matrix3x3d {

    public final Vector3d firstRow;
    public final Vector3d secondRow;
    public final Vector3d thirdRow;

    public Matrix3x3d(Vector3d firstRow, Vector3d secondRow, Vector3d thirdRow) {
        if ((firstRow == null) || (secondRow == null) || (thirdRow == null)) {
            throw new NullPointerException("You cannot create a matrix with undefinied values");
        }
        this.firstRow = firstRow;
        this.secondRow = secondRow;
        this.thirdRow = thirdRow;
    }

    public static Matrix3x3d mul(Matrix3x3d mat, double a) {
        return new Matrix3x3d(Vector3d.mul(mat.firstRow, a), Vector3d.mul(mat.secondRow, a), Vector3d.mul(mat.thirdRow, a));

    }

    public static Matrix3x3d mul(double a, Matrix3x3d mat) {
        return mul(mat, a);
    }

    public static Vector3d matVecMul(Matrix3x3d mat, Vector3d vec) {
        return new Vector3d(dot(mat.firstRow, vec), dot(mat.secondRow, vec), dot(mat.thirdRow, vec));
    }

    public static Matrix3x3d add(Matrix3x3d l, Matrix3x3d r) {
        return new Matrix3x3d(Vector3d.add(l.firstRow, r.firstRow), Vector3d.add(l.secondRow, r.secondRow), Vector3d.add(l.thirdRow, r.thirdRow));
    }

    public static Matrix3x3d sub(Matrix3x3d l, Matrix3x3d r) {
        return new Matrix3x3d(Vector3d.sub(l.firstRow, r.firstRow), Vector3d.sub(l.secondRow, r.secondRow), Vector3d.sub(l.thirdRow, r.thirdRow));
    }

    public static Matrix3x3d transpose(Matrix3x3d mat) {
        Vector3d first = new Vector3d(mat.firstRow.x, mat.secondRow.x, mat.thirdRow.x);
        Vector3d second = new Vector3d(mat.firstRow.y, mat.secondRow.y, mat.thirdRow.y);
        Vector3d third = new Vector3d(mat.firstRow.z, mat.secondRow.z, mat.thirdRow.z);
        return new Matrix3x3d(first, second, third);

    }

    public static Matrix3x3d matMatMul(Matrix3x3d l, Matrix3x3d r) {
        Matrix3x3d rp = transpose(r);
        Matrix3x3d mat = l;
        Vector3d vec = rp.firstRow;
        Vector3d first = new Vector3d(dot(mat.firstRow, vec), dot(mat.secondRow, vec), dot(mat.thirdRow, vec));
        vec = rp.secondRow;
        Vector3d second = new Vector3d(dot(mat.firstRow, vec), dot(mat.secondRow, vec), dot(mat.thirdRow, vec));
        vec = rp.thirdRow;
        Vector3d third = new Vector3d(dot(mat.firstRow, vec), dot(mat.secondRow, vec), dot(mat.thirdRow, vec));
        return new Matrix3x3d(first, second, third);
    }

    public static Matrix2x2d trimMatrix(Matrix3x3d mat) {
        return new Matrix2x2d(new Vector2d(mat.firstRow.x, mat.firstRow.y), new Vector2d(mat.secondRow.x, mat.secondRow.y));
    }

}

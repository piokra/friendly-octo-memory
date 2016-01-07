/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package whfv.utill;
import static whfv.utill.Vector2d.*;
/**
 *
 * @author Pan Piotr
 */
public final class Matrix2x2d {

    public final Vector2d firstRow;
    public final Vector2d secondRow;

    public Matrix2x2d(Vector2d firstRow, Vector2d secondRow) {
        if ((firstRow == null) || (secondRow == null)) {
            throw new NullPointerException("You cannot create a matrix with undefinied values");
        }
        this.firstRow = firstRow;
        this.secondRow = secondRow;
    }
    
    public static Matrix2x2d mul(Matrix2x2d mat, double a) {
        return new Matrix2x2d(Vector2d.mul(mat.firstRow,a),Vector2d.mul(mat.secondRow,a));

    }
    public static Matrix2x2d mul(double a, Matrix2x2d mat) {
        return mul(mat,a);
    }
    
    public static Vector2d matVecMul(Matrix2x2d mat, Vector2d vec) {
        return new Vector2d(dot(mat.firstRow,vec), dot(mat.secondRow,vec));
    }
    
    public static Matrix2x2d add(Matrix2x2d l, Matrix2x2d r) {
        return new Matrix2x2d (Vector2d.add(l.firstRow, r.firstRow),Vector2d.add(l.secondRow, r.secondRow));
    }
    
    public static Matrix2x2d sub(Matrix2x2d l, Matrix2x2d r) {
        return new Matrix2x2d (Vector2d.sub(l.firstRow, r.firstRow),Vector2d.sub(l.secondRow, r.secondRow));
    }
}

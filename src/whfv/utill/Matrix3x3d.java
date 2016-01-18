/* 
 * Copyright (C) 2016 Pan Piotr
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package whfv.utill;

import org.jsfml.graphics.Transform;
import static whfv.utill.Vector3d.*;

/**
 *
 * @author Pan Piotr
 */
public final class Matrix3x3d {

    public static final Matrix3x3d IDENTITY = new Matrix3x3d(new Vector3d(1,0,0), new Vector3d(0,1,0), new Vector3d(0,0,1));
    
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
        Vector3d vec = mat.firstRow;
        Vector3d first = new Vector3d(dot(rp.firstRow, vec), dot(rp.secondRow, vec), dot(rp.thirdRow, vec));
        vec = mat.secondRow;
        Vector3d second = new Vector3d(dot(rp.firstRow, vec), dot(rp.secondRow, vec), dot(rp.thirdRow, vec));
        vec = mat.thirdRow;
        Vector3d third = new Vector3d(dot(rp.firstRow, vec), dot(rp.secondRow, vec), dot(rp.thirdRow, vec));
        return new Matrix3x3d(first, second, third);
    }
    @Override
    public String toString() {
        return firstRow + "\n" + secondRow +"\n" + thirdRow;
    }
    /**
     * Attempts to create a decent approximation of what give transformation
     * would look like in 2D matrix. Assumes that first and second element of
     * third row is equal to zero.
     *
     * @param mat
     * @return Pair of elements first the trimmed matrix, second vector
     * describing the translation of the transformation
     */
    public static Pair<Matrix2x2d, Vector2d> trimMatrix(Matrix3x3d mat) {
        return new Pair<>(new Matrix2x2d(new Vector2d(mat.firstRow.x, mat.firstRow.y), 
                new Vector2d(mat.secondRow.x, mat.secondRow.y)), 
                new Vector2d(mat.firstRow.z / mat.thirdRow.z, mat.secondRow.z / mat.thirdRow.z));
    }
    
    public static Transform toSFMLTransform(Matrix3x3d m) {
        return new Transform((float)m.firstRow.x, (float)m.firstRow.y, (float)m.firstRow.z, 
                (float)m.secondRow.x, (float)m.secondRow.y, (float)m.secondRow.z, 
                (float)m.thirdRow.x, (float)m.thirdRow.y, (float)m.thirdRow.z);
    }

}

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

import static java.lang.Math.*;
import static whfv.utill.Matrix3x3d.*;

/**
 *
 * @author Pan Piotr
 */
public final class Linear2DHTransformations {
    private static final Matrix3x3d X_AXIS_REFLECTION = m(
            v(1,0,0),
            v(0,-1,0),
            v(0,0,1));
    
    private static final Matrix3x3d Y_AXIS_REFLECTION = m(
            v(-1,0,0),
            v(0,1,0),
            v(0,0,1));
    
    
    private Linear2DHTransformations() {

    }

    private static final Vector3d v(double x, double y, double z) {
        return new Vector3d(x, y, z);
    }

    private static final Matrix3x3d m(Vector3d f, Vector3d s, Vector3d t) {
        return new Matrix3x3d(f, s, t);
    }

    public static Matrix3x3d rotationMatrix(double theta) {
        return m(v(cos(theta), sin(theta), 0),
                v(-sin(theta), cos(theta), 0),
                v(0, 0, 1));
    }

    public static Matrix3x3d scalingMatrix(double xm, double ym) {
        return m(v(xm, 0, 0),
                v(0, ym, 0),
                v(0, 0, 1));
    }
    
    public static Matrix3x3d translationMatrix(double dx, double dy) {
        return m(v(1,0,dx),
                v(0,1,dy),
                v(0,0,1));
    }

    public static Matrix3x3d reflectionOverXAxisMatrix() {
        return X_AXIS_REFLECTION;
    }
    public static Matrix3x3d reflectionOverYAxisMatrix() {
        return Y_AXIS_REFLECTION;
    }
    
    public static Matrix3x3d reflectionOverVectorMatrix(Vector2d vec) {
        double theta = atan2(vec.y,vec.x);
        ///XXX i dont even know if this works i suppose that some permutation of minuses works
        return matMatMul(rotationMatrix(-theta),matMatMul(X_AXIS_REFLECTION, rotationMatrix(theta)));
    }
    
    public static Matrix3x3d horizontalSheerMatrix(double factor) {
        return m(v(1,factor,0),
                v(0,1,0),
                v(0,0,1));
        
    }
    public static Matrix3x3d verticalSheer(double factor) {
        return m(v(1,0,0),
                v(factor,1,0),
                v(0,0,1));
    }
    
    public static Matrix3x3d combine(Matrix3x3d l , Matrix3x3d r) {
        return Matrix3x3d.matMatMul(l, r);
    }
}

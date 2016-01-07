/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
}

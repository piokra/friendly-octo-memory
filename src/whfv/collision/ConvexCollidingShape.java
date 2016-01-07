/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package whfv.collision;

import java.util.Arrays;
import whfv.utill.Vector2d;
import static whfv.utill.Vector2d.*;
import whfv.utill.Matrix3x3d;
import static whfv.utill.Matrix3x3d.*;
import static java.lang.Math.*;
import whfv.utill.Vector3d;
import static whfv.utill.Vector3d.*;

/**
 *
 * @author Pan Piotr
 */
public final class ConvexCollidingShape implements CollidingShape {

    private final Vector2d[] mPoints;
    private final Vector2d[] mNormals;

    public ConvexCollidingShape(Vector2d[] points) {
        mPoints = Arrays.copyOf(points, points.length);
        mNormals = generateNormals();
    }

    public ConvexCollidingShape(Vector2d[] points, double order) {
        mPoints = Arrays.copyOf(points, points.length);
        mNormals = generateNormals(order);
    }

    public ConvexCollidingShape(Vector3d[] points, Matrix3x3d transformation) {
        mPoints = transformPoints(points, transformation);
        mNormals = generateNormals();
    }

    public ConvexCollidingShape(Vector2d[] points, Matrix3x3d transformation) {
        mPoints = transformPoints(points, transformation);
        mNormals = generateNormals();
    }

    public ConvexCollidingShape(Vector2d[] points, Matrix3x3d transformation, double order) {
        mPoints = transformPoints(points, transformation);
        mNormals = generateNormals(order);
    }

    public ConvexCollidingShape(Vector3d[] points, Matrix3x3d transformation, double order) {
        mPoints = transformPoints(points, transformation);
        mNormals = generateNormals(order);
    }

    /**
     * Checks whether points are in clockwise order or counter clockwise order
     *
     * @param points
     * @return +1 for clockwise -1 for counter clockwise
     */
    protected double checkPointsOrder(Vector2d[] points) {
        double area = 0;
        for (int i = 0; i < points.length; i++) {
            Vector2d f = points[i];
            Vector2d s = points[(i + 1) % points.length];
            area += (s.x - f.x) * (s.y - f.y);
        }
        return signum(area);
    }

    protected Vector2d countSingleNormal(Vector2d segment, double order) {
        double length = length(segment);
        if (order > 0) { //clockwise
            return new Vector2d(-segment.y / length, segment.x / length);
        } else { //counter
            return new Vector2d(segment.y / length, -segment.x / length);
        }
    }

    protected Vector2d[] transformPoints(Vector2d[] points, Matrix3x3d transformation) {
        Vector2d[] ret = new Vector2d[points.length];
        int i = 0;
        for (Vector2d point : points) {
            ret[i] = doOneTransformation(toHomogenousVector(point), transformation);
            i++;
        }
        return ret;
    }

    protected Vector2d doOneTransformation(Vector3d point, Matrix3x3d transformation) {
        return fromHomogeneousVector(matVecMul(transformation, point));
    }

    protected Vector2d[] transformPoints(Vector3d[] points, Matrix3x3d transformation) {
        Vector2d[] ret = new Vector2d[points.length];
        int i = 0;
        for (Vector3d point : points) {
            ret[i] = doOneTransformation(point, transformation);
            i++;
        }
        return ret;
    }

    protected Vector2d[] generateNormals(double order) {

        Vector2d[] normals = new Vector2d[mPoints.length];
        for (int i = 0; i < mPoints.length; i++) {
            Vector2d t = sub(mPoints[i], mPoints[(i + 1) % mPoints.length]);
            normals[i] = countSingleNormal(t, order);
        }
        return normals;
    }

    protected Vector2d[] generateNormals() {
        return generateNormals(checkPointsOrder(mPoints));
    }

}

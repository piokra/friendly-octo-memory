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
            area += f.x * s.y - s.x * f.y;
        }
        return -signum(area);
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
            Vector2d t = sub(mPoints[(i + 1) % mPoints.length], mPoints[i]);
            normals[i] = countSingleNormal(t, order);
        }
        return normals;
    }

    protected Vector2d[] generateNormals() {
        return generateNormals(checkPointsOrder(mPoints));
    }

    protected boolean checkMinMaxAlongNormal(ConvexCollidingShape ccs, Vector2d normal) {

        double mino = Double.POSITIVE_INFINITY, maxo = Double.NEGATIVE_INFINITY;
        double mint = mino, maxt = maxo;

        for (Vector2d point : mPoints) {
            double ccast = dot(point, normal);
            mint = (mint > ccast) ? ccast : mint;
            maxt = (maxt > ccast) ? maxt : ccast;
        }
        for (Vector2d point : ccs.mPoints) {
            double ccast = dot(point, normal);
            mino = (mino > ccast) ? ccast : mino;
            maxo = (maxo > ccast) ? maxo : ccast;
        }

        if ((maxt >= mino) && (mint <= maxo)) {
            return true;
        }
        if ((maxo >= mint) && (mino <= maxt)) {
            return true;
        }
        return false;

    }

    protected boolean checkSinglePointAlongNormal(Vector2d point, Vector2d normal, Vector2d lineOrigin) {
        double b = dot(normal, lineOrigin);
        double d = dot(normal, point);
        return (b >= d);
    }

    public boolean collides(ConvexCollidingShape ccs) {
        for (Vector2d normal : mNormals) {
            if (!checkMinMaxAlongNormal(ccs, normal)) {
                return false;
            }
        }
        return true;
    }

    public boolean collides(Vector2d point) {
        for (int i = 0; i < mNormals.length; i++) {
            Vector2d normal = mNormals[i];
            Vector2d origin = mPoints[i];
            if (!checkSinglePointAlongNormal(point, normal, origin)) {
                return false;
            }
        }
        return true;
    }

    protected double getFarthestDistanceFromNormal(ConvexCollidingShape ccs, Vector2d normal, Vector2d lineOrigin) {
        double b = dot(normal, lineOrigin);
        double min = Double.MAX_VALUE;
        for (Vector2d point : ccs.mPoints) {
            double distance = dot(point, normal) - b;
            min = (min > distance) ? distance : min;
        }
        return -min;
    }

    public Vector2d countMinimumDistanceNormal(ConvexCollidingShape ccs) {
        double min = Double.MAX_VALUE;
        Vector2d result = Vector2d.VECTOR_ZERO;
        for (int i = 0; i < mNormals.length; i++) {
            Vector2d normal = mNormals[i];
            Vector2d origin = mPoints[i];
            double distance = getFarthestDistanceFromNormal(ccs, normal, origin);
            if(distance < min) {
                result = normal;
                min=distance;
            }
        }
        return result;
    }
}

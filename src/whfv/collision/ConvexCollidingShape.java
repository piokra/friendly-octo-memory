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
package whfv.collision;

import java.util.Arrays;
import whfv.utill.Vector2d;
import static whfv.utill.Vector2d.*;
import whfv.utill.Matrix3x3d;
import static whfv.utill.Matrix3x3d.*;
import static java.lang.Math.*;
import org.jsfml.graphics.ConvexShape;
import org.jsfml.system.Vector2f;
import whfv.utill.Matrix2x2d;
import whfv.utill.Rect2D;
import whfv.utill.Vector3d;
import static whfv.utill.Vector3d.*;

/**
 *
 * @author Pan Piotr
 */
public final class ConvexCollidingShape implements CollidingShape, Comparable<ConvexCollidingShape> {

    protected static final double BOUNDING_RECT_EPSILON = 0.02;
    private final Vector3d[] mStartingPoints; //TODO replace creating new ccs with transforming old ones where possible
    private Vector2d[] mPoints;
    private Vector2d[] mNormals;
    private Rect2D mBoundingRectangle;
    private final double mOrder;
    private Matrix3x3d mTransform = Matrix3x3d.IDENTITY;

    public ConvexCollidingShape(Vector2d[] points) {
        mOrder = checkPointsOrder(points);

        mStartingPoints = new Vector3d[points.length];
        for (int i = 0; i < points.length; i++) {
            Vector2d point = points[i];
            mStartingPoints[i] = new Vector3d(point.x, point.y, 1);

        }
        mPoints = Arrays.copyOf(points, points.length);
        mNormals = generateNormals();
        mBoundingRectangle = countBoundingRectangle();
    }

    public ConvexCollidingShape(Vector2d[] points, double order) {
        mOrder = order;
        mStartingPoints = new Vector3d[points.length];
        for (int i = 0; i < points.length; i++) {
            Vector2d point = points[i];
            mStartingPoints[i] = new Vector3d(point.x, point.y, 1);

        }
        mPoints = Arrays.copyOf(points, points.length);
        mNormals = generateNormals(order);
        mBoundingRectangle = countBoundingRectangle();
    }

    public ConvexCollidingShape(Vector3d[] points, Matrix3x3d transformation) {

        mStartingPoints = Arrays.copyOf(points, points.length);
        mPoints = transformPoints(points, transformation);
        mOrder = checkPointsOrder(mPoints);
        mNormals = generateNormals();
        mBoundingRectangle = countBoundingRectangle();
    }

    public ConvexCollidingShape(Vector2d[] points, Matrix3x3d transformation) {
        mOrder = checkPointsOrder(points);
        mStartingPoints = new Vector3d[points.length];
        for (int i = 0; i < points.length; i++) {
            Vector2d point = points[i];
            mStartingPoints[i] = new Vector3d(point.x, point.y, 1);

        }
        mPoints = transformPoints(points, transformation);
        mNormals = generateNormals();
        mBoundingRectangle = countBoundingRectangle();
    }

    public ConvexCollidingShape(Vector2d[] points, Matrix3x3d transformation, double order) {
        mOrder = order;
        mStartingPoints = new Vector3d[points.length];
        for (int i = 0; i < points.length; i++) {
            Vector2d point = points[i];
            mStartingPoints[i] = new Vector3d(point.x, point.y, 1);

        }
        mPoints = transformPoints(points, transformation);
        mNormals = generateNormals(order);
        mBoundingRectangle = countBoundingRectangle();
    }

    public ConvexCollidingShape(Vector3d[] points, Matrix3x3d transformation, double order) {
        mOrder = order;
        mStartingPoints = Arrays.copyOf(points, points.length);
        mPoints = transformPoints(points, transformation);
        mNormals = generateNormals(order);
        mBoundingRectangle = countBoundingRectangle();
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
            if (distance < min) {
                result = normal;
                min = distance;
            }
        }
        return result;
    }

    public ConvexShape toJSFMLConvexShape() {
        Vector2f[] points = new Vector2f[mPoints.length];
        int i = 0;
        for (Vector2d mPoint : mPoints) {
            points[i] = new Vector2f((float) mPoint.x, (float) mPoint.y);
            i++;
        }

        return new ConvexShape(points);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Vector2d mPoint : mPoints) {
            sb.append(mPoint);
        }
        return sb.toString();
    }

    @Override
    public int compareTo(ConvexCollidingShape o) {
        if (o == this) {
            return 0;
        }
        if (o.hashCode() > hashCode()) {
            return 1;
        }
        return -1;
    }

    public Rect2D getBoundingRectangle() {
        return mBoundingRectangle;
    }

    protected Rect2D countBoundingRectangle() {
        double maxX = Double.NEGATIVE_INFINITY, maxY = Double.NEGATIVE_INFINITY;
        double minX = Double.POSITIVE_INFINITY, minY = Double.POSITIVE_INFINITY;

        for (Vector2d mPoint : mPoints) {
            maxX = Math.max(maxX, mPoint.x);
            maxY = Math.max(maxY, mPoint.y);
            minX = Math.min(minX, mPoint.x);
            minY = Math.min(minY, mPoint.y);
        }
        double lengthX = maxX - minX;
        lengthX *= BOUNDING_RECT_EPSILON;
        double lengthY = maxY - minY;
        lengthY *= BOUNDING_RECT_EPSILON;
        return new Rect2D(new Vector2d(minX - lengthX, minY - lengthY), new Vector2d(maxX + lengthX, maxY + lengthY));
    }

    public Vector2d[] getPoints() {
        return mPoints;
    }

    @Override
    public void transform(Matrix3x3d homoTransformation) {
        mTransform = homoTransformation;
        mPoints = transformPoints(mStartingPoints, homoTransformation);
        mNormals = generateNormals(mOrder);
        mBoundingRectangle = countBoundingRectangle();
    }



    @Override
    public Matrix3x3d getTransform() {
        return mTransform;
    }
}

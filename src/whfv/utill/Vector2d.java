/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package whfv.utill;

/**
 *
 * @author Pan Piotr
 */
public final class Vector2d {

    public static final Vector2d VECTOR_ZERO = new Vector2d(0, 0);

    public final double x;
    public final double y;

    public Vector2d(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public static final Vector2d mul(double l, Vector2d r) {
        return new Vector2d(r.x * l, r.y * l);
    }

    public static final Vector2d mul(Vector2d l, double r) {
        return mul(r, l);
    }

    public static final Vector2d sub(Vector2d l, Vector2d r) {
        return new Vector2d(l.x - r.x, l.y - r.y);
    }

    public static final Vector2d neg(Vector2d l) {
        return new Vector2d(-l.x, -l.y);
    }

    public static final Vector2d add(Vector2d l, Vector2d r) {
        return new Vector2d(l.x + r.x, l.y + r.y);
    }

    public static final double dot(Vector2d l, Vector2d r) {
        return l.x * r.x + l.y * r.y;
    }

    public final static Vector2d componentwiseMul(Vector2d l, Vector2d r) {
        return new Vector2d(l.x * r.x, l.y * r.y);
    }

    public static final Vector3d toHomogenousVector(Vector2d vec) {
        return new Vector3d(vec.x, vec.y, 1);
    }

    public static final double length(Vector2d v) {
        return Math.sqrt(dot(v, v));
    }

    public static final Vector2d normalized(Vector2d v) {
        double l = length(v);
        if(l < 10e-6) return Vector2d.VECTOR_ZERO;
        return mul(1 / l, v);
    }

    public static Vector2d componentwiseMax(Vector2d l, Vector2d r) {
        return new Vector2d(Math.max(l.x, r.x), Math.max(l.y, r.y));

    }

    public static Vector2d componentwiseMin(Vector2d l, Vector2d r) {
        return new Vector2d(Math.min(l.x, r.x), Math.min(l.y, r.y));

    }

    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        if (!(o instanceof Vector2d)) {
            return false;
        }
        Vector2d v = (Vector2d) o;
        return (Math.abs(x - v.x) + Math.abs(y - v.y)) < 10e-12;
    }

    @Override
    public String toString() {
        return "Vector2d{" + x + "," + y + "}";
    }

    Vector2d transform(Matrix3x3d mat) {
        return Vector3d.fromHomogeneousVector(Matrix3x3d.matVecMul(mat, Vector2d.toHomogenousVector(this)));
    }

    Vector2d transform(Matrix2x2d mat) {
        return Matrix2x2d.matVecMul(mat, this);
    }
}

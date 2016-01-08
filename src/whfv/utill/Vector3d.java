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
public final class Vector3d {

    public static final Vector3d VECTOR_ZERO = new Vector3d(0,0,0);
    
    public final double x;
    public final double y;
    public final double z;

    public Vector3d(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public static final Vector3d mul(double l, Vector3d r) {
        return new Vector3d(r.x * l, r.y * l, r.z * l);
    }

    public static final Vector3d mul(Vector3d l, double r) {
        return mul(r, l);
    }

    public static final Vector3d sub(Vector3d l, Vector3d r) {
        return new Vector3d(l.x - r.x, l.y - r.y, l.z - r.z);
    }

    public static final Vector3d neg(Vector3d l) {
        return new Vector3d(-l.x, -l.y, -l.z);
    }

    public static final Vector3d add(Vector3d l, Vector3d r) {
        return new Vector3d(l.x + r.x, l.y + r.y, l.z + r.z);
    }

    public static final double dot(Vector3d l, Vector3d r) {
        return l.x * r.x + l.y * r.y + l.z * r.z;
    }

    public static final Vector2d fromHomogeneousVector(Vector3d vec) {
        return new Vector2d(vec.x / vec.z, vec.y / vec.z);
    }
        public static double length(Vector3d v) {
        return Math.sqrt(dot(v,v));
    }
    public static Vector3d normalized(Vector3d v) {
        return mul(1/length(v),v);
    }
}

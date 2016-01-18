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

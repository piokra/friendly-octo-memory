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
public class Rect2D {

    public enum Corner {
        MinXMinY,
        MaxXMinY,
        MinXMaxY,
        MaxXMaxY
    }
    public final Vector2d minCorner;
    public final Vector2d maxCorner;

    public Rect2D(Vector2d first, Vector2d second) {
        if ((second == null) || (first == null)) {
            throw new NullPointerException("Cannot init rectangle with null corners");
        }

        minCorner = new Vector2d(Math.min(first.x, second.x), Math.min(first.y, second.y));
        maxCorner = new Vector2d(Math.max(first.x, second.x), Math.max(first.y, second.y));
    }

    public Vector2d getCorner(Corner corner) {
        if (corner == Corner.MinXMinY) {
            return minCorner;
        }
        if (corner == Corner.MaxXMaxY) {
            return maxCorner;
        }
        if (corner == Corner.MaxXMinY) {
            return new Vector2d(maxCorner.x, minCorner.y);
        }
        if (corner == Corner.MinXMaxY) {
            return new Vector2d(minCorner.x, maxCorner.y);
        }
        return null; // this will never trigger
    }

    public Vector2d getMidPoint() {
        return Vector2d.mul(0.5, Vector2d.add(minCorner, maxCorner));
    }

    public double area() {
        return (maxCorner.x - minCorner.x) * (maxCorner.y - minCorner.y);
    }

    public boolean fits(Vector2d p) {
        return ((p.x >= minCorner.x) && (p.y >= minCorner.y) && (p.x <= maxCorner.x) && (p.y <= maxCorner.y));
    }

    public boolean fits(Rect2D r) {
        return fits(r.maxCorner) && fits(r.minCorner);
    }

    /**
     * This functions splits a rectangle into up to four rectangles based on
     * where the p is located
     *
     * @param p point that will become one of the corners
     * @return array of rectangle on success, null if p is invalid
     */
    public Rect2D[] split(Vector2d p) {
        if (!fits(p)) {
            return null;
        }
        Rect2D[] arr = new Rect2D[4];
        int i = 0;
        for (Corner value : Corner.values()) {
            arr[i] = new Rect2D(getCorner(value), p);
            i++;
        }
        i = 0;
        for (Rect2D rect2D : arr) {
            if (rect2D.area() > 0) {
                i++;
            }
        }
        Rect2D[] ret = new Rect2D[i];
        i = 0;
        for (Rect2D rect2D : ret) {
            if (rect2D.area() > 0) {
                ret[i] = rect2D;
                i++;
            }
        }
        return ret;
    }

}

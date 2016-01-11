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
public class Segment2D {

    public final Vector2d start;
    public final Vector2d end;

    public Segment2D(Vector2d start, Vector2d end) {
        if ((start == null) && (end == null)) {
            throw new NullPointerException("Cannot init segment with null values");
        }
        this.start = start;
        this.end = end;
    }

}

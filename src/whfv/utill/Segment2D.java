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

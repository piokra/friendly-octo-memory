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
package whfv.position;

import whfv.utill.Transformable;
import whfv.utill.Vector2d;

/**
 *
 * @author Pan Piotr
 * 
 */
public interface Position extends Transformable {
    
    Vector2d getPosition();
    Vector2d getCoordinates();
    void move(Vector2d d);
    
    default void changePosition(Vector2d pos) {
        move(Vector2d.sub(pos, getPosition()));
    };
}

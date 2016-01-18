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
package whfv;

import org.jsfml.window.event.Event;
import org.jsfml.window.event.Event.Type;
import org.jsfml.window.event.MouseEvent;
import whfv.utill.Vector2d;

/**
 *
 * @author Pan Piotr
 */
public interface MouseHoverable extends MouseEventProcessor{
    
    default boolean checkMouseMovement(MouseEvent e) {
        if(e.type == Type.MOUSE_MOVED) {
            Vector2d mouse = new Vector2d(e.position.x, e.position.y);
            if(getMouseCollidingShape().collides(mouse)) {
                if(!isMouseOver()) {
                    onMouseOver();
                    return true;
                }
            } else {
                if(isMouseOver()) {
                    onMouseOff();
                    return true;
                }
            }
        }
        return false;
    }
    
    boolean isMouseOver();
    void onMouseOver();
    void onMouseOff();
}

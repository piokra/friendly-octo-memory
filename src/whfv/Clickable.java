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

import org.jsfml.window.Mouse;
import org.jsfml.window.Mouse.Button;
import org.jsfml.window.event.Event;
import org.jsfml.window.event.Event.Type;
import org.jsfml.window.event.MouseButtonEvent;
import org.jsfml.window.event.MouseEvent;
import whfv.utill.Vector2d;

/**
 *
 * @author Pan Piotr
 */
public interface Clickable extends MouseEventProcessor {
    
    default boolean checkClicked(MouseButtonEvent e) {
        Vector2d click = new Vector2d(e.position.x,e.position.y);
        if(getMouseCollidingShape().collides(click)) {
            if(e.type == Type.MOUSE_BUTTON_PRESSED) {
                if(e.button == Button.LEFT) {
                    onLeftClick();
                    return true;
                } else if (e.button == Button.RIGHT) {
                    onRightClick();
                    return true;
                }
            }
        }
        return false;
    }
    
    void onLeftClick();
    void onRightClick();
    
}

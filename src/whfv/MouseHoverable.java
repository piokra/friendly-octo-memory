/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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

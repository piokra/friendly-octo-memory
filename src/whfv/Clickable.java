/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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

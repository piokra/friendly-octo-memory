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
package whfv.hotkeys;

import org.jsfml.window.event.KeyEvent;

import org.jsfml.window.Keyboard;
import org.jsfml.window.event.Event;

public class Hotkey {

    private final boolean mAlt;
    private final boolean mShift;
    private final boolean mControl;
    private final Keyboard.Key mKey;
    private final boolean mDown;

    public Hotkey(Keyboard.Key key, boolean down, boolean alt, boolean shift, boolean control) {
        mAlt = alt;
        mShift = shift;
        mControl = control;
        mKey = key;
        mDown = down;
    }

    public Hotkey(Keyboard.Key key, boolean down) {
        mKey = key;
        mDown = down;
        mAlt = false;
        mShift = false;
        mControl = false;
    }

    boolean matches(KeyEvent e) {
        if((e.type == Event.Type.KEY_PRESSED)!=mDown) {
            return false;
        }
        if (e.alt != mAlt) {
            return false;
        }
        if (e.shift != mShift) {
            return false;
        }
        if (e.control != mControl) {
            return false;
        }
        if (e.key != mKey) {
            return false;
        }
        return true;
    }

}

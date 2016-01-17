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

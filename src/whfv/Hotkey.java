package whfv;

import org.jsfml.window.event.KeyEvent;

import org.jsfml.window.Keyboard;

public class Hotkey {
	
	private final boolean mAlt;
	private final boolean mShift;
	private final boolean mControl;
	private final Keyboard.Key mKey;
	public Hotkey(Keyboard.Key key, boolean alt, boolean shift, boolean control) {
		mAlt=alt;
		mShift=shift;
		mControl = control;
		mKey = key;
	}
	public Hotkey(Keyboard.Key key) {
		mKey=key;
		mAlt = false;
		mShift = false;
		mControl = false;
	}
	boolean matches(KeyEvent e) {
		if(e.alt != mAlt) return false;
		if(e.shift != mShift) return false;
		if(e.control != mControl) return false;
		if(e.key != mKey) return false;
		return true;
	}
	
}

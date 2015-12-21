package whfv;

import java.util.concurrent.ConcurrentLinkedQueue;

import org.jsfml.graphics.RenderStates;
import org.jsfml.graphics.RenderTarget;
import org.jsfml.system.Vector2i;
import org.jsfml.window.Keyboard;
import org.jsfml.window.event.Event;

public class Console implements Drawable, Focusable, EventProcessor {

	private final int mStringCount;
	private final Vector2i mConsolePosition;
	private final int mConsoleWidth;
	private final ConcurrentLinkedQueue<String> mStrings;
	public Console(int stringCount, Vector2i position, int width) {
		mStrings = new ConcurrentLinkedQueue<String>();
		mStringCount = stringCount;
		mConsolePosition = position;
	}
	@Override
	public void draw(RenderTarget target, RenderStates states) {
		// TODO Auto-generated method stub

	}
	private boolean mHidden = true;
	public void toggleConsole()
	{
			mHidden = !mHidden;
	}
	private boolean mFocused = false;
	private String mTempString = "";
	@Override
	public Event processEvent(Event e) {
		if(isFocused()) {
			if(e.type == Event.Type.KEY_PRESSED) {
				if(e.asKeyEvent().key == Keyboard.Key.RETURN) {
					
				}
			}
		}
		return e;
	}
	@Override
	public boolean isFocused() {
		return mFocused;
	}
	@Override
	public void toggleFocus() {
		mFocused = !mFocused;
		
	}
	@Override
	public void unfocus() {
		mFocused = false;
	}
	@Override
	public void focus() {
		mFocus
	}
	
}

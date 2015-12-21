package whfv;

import java.util.concurrent.ConcurrentLinkedQueue;

import org.jsfml.graphics.RenderStates;
import org.jsfml.graphics.RenderTarget;
import org.jsfml.system.Vector2i;

public class Console implements Drawable {

	private final ConcurrentLinkedQueue<String> mStrings;
	public Console(int queueSize, Vector2i position, Vector2i consoleSize) {
		
	}
	@Override
	public void draw(RenderTarget target, RenderStates states) {
		// TODO Auto-generated method stub

	}

}

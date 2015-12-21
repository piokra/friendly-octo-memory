package whfv;

import java.util.Collection;

import org.jsfml.graphics.RenderStates;
import org.jsfml.graphics.RenderTarget;

public interface Drawable extends org.jsfml.graphics.Drawable {
	static void drawThem(RenderTarget t, RenderStates s, Collection<? extends Drawable> collection) {
		for(Drawable d : collection) {
			t.draw(d,s);
		}
			
	}
}

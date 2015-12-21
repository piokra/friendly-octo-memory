package whfv;

import org.jsfml.graphics.RenderWindow;
import org.jsfml.window.Keyboard;
import org.jsfml.window.VideoMode;
import org.jsfml.window.event.Event;
import org.jsfml.window.event.Event.Type;

public class WHFV {

	public static void main(String[] args) {
		RenderWindow rw = new RenderWindow();
		rw.create(new VideoMode(1920,1080), "yoloylo", RenderWindow.FULLSCREEN);
		while (rw.isOpen()) {
			
			for (Event e : rw.pollEvents()) {
				if(e.type ==  Type.CLOSED) {
					rw.close();
				}
				if(e.type == Type.KEY_PRESSED)
				{
					if(e.asKeyEvent().key == Keyboard.Key.ESCAPE)
						rw.close();
				}
			}
			
		}
	}

}

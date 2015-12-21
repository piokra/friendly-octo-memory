package whfv;

import java.util.ArrayList;

import org.jsfml.graphics.RenderStates;
import org.jsfml.graphics.RenderTarget;

public class World implements Drawable, Processable {

	private final ArrayList<Drawable> mDrawables = new ArrayList<>();
	private final ArrayList<Processable> mProcessables = new ArrayList<>();
	
	@Override
	public void draw(RenderTarget target, RenderStates states) {
		
		Drawable.drawThem(target, states, mDrawables);
	}

	@Override
	public void process(double timestep) {
		Processable.processThem(timestep, mProcessables);
		
	}

}

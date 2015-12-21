package whfv;

import java.util.Collection;

public interface Processable {
	void process(double timestep);
	static void processThem(double timestep, Collection<? extends Processable> collection) {
		for(Processable p : collection ) {
			p.process(timestep);
		}
	}
}

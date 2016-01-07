package whfv;

import java.util.ArrayList;

import org.jsfml.window.event.Event;

import whfv.utill.Pair;

public class HotkeyProcessor implements EventProcessor {

	private final ArrayList<Pair<Hotkey,HotkeyTask>> mHotkeyed = new ArrayList<>();
	private final ArrayList<HotkeyTask> mUnhotkeyed = new ArrayList<>();
	@Override
	public Event processEvent(Event e) {
		if(e.type == Event.Type.KEY_PRESSED) {
			for (Pair<Hotkey,HotkeyTask> h : mHotkeyed) {
				if(h.first.matches(e.asKeyEvent())) {
					h.second.doTask(e.asKeyEvent());
					return null;
				
				}
			}
		}
		
		return e;
	}
	//TODO add a way to assign hotkeys to tasks
	public boolean addTask(HotkeyTask task) {
		if(task == null) return false;
		mUnhotkeyed.add(task);
		return true;
	}
	public boolean addHotkeyedTask(Hotkey hotkey, HotkeyTask task) {
		if(hotkey==null) return false;
		if(task==null) return false;
		mHotkeyed.add(new Pair<>(hotkey,task));
		return true;
	}

}

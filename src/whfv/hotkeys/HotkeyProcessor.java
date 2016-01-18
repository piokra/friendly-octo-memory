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

import java.util.ArrayList;

import org.jsfml.window.event.Event;
import whfv.EventProcessor;

import whfv.utill.Pair;

public class HotkeyProcessor implements EventProcessor {

    private final ArrayList<Pair<Hotkey, HotkeyTask>> mHotkeyed = new ArrayList<>();
    private final ArrayList<HotkeyTask> mUnhotkeyed = new ArrayList<>();

    @Override
    public Event processEvent(Event e) {
        if (e.type == Event.Type.KEY_PRESSED) {
            for (Pair<Hotkey, HotkeyTask> h : mHotkeyed) {
                if (h.first.matches(e.asKeyEvent())) {
                    h.second.doTask(e.asKeyEvent());
                    return null;

                }
            }
        } else if (e.type == Event.Type.KEY_RELEASED) {
            for (Pair<Hotkey, HotkeyTask> h : mHotkeyed) {
                if (h.first.matches(e.asKeyEvent())) {
                    h.second.doTask(e.asKeyEvent());
                    return null;

                }
            }
        }

        return e;
    }
    //TODO add a way to assign hotkeys to tasks

    public boolean addTask(HotkeyTask task) {
        if (task == null) {
            return false;
        }
        mUnhotkeyed.add(task);
        return true;
    }

    public boolean addHotkeyedTask(Hotkey hotkey, HotkeyTask task) {
        if (hotkey == null) {
            return false;
        }
        if (task == null) {
            return false;
        }
        mHotkeyed.add(new Pair<>(hotkey, task));
        return true;
    }

}

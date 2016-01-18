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
package whfv;

import java.util.LinkedList;
import org.jsfml.window.event.Event;

/**
 *
 * @author Uzytkownik
 */
public class EventProcessors implements EventProcessor {

    private final LinkedList<EventProcessor> mEventProcessors = new LinkedList<>();
    @Override
    public Event processEvent(Event e) {
        for(EventProcessor ep : mEventProcessors) {
            if(e==null) return null;
            e = ep.processEvent(e);
        }
        return e;
    }
    public boolean tryAdding(EventProcessor e) {
        if(e==null) return false;
        if(mEventProcessors.contains(e)) return false;
        mEventProcessors.add(e);
        return true;
    }
    public boolean tryRemoving(EventProcessor e) {
        if(mEventProcessors.contains(e)) {
            mEventProcessors.remove(e);
            return true;
        }
        return false;
    }
    
}

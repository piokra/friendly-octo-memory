/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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

package util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Organizer {
    private ArrayList<Event> events;

    public Organizer() {
        this.events = new ArrayList<>();
    }

    public void addEvent(Event event) {
        events.add(event);
    }

    public void removeEvent(Event event) {
        events.remove(event);
    }

    public List<Event> getEvents() {
        return Collections.unmodifiableList(events);
    }
}

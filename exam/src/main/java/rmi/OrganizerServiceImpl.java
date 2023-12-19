package rmi;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;

import util.Event;


public class OrganizerServiceImpl extends UnicastRemoteObject implements OrganizerService {
    private List<Event> events;

    public OrganizerServiceImpl() throws RemoteException {
        super();
        this.events = new ArrayList<>();
    }

    @Override
    public synchronized void addEvent(Event event) throws RemoteException {
        events.add(event);
    }

    @Override
    public synchronized void removeEvent(String eventName) throws RemoteException {
        events.removeIf(event -> event.getName().equals(eventName));
    }

    @Override
    public synchronized List<Event> getEvents() throws RemoteException {
        return new ArrayList<>(events);
    }

    @Override
    public synchronized List<Event> getEventsByDate(Date date) throws RemoteException {
        List<Event> eventsOnDate = new ArrayList<>();

        for (Event event : events) {
            if (event.getStartDate().equals(date) || (event.getRepeat() != null && event.getRepeat().equals("daily"))) {
                eventsOnDate.add(event);
            }
        }

        return eventsOnDate;
    }

    @Override
    public synchronized List<Event> getEventsByCategory(String category) throws RemoteException {
        List<Event> eventsInCategory = new ArrayList<>();

        for (Event event : events) {
            if (event.getCategory().equals(category)) {
                eventsInCategory.add(event);
            }
        }

        return eventsInCategory;
    }

    @Override
    public synchronized List<Event> getUpcomingEvents(Date currentDate, int numEvents) throws RemoteException {
        List<Event> upcomingEvents = new ArrayList<>();

        for (Event event : events) {
            if (event.getStartDate().after(currentDate) || (event.getRepeat() != null && event.getRepeat().equals("daily"))) {
                upcomingEvents.add(event);
            }
        }

        Collections.sort(upcomingEvents, Comparator.comparing(Event::getStartDate));

        if (upcomingEvents.size() > numEvents) {
            upcomingEvents = upcomingEvents.subList(0, numEvents);
        }

        return upcomingEvents;
    }
}
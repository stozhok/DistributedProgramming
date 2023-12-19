package rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Date;
import java.util.List;
import util.Organizer;
import util.Event;

public interface OrganizerService extends Remote {
    void addEvent(Event event) throws RemoteException;
    void removeEvent(String eventName) throws RemoteException;
    List<Event> getEvents() throws RemoteException;
    List<Event> getEventsByDate(Date date) throws RemoteException;
    List<Event> getEventsByCategory(String category) throws RemoteException;
    List<Event> getUpcomingEvents(Date currentDate, int numEvents) throws RemoteException;
}

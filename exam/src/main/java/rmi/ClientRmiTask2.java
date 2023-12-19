package rmi;

import util.Organizer;
import util.Event;

import java.util.Date;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;

public class ClientRmiTask2 {
    public static void main(String[] args) {
        try {
            Registry registry = LocateRegistry.getRegistry("localhost", 1099);
            OrganizerService organizerService = (OrganizerService) registry.lookup("OrganizerService");

            List<Event> events = organizerService.getEvents();
            logEvents(events);

            Event newEvent = new Event("New Event", new Date(), new Date(), "Category", "Description", null);
            organizerService.addEvent(newEvent);

            organizerService.removeEvent("New Event");

            events = organizerService.getEvents();
            logEvents(events);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void logEvents(List<Event> events) {
        if (events.isEmpty()) {
            System.out.println("No events!");
        } else {
            for (Event event : events) {
                System.out.println(event);
            }
        }
    }}

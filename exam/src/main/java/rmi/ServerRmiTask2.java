package rmi;

import util.Organizer;

import java.util.List;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class ServerRmiTask2 {
    public static void main(String[] args) {
        try {
            OrganizerService organizerService = new OrganizerServiceImpl();
            Registry registry = LocateRegistry.createRegistry(1099);
            registry.rebind("OrganizerService", organizerService);
            System.out.println("Organizer Server is running...");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


package socket;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import util.Event;
import java.util.Date;

public class ClientSocketTask2 {
    public static void main(String[] args) {
        try (Socket socket = new Socket("localhost", 12345);
             ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream())) {

            Event eventToSend = new Event("Meeting", new Date(), new Date(), "Work", "Team meeting", null);
            outputStream.writeObject(eventToSend);

            String confirmation = (String) inputStream.readObject();
            System.out.println("Server response: " + confirmation);

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}

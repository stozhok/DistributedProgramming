package socket;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import util.Event;

public class ServerSocketTask2 {
    private static final int THREAD_POOL_SIZE = 10;
    private static ExecutorService executorService = Executors.newFixedThreadPool(THREAD_POOL_SIZE);

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(12345)) {
            System.out.println("Server is running...");

            while (true) {
                try {
                    Socket socket = serverSocket.accept();
                    executorService.submit(new ClientHandler(socket));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            executorService.shutdown();
        }
    }

    private static class ClientHandler implements Runnable {
        private Socket socket;

        public ClientHandler(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try (ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
                 ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream())) {

                Event receivedEvent = (Event) inputStream.readObject();
                System.out.println("Received event: " + receivedEvent);

                outputStream.writeObject("Event received successfully");
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}

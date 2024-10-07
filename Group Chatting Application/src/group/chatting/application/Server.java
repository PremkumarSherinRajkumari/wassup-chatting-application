// Server.java
package group.chatting.application;

import java.net.*;
import java.io.*;
import java.util.*;

public class Server implements Runnable {

    Socket socket;

    // Vector to store client writers for broadcasting messages
    public static Vector client = new Vector();

    // Constructor to initialize the socket
    public Server(Socket socket) {
        try {
            this.socket = socket;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Run method to handle client communication
    public void run() {
        try {
            // Setting up input and output streams for communication
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

            // Adding the current client's writer to the Vector
            client.add(writer);

            // Continuous loop to read and broadcast messages
            while (true) {
                // Reading a message from the current client
                String data = reader.readLine().trim();
                System.out.println("Received " + data);

                // Broadcasting the message to all clients
                for (int i = 0; i < client.size(); i++) {
                    try {
                        // Getting the writer for the current client
                        BufferedWriter bufferWriter = (BufferedWriter) client.get(i);
                        // Writing the message to the client's writer
                        bufferWriter.write(data);
                        bufferWriter.write("\r\n");
                        bufferWriter.flush();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Main method to start the server
    public static void main(String[] args) throws Exception {
        // Creating a server socket on port 2003
        ServerSocket s = new ServerSocket(2003);
        while (true) {
            // Accepting a new client connection
            Socket socket = s.accept();
            // Creating a new Server instance for the client
            Server server = new Server(socket);
            // Creating a new thread for the server instance and start it
            Thread thread = new Thread(server);
            thread.start();
        }
    }
}

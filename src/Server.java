/**
 * This program demonstrates a simple TCP/IP socket server.
 * The server accepts a single client at a time and the client sends messages to the server.
 * The server prints the received messages and can be terminated.
 * This is a single-threaded client-server communication (see program).
 */
import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
public class Server {
    // initialization of socket and input stream
    private Socket socket = null;
    private ServerSocket serverSocket = null;
    private DataInputStream in = null;
    // implementation of constructor
    public Server(int port) {
        // start server and wait for a connection
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("Server started!");
            System.out.println("Waiting for a client ......");
            socket = serverSocket.accept();
            System.out.println("Client accepted.");
            // take input from the client socket
            in = new DataInputStream(
                    new BufferedInputStream(socket.getInputStream()));
            // read msg from client till "Stop" is entered
            String line = "";
            while (!line.equals("Stop")) {
                try {
                    line = in.readUTF();
                    System.out.println(line);
                } catch (IOException e) {
                    System.out.println(e);
                }
            }
            // close connection
            socket.close();
            in.close();
            System.out.println("Connection terminated.");
        } catch (IOException e) {
            System.out.println(e);
        }
    }
    public static void main(String args[]) {
        Server serverSideTCP = new Server(2000);
    }
}
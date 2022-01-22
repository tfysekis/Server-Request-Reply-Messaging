/**
 * This program demonstrates a simple TCP/IP socket client application that connects to a server
 * to get some messages.
 */
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
public class Client {
    // initialization: socket, input & output streams
    private Socket socket = null;
    private DataInputStream input = null;
    private DataOutputStream output = null;
    // implementation of constructor
    public Client(String address, Integer port) {
        // do the connection
        try {
            socket = new Socket(address, port);
            input = new DataInputStream(System.in);
            // send output to the socket
            output = new DataOutputStream(socket.getOutputStream());
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // read msg from input
        String line = "";
        while (!(line.equals("Stop"))) {
            try {
                line = input.readLine();
                output.writeUTF(line);
            } catch (IOException e) {
                e.printStackTrace();}
        }
        // terminate the connection
        try {
            input.close();
            output.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) {
        Client client = new Client("127.0.0.1", 2000);
    }
}
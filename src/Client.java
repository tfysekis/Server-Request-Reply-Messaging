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
    private String username = null;
    private Account account = null;
    // implementation of constructor
    // do the connection
    public Client(String[] args) throws IOException {
        try {
            socket = new Socket(args[0], Integer.parseInt(args[1]));
            input = new DataInputStream(System.in);
            // send output to the socket
            output = new DataOutputStream(socket.getOutputStream());
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // sending username
        output.write(Integer.parseInt(args[2]));
        try {
            output.writeUTF(args[3]);
        } catch (IOException e) {
            e.printStackTrace();}



        //sending message
        /*
        try {
            Message message = new Message(input.readLine());
            output.writeUTF(message.body());
        }catch (IOException e) {
            e.printStackTrace();}
         */
        // terminate the connection after sending one message
        try {
            input.close();
            output.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws IOException {
        Client client = new Client(args);
    }
}




/**
 * This program demonstrates a simple TCP/IP socket client application that connects to a server
 * to get some messages.
 */
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class MessagingClient {
    // initialization: socket, input & output streams
    private Socket socket = null;
    private DataInputStream input = null;
    private DataOutputStream output = null;
    private String username = null;
    private Account account = null;
    // implementation of constructor
    public MessagingClient(String address, Integer port, String username) {
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
        try {
            line = input.readLine();
            Message message = new Message(line);
            Account account = new Account(username,port,message);
            setAccount(account);
            output.writeUTF(message.body());
        } catch (IOException e) {
            e.printStackTrace();}
        // terminate the connection
        try {
            input.close();
            output.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void setAccount(Account account){
        this.account = account;
    }
    public Account getAccount(Account account){
        return account;
    }
}
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
            input = new DataInputStream(socket.getInputStream());
            // send output to the socket
            output = new DataOutputStream(socket.getOutputStream());
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        int id = Integer.parseInt(args[2]);
        // sending id
        output.write(id);
        //first case
        if (id == 1){
            try {
                output.writeUTF(args[3]);
                int token;
                token = input.read();
                System.out.println(token);
            } catch (IOException e) {
                e.printStackTrace();}
        }/*else if (id == 2){
            int size = input.read();
            for (int i = 0; i < size; i++){
                String accounts = input.readUTF();
                System.out.println(accounts);
            }
        }else if (id == 3){
            String line = args[4];
            output.writeUTF(line);
            //output.writeUTF(args[3]);
        }*/
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




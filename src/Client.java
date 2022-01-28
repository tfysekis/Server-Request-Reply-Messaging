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
                String message;
                message = input.readUTF();
                System.out.println(message);
            } catch (IOException e) {
                e.printStackTrace();}
        }else if (id == 2) {
            output.write(Integer.parseInt(args[3]));
            if (input.readBoolean()){
                int size = input.read();
                for (int i = 0; i < size; i++) {
                    System.out.println(input.readUTF());
                }
            }else{
                System.out.println(input.readUTF());
            }
        }else if (id == 3){
            output.write(Integer.parseInt(args[3]));
            if(input.readBoolean()){
                output.writeUTF(args[4]);
                output.writeUTF(args[5]);
                System.out.println(input.readUTF());
            }else{
                System.out.println(input.readUTF());
            }
        }else if (id == 4){
            output.write(Integer.parseInt(args[3]));
            //check valid token
            if(input.readBoolean()){
                //inbox empty
                boolean inbox = input.readBoolean();
                if (inbox){
                    int sizeOfMessageBox = input.read();
                    System.out.println(sizeOfMessageBox + " sizeofbox");
                    for (int i = 0; i < sizeOfMessageBox; i++){
                        //print inbox
                        System.out.println(input.readUTF());
                    }
                }else{
                    //empty inbox
                    System.out.println(input.readUTF());
                }
            }else{
                //invalid token
                System.out.println(input.readUTF());
            }
        }else if (id == 5){
            output.write(Integer.parseInt(args[2]));
            //check valid token
            if (input.readBoolean()){
                //sending message id
                output.write(Integer.parseInt(args[4]));
                //print output
                System.out.println(input.readUTF());
            }else{
                //invalid token
                System.out.println(input.readUTF());
            }
        }else if (id == 6){
            //send token
            output.write(Integer.parseInt(args[3]));
            //check if token is valid
            if(input.readBoolean()){
                //send messageID
                output.write(Integer.parseInt(args[4]));
                //results
                System.out.println(input.readUTF());
            }else{
                //invalid token
                System.out.println(input.readUTF());
            }
        }
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
/**
 * This program demonstrates a simple TCP/IP socket server.
 * The server accepts a single client at a time and the client sends messages to the server.
 * The server prints the received messages and can be terminated.
 * This is a single-threaded client-server communication (see program).
 */
import org.w3c.dom.ls.LSOutput;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.Random;

public class Server {
    // initialization of socket and input stream
    private Socket socket = null;
    private ServerSocket serverSocket = null;
    private DataInputStream in = null;
    private static int authToken = 145;
    private static List<Account> accountList;
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

            //changing the token
            authToken++;

            // read msg from client
            int fn_id = in.read();
            if(fn_id == 1){
                try {
                    boolean flag = false;
                    String username = in.readUTF();
                    if (accountList.isEmpty()){
                        System.out.println("k");;
                    }else{
                        for (int i = 0; i < accountList.size(); i++){
                            if (username.equals(accountList.get(i).username())){
                                flag = true;
                            }
                        }
                    }

                } catch (IOException e) {
                    System.out.println(e);
                }
            }
            /*
            try{
                Message message = new Message(in.readUTF());
                //Account account = new Account(username,authToken);
                //accountList.add(account);
                System.out.println(message.body());
            } catch (IOException e) {
                System.out.println(e);
            }*/

            // close connection after sending one message
            socket.close();
            in.close();
            System.out.println("Connection terminated.");
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public static void main(String[] args) {
        Server server = new Server(Integer.parseInt(args[0]));
    }

}
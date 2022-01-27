/**
 * This program demonstrates a simple TCP/IP socket server.
 * The server accepts a single client at a time and the client sends messages to the server.
 * The server prints the received messages and can be terminated.
 * This is a single-threaded client-server communication (see program).
 */
import org.w3c.dom.ls.LSOutput;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.DoubleToIntFunction;

public class Server {
    // initialization of socket and input stream
    private Socket socket = null;
    private ServerSocket serverSocket = null;
    private DataInputStream in = null;
    private DataOutputStream out = null;
    private static int authToken = 100;
    private static List<Account> accountList = new ArrayList<Account>();
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
            out = new DataOutputStream(
                    new BufferedOutputStream(socket.getOutputStream()));


            //get id
            int fn_id;
            fn_id = in.read();
            if (fn_id == 1){
                try {
                    boolean flag = false;
                    //reading the username
                    String username = in.readUTF();
                    //Checking if the user exists
                    for (Account value : accountList) {
                        if (username.equals(value.username())) {
                            System.out.println("Sorry, the user already exists");
                            flag = true;
                            break;
                        }
                    }
                    if (!flag){
                        for (int i = 0; i < username.length(); i++){
                            char c = username.charAt(i);
                            if (!(c >= 'A' && c <= 'Z') && !(c >= 'a' && c <= 'z')) {
                                if (c != '_'){
                                    flag = true;
                                    break;
                                }
                            }
                        }
                        if (flag){
                            System.out.println("Invalid Username");
                        }else{
                            //creating the account
                            //changing token
                            authToken++;
                            int token = authToken;
                            Account account = new Account(username,authToken);
                            out.writeInt(token);
                            System.out.println(token);
                        }
                    }

                } catch (IOException e) {
                    System.out.println(e);
                }
            }/*else if (fn_id == 2){
                int counter = 0;
                out.write(accountList.size());
                for (Account account : accountList) {
                    counter++;
                    out.writeUTF(counter + ". " + account.username());
                }
            }else if (fn_id == 3){
                String line = in.readUTF();
                Message message = new Message(line);
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
            //out.close();
            System.out.println("Connection terminated.");
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public static void main(String[] args) {
        Server server = new Server(Integer.parseInt(args[0]));
    }

}
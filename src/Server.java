/**
 * This program demonstrates a simple TCP/IP socket server.
 * The server accepts a single client at a time and the client sends messages to the server.
 * The server prints the received messages and can be terminated.
 */

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {
    // initialization of socket and input stream
    private Socket socket = null;
    private ServerSocket serverSocket = null;
    private DataInputStream in = null;
    private DataOutputStream out = null;
    private static int uniqueToken = 100;
    private static List<Account> accountList = new ArrayList<Account>();
    private static int counter_id = 0;
    // implementation of constructor

    public Server(int port) {
        // start server and wait for a connection
        try {
            serverSocket = new ServerSocket(port);
            //System.out.println("Server started!");
            //System.out.println("Waiting for a client ......");

            // take input from the client socket

            try {
                while (true) {
                    socket = serverSocket.accept();
                    System.out.println("Client accepted.");
                    in = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
                    out = new DataOutputStream(socket.getOutputStream());
                    int fn_id;
                    fn_id = in.read();
                    if (fn_id == 1) {
                        createAccount(in,out,accountList);
                    }else if (fn_id == 2){
                        //java client <ip> <port number> 2 <authToken>
                        int token = in.read();
                        out.writeBoolean(validToken(token));
                        if(validToken(token)){
                            showAccounts(in,out,accountList);
                        }else{
                            out.writeUTF("Invalid Token");
                        }
                    }else if (fn_id == 3){
                        //java client localhost 5000 3 1024 tester “HELLO WORLD”
                        //check token
                        int token = in.read();
                        boolean valid = validToken(token);
                        out.writeBoolean(valid);
                        if(validToken(token)){
                            sendMessage(in,out,accountList,token);
                        }else{
                            out.writeUTF("Invalid Token");
                        }
                    }else if (fn_id == 4){
                        //java client <ip> <port number> 4 <authToken>
                        int token = in.read();
                        boolean valid = validToken(token);
                        out.writeBoolean(valid);
                        if (valid){
                            showInbox(in,out,accountList,token);
                        }else{
                            out.writeUTF("Invalid Token");
                        }
                    }else if (fn_id == 5){
                        //java client <ip> <port number> 5 <authToken> <message_id>
                        int token = in.read();
                        boolean valid = validToken(token);
                        out.writeBoolean(valid);
                        if (valid){
                            readMessage(in,out,accountList,token);
                        }else{
                            out.writeUTF("Invalid Token");
                        }
                    }else if (fn_id == 6){
                        //java client <ip> <port number> 6 <authToken> <message_id>
                        int token = in.read();
                        boolean valid = validToken(token);
                        out.writeBoolean(valid);
                        if (valid){
                            deleteMessage(in,out,accountList,token);
                        }else{
                            out.writeUTF("Invalid Token");
                        }
                    }
                }
            }catch (IOException e){
                e.printStackTrace();
            }
            socket.close();
            in.close();
            //out.close();
            //System.out.println("Connection terminated.");
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public static void deleteMessage(DataInputStream in,DataOutputStream out,List<Account> accountList,int token) throws IOException {
        //java client <ip> <port number> 6 <authToken> <message_id>
        int messageID = in.read();
        //find if messageID exists
        boolean exists = false;
        int posI = 0, posJ = 0;
        for (int i = 0; i < accountList.size(); i++){
            for (int j = 0; j < accountList.get(i).getMessageBox().size(); j++){
                if (messageID == accountList.get(i).getMessageBox().get(j).getId()) {
                    exists = true;
                    posI = i;
                    posJ = j;
                    break;
                }
            }
        }
        if (exists){
            //removing message
            accountList.get(posI).getMessageBox().remove(posJ);
            out.writeUTF("OK");
        }else{
            out.writeUTF("Message does not exist");
        }

    }

    public static void readMessage(DataInputStream in,DataOutputStream out,List<Account> accountList,int token) throws IOException {
        //java client <ip> <port number> 5 <authToken> <message_id>
        int messageId = in.read();
        //find the user
        int pos = 0;
        for (int i = 0; i < accountList.size(); i++){
            if (token == accountList.get(i).authToken()){
                pos = i;
                break;
            }
        }
        //find if the messageId exists
        int sizeOfMessages = accountList.get(pos).getMessageBox().size();
        String sender = null;
        boolean exists = false;
        for (int i = 0; i < sizeOfMessages; i++){
            if (messageId == accountList.get(pos).getMessageBox().get(i).getId()){
                sender = accountList.get(pos).getMessageBox().get(i).getSender();
                out.writeUTF("("+sender+")" + accountList.get(pos).getMessageBox().get(i).getBody());
                accountList.get(pos).getMessageBox().get(i).setRead(true);
                exists = true;
                break;
            }
        }
        if (!exists){
            out.writeUTF("Message ID does not exist");
        }
    }

    public static void showInbox(DataInputStream in,DataOutputStream out,List<Account> accountList,int token) throws IOException {
        //check if inbox is empty
        int pos = 0;
        boolean flag = false;
        for (int i = 0; i < accountList.size(); i++){
            if (token == accountList.get(i).authToken()){
                pos = i;
                if(accountList.get(i).getMessageBox().isEmpty()){
                    flag = true;
                }
                break;
            }
        }
        out.writeBoolean(flag);
        if (!flag){
            int sizeOfMessageBox = accountList.get(pos).getMessageBox().size();
            out.write(sizeOfMessageBox);
            for (int i = 0; i < sizeOfMessageBox; i++){
                boolean isRead = accountList.get(pos).getMessageBox().get(i).isRead();
                String sender = accountList.get(pos).getMessageBox().get(i).getSender();
                int idMessage = accountList.get(pos).getMessageBox().get(i).getId();
                if (isRead){
                    out.writeUTF(idMessage + "." + " from: " + sender);
                }else{
                    out.writeUTF(idMessage + "." + " from: " + sender + "*");
                }
            }
        }else {
            out.writeUTF("Inbox is empty");
        }

    }

    private boolean validToken(int token){
        for (Account account : accountList) {
            if (token == account.authToken()) {
                return true;
            }
        }
        return false;
    }

    public static void createAccount(DataInputStream in,DataOutputStream out,List<Account> accountList){
        try {
            boolean flag = false;
            String username = in.readUTF();
            for (int i = 0; i < accountList.size(); i++){
                if (username.equals(accountList.get(i).username())){
                    out.writeUTF("Sorry, the user already exists");
                    //System.out.println(accountList.get(i).username());
                    flag = true;
                    break;
                }
            }
            boolean flagName = false;
            if (!flag) {
                for (int i = 0; i < username.length(); i++) {
                    char c = username.charAt(i);
                    if (!(c >= 'A' && c <= 'Z') && !(c >= 'a' && c <= 'z')) {
                        if (c != '_') {
                            flagName = true;
                            break;
                        }
                    }
                }
                if (flagName) {
                    out.writeUTF("Invalid Username");
                }else{
                    //creating the account
                    //generate token
                    uniqueToken++;
                    String token = String.valueOf(uniqueToken);
                    Account account = new Account(username, uniqueToken);
                    out.writeUTF(token);
                    accountList.add(account);
                }
            }

        } catch (IOException e) {
            System.out.println(e);
        }

    }

    public static void showAccounts(DataInputStream in,DataOutputStream out,List<Account> accountList) throws IOException {
        int counter = 0;
        out.write(accountList.size());
        for (int i = 0; i < accountList.size(); i++){
            counter++;
            out.writeUTF(counter +". " + accountList.get(i).username());
        }
    }

    public static void sendMessage(DataInputStream in,DataOutputStream out,List<Account> accountList,int token) throws IOException {
        //java client localhost 5000 3 1024 tester “HELLO WORLD”
        String receiver = in.readUTF();
        String line = in.readUTF();
        //find if receiver exists
        boolean exists = false;
        int pos = 0;
        for (int i = 0; i < accountList.size(); i++){
            if (receiver.equals(accountList.get(i).username())){
                exists = true;
                pos = i;
                break;
            }
        }
        if (exists){
            //find the sender
            String sender = null;
            boolean senderExists = false;
            for (int i = 0; i < accountList.size(); i++){
                if (token == accountList.get(i).authToken()){
                    senderExists = true;
                    sender = accountList.get(i).username();
                    break;
                }
            }
            if (senderExists){
                Message message = new Message(false,sender,receiver,counter_id++,line);
                accountList.get(pos).addMessageBox(message);
                out.writeUTF("OK");
            }else{
                out.writeUTF("User does not exist");
            }
        }else{
            out.writeUTF("User does not exist");
        }
    }


    public static void main(String[] args) {
        Server server = new Server(Integer.parseInt(args[0]));
    }

}
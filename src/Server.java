import java.io.*;
import java.net.*;

class Server {
    public static void main(String[] args)
    {
        ServerSocket server = null;
        try {
            // server is listening on port 2021
            server = new ServerSocket(2021);
            // running infinite loop accepting client request
            while (true) {
                // socket object to receive incoming client requests
                Socket client = server.accept();
                System.out.println("New client connected" + client.getInetAddress().getHostAddress());
                // create a new thread object of Runnable
                ClientHandler clientSock = new ClientHandler(client);
                // This thread will handle the client separately
                new Thread(clientSock).start();
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        // command "finally" is omitted
    }
    private static class ClientHandler implements Runnable {
        private final Socket clientSocket;
        // Constructor
        public ClientHandler(Socket socket)
        {
            this.clientSocket = socket;
        }
        // the thread echoes client's messages
        public void run()
        {
            PrintWriter out = null;
            BufferedReader in = null;
            try {
                // get the outputstream of client
                out = new PrintWriter(
                        clientSocket.getOutputStream(), true);
                // get the inputstream of client
                in = new BufferedReader(
                        new InputStreamReader(
                                clientSocket.getInputStream()));
                // writing the received message from client
                String line;
                while ((line = in.readLine()) != null) {
                    System.out.printf(
                            " Sent from the client: %s\n",
                            line);
                    out.println(line);
                }
            }
            catch (IOException e) {
                e.printStackTrace();
            }
            // command "finally" is omitted
        }
    }
}
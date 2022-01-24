import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String username = scanner.nextLine();
        MessagingClient messagingClient = new MessagingClient("127.0.0.1",2000,username);
    }

}

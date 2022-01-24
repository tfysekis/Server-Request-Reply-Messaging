import java.util.List;

public class Account {
    private String username;
    private int port;
    private Message message;

    public Account(String username,int port,Message message) {
        this.username = username;
        this.port = port;
        this.message = message;
    }

    public String username(String username){
        return username;
    }

    public int authToken(){
        return port;
    }

    public List<Message> messageBox(){
        messageBox().add(message);
        return messageBox();
    }


}

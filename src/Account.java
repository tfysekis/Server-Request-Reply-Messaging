import java.util.List;

public class Account {
    private String username;
    private int authToken;
    private Message message;

    public Account(String username,int authToken) {
        this.username = username;
        this.authToken = authToken;
        //this.message = message;
    }

    public String username(){
        return username;
    }

    public int authToken(){
        return authToken;
    }

    public List<Message> messageBox(){
        messageBox().add(message);
        return messageBox();
    }


}

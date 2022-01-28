import java.util.ArrayList;
import java.util.List;

public class Account {
    private String username;
    private int authToken;
    private String line;
    private List<Message> messageBox;

    public Account(String username,int authToken) {
        this.username = username;
        this.authToken = authToken;
        this.messageBox = new ArrayList<>();
    }

    public String username(){
        return username;
    }

    public int authToken(){
        return authToken;
    }

    public List<Message> getMessageBox() {
        return messageBox;
    }

    public void setMessageBox(List<Message> messageBox) {
        this.messageBox = messageBox;
    }

    public void addMessageBox(Message message){
        messageBox.add(message);
    }


}

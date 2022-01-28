public class Message {

    private boolean isRead;
    private String sender;
    private String receiver;
    private String body;
    private int id;

    public Message(boolean isRead, String sender, String receiver,int id, String body){
        this.isRead = isRead;
        this.sender = sender;
        this.receiver = receiver;
        this.body = body;
        this.id = id;
    }

    public boolean isRead(){
        return isRead;
    }

    public void setRead(boolean read) {
        isRead = read;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getBody() {
        return body;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getSender() {
        return sender;
    }
}

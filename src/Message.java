public class Message {
    private String line;

    public Message(String line){
        this.line = line;
    }

    public boolean isRead(){

        return false;
    }

    public String sender(){

        return null;
    }

    public String receiver(){

        return null;
    }

    public String body(){
        return this.line;
    }
}

import java.io.Serializable;
import java.util.List;

public class ChatMessage implements Serializable {
    private static final long serialVersionUID = 1L;

    public enum Type {
        PUBLIC_MSG,   
        PRIVATE_MSG, 
        USER_LIST    
    }

    private Type type;
    private String sender;
    private String recipient; 
    private String content;
    private List<String> activeUsers; 

    public ChatMessage(Type type, String sender, String recipient, String content) {
        this.type = type;
        this.sender = sender;
        this.recipient = recipient;
        this.content = content;
    }

    public ChatMessage(List<String> activeUsers) {
        this.type = Type.USER_LIST;
        this.activeUsers = activeUsers;
    }

    public Type getType() { 
        return type; 
    }

    
    public String getSender() { 
        return sender; 
    }


    public String getRecipient() { 
        return recipient; 
    }


    public String getContent() { 
        return content; 
    }


    public List<String> getActiveUsers() { 
        return activeUsers; 
    }
}
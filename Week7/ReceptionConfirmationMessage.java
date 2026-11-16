import java.io.Serializable;

public class ReceptionConfirmationMessage implements Serializable{
    private static final long serialVersionUID = 1L;
    private String rcvMsg;

    public ReceptionConfirmationMessage(String rcvMsg) {
        this.rcvMsg = rcvMsg;
    }

    public String getMessage() {
        return rcvMsg;
    }

    @Override 
    public String toString() {
        return rcvMsg;
    }
}
package iscot.events;
import java.io.Serializable;

public class ReceptionConfirmationMessage implements Serializable {
    private static final long serialVersionUID = 1L;
    private final String event;
    private final String deviceId;
    private final String status;

    public ReceptionConfirmationMessage(String deviceId, String status) {
        this.event = "evento-recebido";
        this.deviceId = deviceId;
        this.status = status;
    }

    public String getEvent() {
        return event;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public String getStatus() {
        return status;
    }

    @Override 
    public String toString() {
        return "{ event: \"" + event + "\", deviceId: \""  + deviceId + "\", status: \"" + status + "\" }";
    }
}

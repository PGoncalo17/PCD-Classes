package iscot.client.actuators;

import iscot.client.Client;
import iscot.enums.Areas;
import iscot.enums.Type;
import iscot.events.Event;
import iscot.events.HighLevelOrderEvent;
import iscot.events.ReceptionConfirmationMessage;
import java.io.IOException;

public abstract class ActuatorClient extends Client{
    private String deviceId;
    
    protected ActuatorClient(String ip, int porto, Areas area) {
        super(ip, porto, area, Type.ACTUATOR);
        this.deviceId = this.getClass().getSimpleName() + "_" + area.name();
    }

    @Override 
    public void execute() throws IOException, ClassNotFoundException {
        System.out.println("À escuta de eventos...");

        while (true) { 
            Event event = receiveEvent();
            if (event instanceof HighLevelOrderEvent highLevelOrderEvent) {
                boolean success = processOrderEvent(highLevelOrderEvent);
                String status = success ? "OK" : "NOK";

                ReceptionConfirmationMessage confirmation = new ReceptionConfirmationMessage(deviceId, status);
                sendObject(confirmation);
                System.out.println("Confirmação enviada: " + confirmation);
            }
        }
    }

    protected abstract boolean processOrderEvent(HighLevelOrderEvent order);
}

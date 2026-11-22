package iscot.client.actuators;

import iscot.client.Client;
import iscot.enums.Areas;
import iscot.enums.Type;
import iscot.events.Event;
import iscot.events.HighLevelOrderEvent;
import java.io.IOException;

public abstract class ActuatorClient extends Client{
    
    protected ActuatorClient(String ip, int porto, Areas area) {
        super(ip, porto, area, Type.ACTUATOR);
    }

    @Override 
    public void execute() throws IOException, ClassNotFoundException {
        System.out.println("À escuta de eventos...");

        while (true) { 
            Event event = receiveEvent();
            if (event instanceof HighLevelOrderEvent highLevelOrderEvent) {
                processOrderEvent(highLevelOrderEvent);
            }
        }
    }

    protected abstract void processOrderEvent(HighLevelOrderEvent order);
}

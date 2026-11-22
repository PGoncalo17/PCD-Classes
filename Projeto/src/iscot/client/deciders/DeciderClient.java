package iscot.client.deciders;

import iscot.client.Client;
import iscot.enums.Areas;
import iscot.enums.Type;
import iscot.events.Event;
import iscot.events.HighLevelOrderEvent;
import iscot.events.SensorEvent;
import java.io.IOException;

public abstract class DeciderClient extends Client {


    protected DeciderClient(String ip, int porto, Areas area) {
        super(ip, porto, area, Type.DECIDER);
    }

    @Override 
    public void execute() throws IOException, ClassNotFoundException {
        System.out.println("Decisor ativo na área " + getArea() + ". À escuta de eventos...");

        while (true) { 
            Event event = receiveEvent();

            if (event instanceof SensorEvent sensorEvent) {
                processSensorEvent(sensorEvent);
            }
        }
    }

    protected abstract void processSensorEvent(SensorEvent event);

    public void sendEvent(HighLevelOrderEvent order) {
        try {
            super.sendEvent(order);
            System.out.println("Evento enviado: " + order);
        } catch (IOException e) {
            System.err.println("Erro ao enviar evento");
        }
    }
    
}

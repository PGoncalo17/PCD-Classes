package iscot.client.sensors;

import iscot.client.Client;
import iscot.enums.Areas;
import iscot.enums.Type;
import iscot.events.SensorEvent;
import java.io.IOException;

public abstract class SensorClient extends Client {

    protected SensorClient(String ip, int porto, Areas area){
        super(ip, porto, area, Type.SENSOR);
    }

    public void sendEvent(SensorEvent event) {
        try {
            super.sendEvent(event);
            System.out.println("Evento enviado: " + event);
        } catch (IOException e) {
            System.err.println("Erro ao enviar evento");
        }
    }

    @Override 
    protected void execute(){ }
}

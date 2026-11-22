package iscot.client.deciders;

import iscot.enums.Areas;
import iscot.events.AreaLightingOrderEvent;
import iscot.events.LightSensorEvent;
import iscot.events.SensorEvent;

public class LightningDecider extends DeciderClient {

    public LightningDecider(String ip, int porto, Areas area) {
        super(ip, porto, area);
    }

    @Override 
    public void processSensorEvent(SensorEvent event) {
        if (event instanceof LightSensorEvent) {
            LightSensorEvent lightEvent = (LightSensorEvent) event;
            double nivelLuz = lightEvent.getLightSensorValue();
            System.out.println("Recebido sensor de luz: " + nivelLuz + "%");

            if (nivelLuz < 35) {
                sendEvent(new AreaLightingOrderEvent(getArea(), true));
            } else {
                sendEvent(new AreaLightingOrderEvent(getArea(), false));
            }
        }
    }



    public static void main(String[] args) throws NumberFormatException, InterruptedException {
        if (args.length != 3){
            System.err.println("Invalid arguments!");
            return;
        }
        
        String ip = args[0];
        int porto = Integer.parseInt(args[1]);
        Areas area = Areas.valueOf(args[2].toUpperCase());

        LightningDecider decider = new LightningDecider(ip, porto, area);

        decider.runClient();
        }
    
}

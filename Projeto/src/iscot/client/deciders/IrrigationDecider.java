package iscot.client.deciders;

import iscot.enums.Areas;
import iscot.events.AreaIrrigationOrderEvent;
import iscot.events.GroundMoistureSensorEvent;
import iscot.events.SensorEvent;

public class IrrigationDecider extends DeciderClient {

    public IrrigationDecider(String ip, int porto, Areas area) {
        super(ip, porto, area);
    }


    @Override 
    public void processSensorEvent(SensorEvent event) {
        if (event instanceof GroundMoistureSensorEvent groundMoistureSensorEvent) {
            GroundMoistureSensorEvent groundEvent = groundMoistureSensorEvent;
            double nivelHumidadeSolo = groundEvent.getGroundMoistureValue();
            System.out.println("Recebido sensor de humidade do solo: " + nivelHumidadeSolo + "%");

            if(nivelHumidadeSolo < 40) {
                sendEvent(new AreaIrrigationOrderEvent(getArea(), true));
            } else {
                sendEvent(new AreaIrrigationOrderEvent(getArea(), false));
            }
        }
    }

    public static void main(String[] args) throws NumberFormatException {
        if (args.length != 3){
            System.err.println("Invalid arguments!");
            return;
        }   

        String ip = args[0];
        int porto = Integer.parseInt(args[1]);
        Areas area = Areas.valueOf(args[2].toUpperCase());
        
        IrrigationDecider decider = new IrrigationDecider(ip, porto, area);

        decider.runClient();
    }
    
}

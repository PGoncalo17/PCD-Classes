package iscot.client;

import iscot.enums.Areas;
import iscot.enums.Type;
import iscot.events.AreaLightingOrderEvent;
import iscot.events.LightSensorEvent;
import iscot.events.RegistrationEvent;

public class LightningDecider {
    public static void main(String[] args) throws NumberFormatException, InterruptedException {
        if (args.length != 3){
            System.err.println("Invalid arguments!");
        } else {
            Areas area = Areas.valueOf(args[2]);
            RegistrationEvent regEvent = new RegistrationEvent(area, Type.DECIDER);
            System.out.println("Decisor de Ventilação registado: " + regEvent);

            // Local Simulation
            Thread.sleep(3000);
            LightSensorEvent sensorEvent = new LightSensorEvent(area, 20);
            System.out.println("Leitura recebida: " + sensorEvent);

            boolean ligar = sensorEvent.getLighSensorValue() < 30;
            AreaLightingOrderEvent ordem = new AreaLightingOrderEvent(area, ligar);
            System.out.println("Ordem emitida: " + ordem);

        }
    }
    
}

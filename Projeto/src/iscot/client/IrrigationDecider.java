package iscot.client;

import iscot.enums.Areas;
import iscot.enums.Type;
import iscot.events.AreaIrrigationOrderEvent;
import iscot.events.AreaLightingOrderEvent;
import iscot.events.GroundMoistureSensorEvent;
import iscot.events.LightSensorEvent;
import iscot.events.RegistrationEvent;

public class IrrigationDecider {
    public static void main(String[] args) throws NumberFormatException, InterruptedException {
        if (args.length != 3){
            System.err.println("Invalid arguments!");
        } else {
            Areas area = Areas.valueOf(args[2]);
            RegistrationEvent regEvent = new RegistrationEvent(area, Type.DECIDER);
            System.out.println("Decisor de Ventilação registado: " + regEvent);

            // Local Simulation
            Thread.sleep(3000);
            GroundMoistureSensorEvent sensorEvent = new GroundMoistureSensorEvent(area, 30);
            System.out.println("Leitura recebida: " + sensorEvent);

            boolean ligar = sensorEvent.getGroundMoistureValue() < 40;
            AreaIrrigationOrderEvent ordem = new AreaIrrigationOrderEvent(area, ligar);
            System.out.println("Ordem emitida: " + ordem);

        }
    }
    
}

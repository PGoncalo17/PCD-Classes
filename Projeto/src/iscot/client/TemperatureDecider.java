package iscot.client;

import iscot.enums.Areas;
import iscot.enums.Type;
import iscot.events.AreaTemperatureOrderEvent;
import iscot.events.RegistrationEvent;
import iscot.events.TemepratureSensorEvent;

public class TemperatureDecider {
    public static void main(String[] args) throws NumberFormatException, InterruptedException {
        if (args.length != 3){
            System.err.println("Invalid arguments!");
        } else {
            Areas area = Areas.valueOf(args[2]);
            RegistrationEvent regEvent = new RegistrationEvent(area, Type.DECIDER);
            System.out.println("Decisor de Ventilação registado: " + regEvent);

            // Local Simulation
            Thread.sleep(3000);
            TemepratureSensorEvent sensorEvent = new TemepratureSensorEvent(area, 28);
            System.out.println("Leitura recebida: " + sensorEvent);

            boolean ligar = sensorEvent.getTemperatureValue() > 25;
            AreaTemperatureOrderEvent ordem = new AreaTemperatureOrderEvent(area, ligar);
            System.out.println("Ordem emitida: " + ordem);

        }
    }
    
}

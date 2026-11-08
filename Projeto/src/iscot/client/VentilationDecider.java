package iscot.client;

import iscot.enums.Areas;
import iscot.enums.Type;
import iscot.events.AreaVentilationOrderEvent;
import iscot.events.HumiditySensorEvent;
import iscot.events.RegistrationEvent;

public class VentilationDecider {
    public static void main(String[] args) throws NumberFormatException, InterruptedException {
        if (args.length != 3){
            System.err.println("Invalid arguments!");
        } else {
            Areas area = Areas.valueOf(args[2]);
            RegistrationEvent regEvent = new RegistrationEvent(area, Type.DECIDER);
            System.out.println("Decisor de Ventilação registado: " + regEvent);

            // Local Simulation
            Thread.sleep(3000);
            HumiditySensorEvent sensorEvent = new HumiditySensorEvent(area, 75);
            System.out.println("Leitura recebida: " + sensorEvent);

            boolean ligar = sensorEvent.getHumidityValue() > 70;
            AreaVentilationOrderEvent ordem = new AreaVentilationOrderEvent(area, ligar);
            System.out.println("Ordem emitida: " + ordem);

        }
    }
    
}

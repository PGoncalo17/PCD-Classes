package iscot.client;

import iscot.enums.AirConditioningMode;
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

            // Local Simulation - Change after connect client with server
            Thread.sleep(3000);
            TemepratureSensorEvent sensorEvent = new TemepratureSensorEvent(area, 28);
            System.out.println("Leitura recebida: " + sensorEvent);

            double temp = sensorEvent.getTemperatureValue();
            AirConditioningMode mode; 

            if (temp < 20) {
                mode = AirConditioningMode.Aquecimento;
            } else if (temp > 26){
                mode = AirConditioningMode.Frio;
            } else if (temp >= 22 && temp <= 24) {
                mode = AirConditioningMode.Desligado;
            } else {
                mode = AirConditioningMode.Ventilacao;
            }

            AreaTemperatureOrderEvent ordem = new AreaTemperatureOrderEvent(area, mode);
            System.out.println("Ordem emitida: " + ordem);

        }
    }
    
}

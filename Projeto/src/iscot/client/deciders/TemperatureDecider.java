package iscot.client.deciders;

import iscot.enums.AirConditioningMode;
import iscot.enums.Areas;
import iscot.events.AreaTemperatureOrderEvent;
import iscot.events.SensorEvent;
import iscot.events.TemepratureSensorEvent;

public class TemperatureDecider extends DeciderClient {

    public TemperatureDecider(String ip, int porto, Areas area) {
        super(ip, porto, area);
    }

    @Override
    public void processSensorEvent(SensorEvent event) {
        if (event instanceof TemepratureSensorEvent temperatureSensorEvent) {
            TemepratureSensorEvent tempSensor = temperatureSensorEvent;
            double temp = tempSensor.getTemperatureValue();
            System.out.println("Recebido sensor de temperatura: " + temp + "Cº");

            if (temp < 20) {
                sendEvent(new AreaTemperatureOrderEvent(getArea(), AirConditioningMode.Aquecimento));
            } else if (temp > 26){
                sendEvent(new AreaTemperatureOrderEvent(getArea(), AirConditioningMode.Frio));
            } else if (temp >= 22 && temp <= 24) {
                sendEvent(new AreaTemperatureOrderEvent(getArea(), AirConditioningMode.Desligado));
            } else {
                sendEvent(new AreaTemperatureOrderEvent(getArea(), AirConditioningMode.Ventilacao));
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

        TemperatureDecider decider = new TemperatureDecider(ip, porto, area);

        decider.runClient();
    }
    
}

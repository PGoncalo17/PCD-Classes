package iscot.client.deciders;

import iscot.enums.AirConditioningMode;
import iscot.enums.Areas;
import iscot.events.AreaTemperatureOrderEvent;
import iscot.events.AreaVentilationOrderEvent;
import iscot.events.HumiditySensorEvent;
import iscot.events.SensorEvent;
import iscot.events.TemepratureSensorEvent;

public class VentilationDecider extends DeciderClient {
    private AirConditioningMode acMode = AirConditioningMode.Desligado;
    private double lastHumidity = 0;

    public VentilationDecider(String ip, int porto, Areas area) {
        super(ip, porto, area);
    }


    @Override 
    public void processSensorEvent(SensorEvent event) {
        if (event instanceof TemepratureSensorEvent) {
            TemepratureSensorEvent tempSensor = (TemepratureSensorEvent) event;
            double temp = tempSensor.getTemperatureValue();

            if (temp < 20) {
                acMode = AirConditioningMode.Aquecimento;
            } else if (temp > 26){
                acMode = AirConditioningMode.Frio;
            } else if (temp >= 22 && temp <= 24) {
                acMode = AirConditioningMode.Desligado;
                if (lastHumidity > 70) {
                    sendEvent(new AreaTemperatureOrderEvent(getArea(), AirConditioningMode.Ventilacao));
                }
            } else {
                acMode = AirConditioningMode.Ventilacao;
            }
        }

        if (event instanceof HumiditySensorEvent) {
            HumiditySensorEvent humiditySensor = (HumiditySensorEvent) event;
            double humidity = humiditySensor.getHumidityValue();
            lastHumidity = humidity;
            System.out.println("Recebido sensor de humidade: " + humidity + "%");

            if(humidity > 70) {
                sendEvent(new AreaVentilationOrderEvent(getArea(), true));

                if(acMode == AirConditioningMode.Desligado) {
                    acMode = AirConditioningMode.Ventilacao;
                    sendEvent(new AreaTemperatureOrderEvent(getArea(), acMode));
                }
            } else {
                sendEvent(new AreaVentilationOrderEvent(getArea(), false));
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
        Areas area = Areas.valueOf(args[2]);

        VentilationDecider decider = new VentilationDecider(ip, porto, area);

        decider.runClient();
    }
    
}

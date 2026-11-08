package iscot.events;

import iscot.enums.Areas;

public class HumiditySensorEvent extends SensorEvent{
    private final double humidityValue;

    public HumiditySensorEvent(Areas area, double humidityValue){
        super(area);
        this.humidityValue = humidityValue;
    }

    public double getHumidityValue(){
        return humidityValue;
    }

    @Override 
    public String toString(){
        return "[SENSOR HUMIDADE] " + super.toString() + " | Humidade: " + getHumidityValue() + "%";
    }
    
}
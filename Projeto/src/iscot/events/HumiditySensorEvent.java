package iscot.events;

import iscot.enums.Areas;

public class HumiditySensorEvent extends SensorEvent{
    private final double humidityValue;
    private static final long serialVersionUID = 1L;

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
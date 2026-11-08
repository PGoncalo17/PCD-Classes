package iscot.events;

import iscot.enums.Areas;

public class TemepratureSensorEvent extends SensorEvent{
    private final double temperatureValue;

    public TemepratureSensorEvent(Areas area, double temperatureValue){
        super(area);
        this.temperatureValue = temperatureValue;
    }

    public double getTemperatureValue(){
        return temperatureValue;
    }

    @Override 
    public String toString(){
        return "[SENSOR Temperatura] " + super.toString() + " | Temperatura: " + getTemperatureValue() + "Cº";
    }
    
}
package iscot.events;

import iscot.enums.Areas;

public class TemepratureSensorEvent extends SensorEvent{
    private final int temperatureValue;

    public TemepratureSensorEvent(Areas area, int temperatureValue){
        super(area);
        this.temperatureValue = temperatureValue;
    }

    public int getTemperatureValue(){
        return temperatureValue;
    }

    @Override 
    public String toString(){
        return "[SENSOR Temperatura] " + super.toString() + " | Temperatura: " + getTemperatureValue() + "Cº";
    }
    
}
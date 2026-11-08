package iscot.events;

import iscot.enums.Areas;

public class LightSensorEvent extends SensorEvent{
    private final double lightSensorValue;

    public LightSensorEvent(Areas area, double lightSensorValue){
        super(area);
        this.lightSensorValue = lightSensorValue;
    }

    public double getLighSensoValue(){
        return lightSensorValue;
    }

    @Override 
    public String toString(){
        return "[SENSOR LUZ] " + super.toString() + " | LUZ: " + getLighSensoValue() + "lux";
    }
    
}
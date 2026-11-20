package iscot.events;

import iscot.enums.Areas;

public class LightSensorEvent extends SensorEvent{
    private final double lightSensorValue;
    private static final long serialVersionUID = 1L;

    public LightSensorEvent(Areas area, double lightSensorValue){
        super(area);
        this.lightSensorValue = lightSensorValue;
    }

    public double getLightSensorValue(){
        return lightSensorValue;
    }

    @Override 
    public String toString(){
        return "[SENSOR LUZ] " + super.toString() + " | LUZ: " + getLightSensorValue() + "lux";
    }
    
}
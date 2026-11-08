package iscot.events;

import iscot.enums.Areas;

public class LightSensorEvent extends SensorEvent{
    private final int lightSensorValue;

    public LightSensorEvent(Areas area, int lightSensorValue){
        super(area);
        this.lightSensorValue = lightSensorValue;
    }

    public int getLighSensoValue(){
        return lightSensorValue;
    }

    @Override 
    public String toString(){
        return "[SENSOR LUZ] " + super.toString() + " | LUZ: " + getLighSensoValue() + "lux";
    }
    
}
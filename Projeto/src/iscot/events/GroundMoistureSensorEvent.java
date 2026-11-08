package iscot.events;

import iscot.enums.Areas;

public class GroundMoistureSensorEvent extends SensorEvent {
    private final double groundMoistureValue;

    public GroundMoistureSensorEvent(Areas area, double groundMoistureValue){
        super(area);
        this.groundMoistureValue = groundMoistureValue;
    }

    public double getGroundMoistureValue(){
        return groundMoistureValue;
    }

    @Override 
    public String toString(){
        return "[SENSOR GROUND MOISTURE] " + super.toString() + " | Ground Moisture: " + getGroundMoistureValue() + "%";
    }

}
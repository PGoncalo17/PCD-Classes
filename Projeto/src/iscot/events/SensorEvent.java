package iscot.events;
import iscot.enums.*;

public abstract class SensorEvent extends Event{

    protected SensorEvent(Areas area){
        super(area);
    }

    public Type getType(){
        return Type.SENSOR;
    }

}
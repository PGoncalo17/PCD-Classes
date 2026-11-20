package iscot.events;
import iscot.enums.*;

public abstract class SensorEvent extends Event{
    private static final long serialVersionUID = 1L;

    protected SensorEvent(Areas area){
        super(area);
    }

    public Type getType(){
        return Type.SENSOR;
    }

}
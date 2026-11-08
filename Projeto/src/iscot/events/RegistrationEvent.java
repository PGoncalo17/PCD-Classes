package iscot.events;

import iscot.enums.Areas;
import iscot.enums.Type;

public abstract class RegistrationEvent extends Event {
    private final Type type;

    protected RegistrationEvent(Areas area, Type type){
        super(area);
        this.type = type;
    }

    public Type getType(){
        return type;
    }

    @Override
    public String toString() {
        return "[REGISTO] Cliente do tipo " + type + " registado na área: " + getArea();
    }
}



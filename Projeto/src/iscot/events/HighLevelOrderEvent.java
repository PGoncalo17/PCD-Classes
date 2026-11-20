package iscot.events;

import iscot.enums.Areas;
import iscot.enums.Type;

public abstract class HighLevelOrderEvent extends Event {
    private static final long serialVersionUID = 1L;

    protected HighLevelOrderEvent(Areas area){
        super(area);
    }

    public Type getType(){
        return Type.DECIDER;
    }
}

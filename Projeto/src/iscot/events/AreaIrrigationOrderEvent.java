package iscot.events;

import iscot.enums.Areas;

public class AreaIrrigationOrderEvent extends HighLevelOrderEvent {
    private final boolean active;

    public AreaIrrigationOrderEvent(Areas area, boolean active){
        super(area);
        this.active = active;
    }

    public boolean isActive(){
        return active;
    }

    @Override
    public String toString() {
        String state = active ? "TURN ON" : "TURN OFF";
        return "[IRRIGATION ORDER] " + super.toString() + " | ACTION: " + state;
    }
}

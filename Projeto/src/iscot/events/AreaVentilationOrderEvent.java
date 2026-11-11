package iscot.events;

import iscot.enums.Areas;

public class AreaVentilationOrderEvent extends HighLevelOrderEvent {
    private final boolean active;

    public AreaVentilationOrderEvent(Areas area, boolean active) {
        super(area);
        this.active = active;
    }

    public boolean isActive(){
        return active;
    }

    @Override
    public String toString() {
        String state = active ? "TURN ON" : "TURN OF";
        return "[VENTILATION ORDER] " + super.toString() + " | ACTION: " + state;
    }
    
}

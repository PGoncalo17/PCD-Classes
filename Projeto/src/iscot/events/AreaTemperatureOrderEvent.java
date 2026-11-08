package iscot.events;

import iscot.enums.Areas;

public class AreaTemperatureOrderEvent extends HighLevelOrderEvent {
    private final boolean active;

    public AreaTemperatureOrderEvent(Areas area, boolean active) {
        super(area);
        this.active = active;
    }

    public boolean isActive(){
        return active;
    }

    @Override
    public String toString() {
        String state = active ? "TURN ON" : "TURN OF";
        return "[Temperature ORDER] " + super.toString() + " | ACTION: " + state;
    }
    
}

package iscot.events;

import iscot.enums.AirConditioningMode;
import iscot.enums.Areas;

public class AreaTemperatureOrderEvent extends HighLevelOrderEvent {
    private final AirConditioningMode mode;
    // private final boolean active; // Reformar para airconditioning mode

    public AreaTemperatureOrderEvent(Areas area, AirConditioningMode mode) {
        super(area);
        this.mode = mode;
    }

    public AirConditioningMode getMode(){
        return mode;
    }

    @Override
    public String toString() {
        String state = (mode == AirConditioningMode.Desligado) ? "TURN OF" : "TURN ON (" + mode + ")";
        return "[TEMPERATURE ORDER] " + super.toString() + " | ACTION: " + state;
    }
    
}

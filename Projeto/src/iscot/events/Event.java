package iscot.events;
import iscot.enums.Areas;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZoneId;

public abstract class Event implements Serializable {
    private final Areas area;
    private final LocalDateTime timestamp;
    private static final long serialVersionUID = 1L;

    protected Event(Areas area){
        this.area = area;
        this.timestamp = LocalDateTime.now(ZoneId.systemDefault());
    }

    public Areas getArea(){
        return area;
    }

    public LocalDateTime getTimestamp(){
        return timestamp;
    }

    @Override 
    public String toString(){
        return "[" + timestamp.toLocalTime() + "] Área: " + area;
    }
}
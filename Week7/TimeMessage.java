import java.io.Serializable;

public class TimeMessage implements Serializable {
    private static final long serialVersionUID = 1L;
    private long time;

    public TimeMessage (long time) {
        this.time = time;
    }

    public long getTime() {
        return time;
    }

    @Override
    public String toString() {
        return "Time: " + time;
    }
}
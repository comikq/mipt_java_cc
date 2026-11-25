import java.time.Duration;
import java.time.Instant;

public record MaintenanceWindow(
        Instant start,
        Instant end
) {

    public MaintenanceWindow {

        if (start == null) throw new NullPointerException("null start object");
        if (end == null) throw new NullPointerException("null end object");

        if (end.isBefore(start)) {
            throw new IllegalArgumentException("End before start");
        }
    }

    public Duration duration() {
        return Duration.between(start, end);
    }
}

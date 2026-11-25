import java.time.Duration;
import java.time.Instant;

public record Incident(
        String id,
        String service,
        Instant startedAt,
        Instant resolvedAt) {
    public Incident {

        if (id == null) throw new NullPointerException("null id object");
        if (service == null) throw new NullPointerException("null service object");
        if (startedAt == null) throw new NullPointerException("null startedAt object");
        if (resolvedAt == null) throw new NullPointerException("null resolvedAt object");

        if (id.isBlank() || service.isBlank()) {
            throw new IllegalArgumentException("Empty id or service");
        }

        if (resolvedAt.isBefore(startedAt)) {
            throw new IllegalArgumentException("Incorrect time order");
        }
    }

    public Duration duration() {
        return Duration.between(startedAt, resolvedAt);
    }
}
import java.time.*;
import java.util.ArrayList;
import java.util.List;

public final class MaintenanceScheduler {

    private final Clock clock;

    public MaintenanceScheduler(Clock clock) {
        if (clock == null) throw new NullPointerException("null clock object");
        this.clock = clock;
    }

    public MaintenanceWindow nextWindow(LocalDate date, LocalTime localStart, Duration duration,
            ZoneId zone, OverlapPolicy policy) {

        if ( date == null) throw new NullPointerException("null date object");
        if ( localStart == null) throw new NullPointerException("null localStart object");
        if ( duration == null) throw new NullPointerException("null duration object");
        if ( zone == null) throw new NullPointerException("null zone object");
        if ( policy == null) throw new NullPointerException("null policy object");

        if (duration.isZero() || duration.isNegative()) {
            throw new IllegalArgumentException("Duration must be positive");
        }

        LocalDateTime localDateTime = LocalDateTime.of(date, localStart);

        if (zone.getRules().getValidOffsets(localDateTime).isEmpty()) {
            throw new DateTimeException("Local time does not exist in this zone");
        }

        ZonedDateTime zonedStart = localDateTime.atZone(zone);

        if (policy == OverlapPolicy.EARLIER) {
            zonedStart = zonedStart.withEarlierOffsetAtOverlap();
        } else {
            zonedStart = zonedStart.withLaterOffsetAtOverlap();
        }

        Instant start = zonedStart.toInstant();
        Instant end = start.plus(duration);

        return new MaintenanceWindow(start, end);
    }

    public List<MaintenanceWindow> upcomingWindows(int days, LocalTime localStart, Duration duration,
            ZoneId zone, OverlapPolicy policy) {

        if (localStart == null) throw new NullPointerException("null localStart object");
        if (duration == null) throw new NullPointerException("null duration object");
        if (zone == null) throw new NullPointerException("null zone object");
        if (policy == null) throw new NullPointerException("null policy object");

        if (days < 0) {
            throw new IllegalArgumentException("Days cannot be negative");
        }

        List<MaintenanceWindow> windows = new ArrayList<>();

        LocalDate today = LocalDate.now(clock.withZone(zone));

        for (int i = 0; i < days; i++) {
            LocalDate date = today.plusDays(i);

            windows.add(nextWindow(date, localStart, duration, zone, policy));
        }

        return windows;
    }

}
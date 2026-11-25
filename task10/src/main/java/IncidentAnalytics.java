import java.math.BigInteger;
import java.time.Duration;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

public class IncidentAnalytics {

    public static Duration totalDowntime(List<Incident> incidents) {

        if (incidents == null) throw new NullPointerException("null incedents object");

        Duration total = Duration.ZERO;

        for (Incident incident : incidents) {
            total = total.plus(incident.duration());
        }

        return total;
    }

    public static Duration averageDowntime(List<Incident> incidents) {

        if (incidents == null) throw new NullPointerException("null incedents object");

        if (incidents.isEmpty()) return Duration.ZERO;

        BigInteger nanosPerSecond = BigInteger.valueOf(1_000_000_000L);
        BigInteger totalNanos = BigInteger.ZERO;

        for (Incident incident : incidents) {

            if (incident == null) throw new NullPointerException("null incident object");


            Duration duration = incident.duration();

            BigInteger nanos = BigInteger.valueOf(duration.getSeconds())
                    .multiply(nanosPerSecond)
                    .add(BigInteger.valueOf(duration.getNano()));

            totalNanos = totalNanos.add(nanos);
        }

        BigInteger averageNanos = totalNanos.divide(
                BigInteger.valueOf(incidents.size())
        );

        BigInteger[] parts = averageNanos.divideAndRemainder(nanosPerSecond);

        return Duration.ofSeconds(
                parts[0].longValueExact(),
                parts[1].longValueExact()
        );
    }

   public static Optional<Incident> longestIncident(List<Incident> incidents) {

        if (incidents == null) throw new NullPointerException("null incedents object");

        return incidents.stream().min(Comparator
                .comparing(Incident::duration).reversed()
                .thenComparing(Incident::startedAt)
                .thenComparing(Incident::id));
    }

    public static Map<LocalDate, List<Incident>> groupByStartDate(List<Incident> incidents, ZoneId zone) {

        if (incidents == null) throw new NullPointerException("null incedents object");
        if (zone == null) throw new NullPointerException("null zone object");

        return incidents.stream()
                .sorted(Comparator.comparing(Incident::startedAt)
                        .thenComparing(Incident::id))
                .collect(Collectors.groupingBy(
                        incident -> incident.startedAt().atZone(zone).toLocalDate(),
                        TreeMap::new,
                        Collectors.toList()
                ));
    }

}

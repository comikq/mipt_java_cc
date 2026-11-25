import org.junit.jupiter.api.Test;

import java.time.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class IncidentAnalyticsTest {

    @Test
    void shouldCalculateDowntime() {
        Instant start = Instant.parse("2024-01-01T00:00:00Z");

        Incident a = new Incident("A", "service", start, start.plusNanos(1));
        Incident b = new Incident("B", "service", start, start.plusNanos(2));

        List<Incident> incidents = List.of(a, b);

        assertEquals(
                Duration.ofNanos(3),
                IncidentAnalytics.totalDowntime(incidents)
        );

        // 3 / 2 = 1.5 нс, округление до 1 нс
        assertEquals(
                Duration.ofNanos(1),
                IncidentAnalytics.averageDowntime(incidents)
        );
    }

    @Test
    void shouldHandleEmptyList() {
        List<Incident> incidents = List.of();

        assertEquals(Duration.ZERO, IncidentAnalytics.totalDowntime(incidents));
        assertEquals(Duration.ZERO, IncidentAnalytics.averageDowntime(incidents));
        assertTrue(IncidentAnalytics.longestIncident(incidents).isEmpty());
    }

    @Test
    void shouldChooseLongestIncident() {
        Instant start = Instant.parse("2024-01-01T00:00:00Z");

        Incident a = new Incident("B", "service", start, start.plusSeconds(60));
        Incident b = new Incident("A", "service", start, start.plusSeconds(60));
        Incident c = new Incident("C", "service",
                start.plusSeconds(10), start.plusSeconds(70));

        assertEquals(
                b,
                IncidentAnalytics.longestIncident(List.of(a, b, c)).orElseThrow()
        );
    }

    @Test
    void shouldGroupByBerlinDate() {
        Incident a = new Incident(
                "A", "service",
                Instant.parse("2024-03-30T23:30:00Z"),
                Instant.parse("2024-03-31T00:00:00Z")
        );

        Map<LocalDate, List<Incident>> groups =
                IncidentAnalytics.groupByStartDate(
                        List.of(a), ZoneId.of("Europe/Berlin")
                );

        assertTrue(groups.containsKey(LocalDate.of(2024, 3, 31)));
        assertEquals(List.of(a), groups.get(LocalDate.of(2024, 3, 31)));
    }
}
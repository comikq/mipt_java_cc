import org.junit.jupiter.api.Test;

import java.time.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class LegacyDateAdapterTest {

    @Test
    void shouldPreserveInstant() {
        Instant instant = Instant.parse("2024-03-31T01:00:00.123Z");

        Date date = LegacyDateAdapter.toDate(instant);

        assertEquals(instant, LegacyDateAdapter.toInstant(date));
    }

    @Test
    void shouldPreserveCalendarZone() {
        ZonedDateTime original = ZonedDateTime.of(
                2024, 10, 27, 2, 30, 0, 0,
                ZoneId.of("Europe/Berlin")
        );

        GregorianCalendar calendar =
                LegacyDateAdapter.toGregorianCalendar(original);

        ZonedDateTime restored =
                LegacyDateAdapter.toZonedDateTime(calendar);

        assertEquals(original.toInstant(), restored.toInstant());
        assertEquals(original.getZone(), restored.getZone());
    }
}
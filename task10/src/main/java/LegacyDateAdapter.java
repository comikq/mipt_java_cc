import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.GregorianCalendar;

public final class LegacyDateAdapter {

    public static Instant toInstant(Date date) {
        if (date == null) throw new NullPointerException("null date onject");
        return date.toInstant();
    }

    public static Date toDate(Instant instant) {
        if (instant == null) throw new NullPointerException("null instant object");
        return Date.from(instant);
    }

    public static ZonedDateTime toZonedDateTime(GregorianCalendar calendar) {
        if (calendar == null) throw new NullPointerException("null calendar object");
        return calendar.toZonedDateTime();
    }

    public static GregorianCalendar toGregorianCalendar(ZonedDateTime dateTime) {
        if (dateTime == null) throw new NullPointerException("null dateTime object");
        return GregorianCalendar.from(dateTime);
    }
}
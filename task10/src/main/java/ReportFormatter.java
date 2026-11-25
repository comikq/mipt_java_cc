import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class ReportFormatter {
    public static String formatWindow(MaintenanceWindow window, ZoneId zone, Locale locale) {

        if (window == null) throw new NullPointerException("null window object");
        if (zone == null) throw new NullPointerException("null zone object");
        if (locale == null) throw new NullPointerException("null locale object");

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(
                "dd.MM.uuuu HH:mm XXX (VV)", locale
        );

        String start = window.start()
                .atZone(zone)
                .format(formatter);

        String end = window.end()
                .atZone(zone)
                .format(formatter);

        return start + " – " + end;
    }
}

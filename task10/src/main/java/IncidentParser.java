import java.time.Instant;

public class IncidentParser {

    public static Incident parse(String line) {

        if (line == null) throw new NullPointerException("null line object");

        String[] fields = line.split(";", -1);

        if (fields.length != 4) throw new IllegalArgumentException("Incorrect line");

        for (String field : fields) {
            if (field.isBlank()) {
                throw new IllegalArgumentException("Empty field");
            }
        }

        Instant startedAt = Instant.parse(fields[2]);
        Instant resolvedAt = Instant.parse(fields[3]);

        if (resolvedAt.isBefore(startedAt)) throw new IllegalArgumentException("Incorrect time order");

        return new Incident(
                fields[0],
                fields[1],
                startedAt,
                resolvedAt
        );
    }
}

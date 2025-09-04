package duke;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
/**
 * Represents an Event task with a start and end time.
 */
public class Event extends Task {

    protected String from;
    protected String to;
    protected LocalDateTime fromDateTime;
    protected LocalDateTime toDateTime;
    private boolean isDateTime;

    public Event(String description, String from, String to) {
        super(description);
        this.from = from.trim();
        this.to = to.trim();
        isDateTime = false;
    }

    public Event(String description, LocalDateTime fromDateTime , LocalDateTime toDateTime) {
        super(description);
        this.fromDateTime = fromDateTime;
        this.toDateTime = toDateTime;
        isDateTime = true;
    }

    @Override
    public String toString() {
        if (isDateTime) {
            return "[E]" + super.toString() + " (from: " + formatDateTimeWithOrdinal(this.fromDateTime)
                    + " to: " + formatDateTimeWithOrdinal(this.toDateTime) + ")";
        } else {
            return "[E]" + super.toString() + " (from: " + this.from + " to: " + this.to + ")";
        }
    }

    @Override
    public String toFileString() {
        if (isDateTime) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy HHmm");
            return "E | " + super.toFileString() + " | " + fromDateTime.format(formatter)
                    + " | " + toDateTime.format(formatter);

        } else {
            return "E | " + super.toFileString() + " | " + this.from + " | " + this.to;
        }
    }

    /**
     * Converts a LocalDateTime object into a string using the pattern "d/M/yyyy HHmm".
     *
     * @param dateTime the LocalDateTime to format
     * @return a string representing the formatted date and time
     */
    public String formatDateTimeWithOrdinal(LocalDateTime dateTime) {
        int day = dateTime.getDayOfMonth();
        String ordinal = getOrdinal(day);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d'" + ordinal + " of 'MMMM yyyy, ha");
        return dateTime.format(formatter).toLowerCase();
    }

    /**
     * Returns the correct suffix to a particular date.
     *
     * @param day Day of the month.
     * @return date as a string: dth of M, yyyy hh.
     */
    public String getOrdinal(int day) {
        if (day >= 11 && day <= 13) {
            return "th";
        }
        switch (day % 10) {
        case 1: return "st";
        case 2: return "nd";
        case 3: return "rd";
        default: return "th";
        }
    }
}

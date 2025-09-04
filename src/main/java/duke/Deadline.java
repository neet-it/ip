package duke;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents a Deadline task with a due date and time.
 */
public class Deadline extends Task {

    protected LocalDateTime byDateTime;
    protected String by;
    private boolean isDateTime;

    public Deadline(String description, LocalDateTime byDateTime) {
        super(description);
        this.byDateTime = byDateTime;
        this.isDateTime = true;
    }

    public Deadline(String description, String by) {
        super(description);
        this.by = by.trim();
        this.isDateTime = false;
    }

    @Override
    public String toString() {
        if (isDateTime) {
            return "[D]" + super.toString() + " (by: " + formatDateTimeWithOrdinal(this.byDateTime) + ")";
        } else {
            return "[D]" + super.toString() + " (by: " + this.by + ")";
        }
    }

    @Override
    public String toFileString() {
        if (isDateTime) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy HHmm");
            return "D | " + super.toFileString() + " | " + this.byDateTime.format(formatter);

        } else {
            return "D | " + super.toFileString() + " | " + this.by;
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

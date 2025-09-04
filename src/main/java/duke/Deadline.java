package duke;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Deadline extends Task {

    protected LocalDateTime byDateTime;
    protected String by;
    public boolean isDateTime;

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
        if(isDateTime) {
            return "[D]" + super.toString() + " (by: " + formatDateTimeWithOrdinal(this.byDateTime) + ")";
        } else {
            return "[D]" + super.toString() + " (by: " + this.by + ")";
        }
    }

    @Override
    public String toFileString() {
        if(isDateTime) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy HHmm");
            return "D | " + super.toFileString() + " | " +  this.byDateTime.format(formatter);

        } else {
            return "D | " + super.toFileString() + " | " + this.by;
        }
    }

    /**
     * Formats d/M/yyyy hh -> dth of M, yyyy hh.
     *
     * @param dateTime is the LocalDateTime object to be returned in String format.
     * @return date as dth of M, yyyy hh.
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
     * @return date as dth of M, yyyy hh.
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
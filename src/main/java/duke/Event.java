package duke;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Event extends Task {

    protected String from;
    protected String to;
    protected LocalDateTime fromDateTime;
    protected LocalDateTime toDateTime;
    public boolean isDateTime;

    public Event(String description, String from, String to) {
        super(description);
        this.from = from.trim();
        this.to = to.trim();
        isDateTime = false;
    }

    public Event(String description,LocalDateTime fromDateTime , LocalDateTime toDateTime) {
        super(description);
        this.fromDateTime = fromDateTime;
        this.toDateTime = toDateTime;
        isDateTime = true;
    }

    @Override
    public String toString() {
        if(isDateTime) {
            return "[E]" + super.toString() + " (from: " + formatDateTimeWithOrdinal(this.fromDateTime)
                    + " to: " + formatDateTimeWithOrdinal(this.toDateTime) + ")";
        } else {
            return "[E]" + super.toString() + " (from: " + this.from + " to: " + this.to + ")";
        }
    }

    @Override
    public String toFileString() {
        if(isDateTime) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy HHmm");
            return "E | " +  super.toFileString() + " | " +  fromDateTime.format(formatter) + " | " + toDateTime.format(formatter);

        } else {
            return "E | " + super.toFileString() + " | " + this.from + " | " + this.to;
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
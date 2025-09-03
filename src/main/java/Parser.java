import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Parser {

    public int parseMarkCommand(String command) {
        return Integer.parseInt(command.substring(5).trim()) - 1;
    }

    public int parseUnmarkCommand(String command) {
        return Integer.parseInt(command.substring(7).trim()) - 1;
    }

    public int parseDeleteCommand(String command) {
        return Integer.parseInt(command.substring(7).trim()) - 1;
    }

    public Task parseTodoCommand(String command) {
        Task task = null ;
        try {
            String s = command.substring(5).trim();
            task = new Todo(s);
        } catch (Exception e){
            System.out.println("\t" + "INVALID TODO TASK");
        }
        return task;
    }

    public Task parseDeadlineCommand(String command) {
        Task task = null;
        try {
            String s = command.substring(9).trim();
            String[] bySplit = s.split("/by");
            String desc = bySplit[0].trim();
            String by = bySplit[1].trim();

            try {
                LocalDateTime byDateTime = parseDateTime(by);
                task = new Deadline(desc, byDateTime);
            } catch (DateTimeParseException e) {
                task = new Deadline(desc, by);
            }

        } catch (Exception e) {
            System.out.println("\t" + "INVALID DEADLINE TASK");
        }
        return task;
    }

    public Task parseEventCommand(String command) {
        Task task = null;
        try {
            String s = command.substring(6).trim();
            String[] fromSplit = s.split("/from");
            String desc = fromSplit[0].trim();

            String[] toSplit = fromSplit[1].split("/to");
            String from = toSplit[0];
            String to = toSplit[1];

            try {
                LocalDateTime fromDateTime = parseDateTime(from);
                LocalDateTime toDateTime = parseDateTime(to);
                task = new Event(desc, fromDateTime, toDateTime);
            } catch (DateTimeParseException e) {
                task = new Event(desc, from, to);
            }

        } catch (Exception e) {
            System.out.println("\t" + "INVALID EVENT TASK");
        }
        return task;
    }



    /**
     * Returns a string as LocalDateTime object.
     *
     * @param timeString to store the time as a string.
     * @throws DateTimeParseException if the timeString is invalid time format.
     */
    public LocalDateTime parseDateTime(String timeString) throws DateTimeParseException {
        timeString = timeString.trim();

        if (timeString.contains(" ")) {
            String[] parts = timeString.split(" ");
            String dd = parts[0];

            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("d/M/yyyy");
            LocalDate date = LocalDate.parse(dd, dateFormatter);

            if (parts[1].length() == 4) {
                int hour = Integer.parseInt(parts[1].substring(0, 2));
                int minute = Integer.parseInt(parts[1].substring(2));
                return LocalDateTime.of(date, java.time.LocalTime.of(hour, minute));
            } else {
                throw new DateTimeParseException("Invalid time!", timeString, 0);
            }
        } else {
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("d/M/yyyy");
            return LocalDate.parse(timeString, dateFormatter).atStartOfDay();
        }
    }
}

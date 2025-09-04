package duke;
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

            if (s.startsWith("/by")) {
                throw new DukeException("Deadline description cannot be empty!");
            }

            String[] bySplit = s.split("/by");

            if (bySplit.length < 2) {
                throw new DukeException("Invalid deadline format! Use: deadline <description> /by <time>");
            }

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
            if (s.startsWith("/from")) {
                throw new DukeException("Event description cannot be empty!");
            }

            String[] fromSplit = s.split("/from");
            if (fromSplit.length < 2) {
                throw new DukeException("Invalid event format! Use: event <description> /from <time> /to <time>");
            }

            String desc = fromSplit[0].trim();
            String[] toSplit = fromSplit[1].trim().split("/to");
            if (toSplit.length < 2) {
                throw new DukeException("Invalid event format! Missing /to parameter");
            }

            String from = toSplit[0].trim();
            String to = toSplit[1].trim();

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
    public static LocalDateTime parseDateTime(String timeString) throws DateTimeParseException {
        timeString = timeString.trim();

        if (timeString.contains(" ")) {
            String[] parts = timeString.split(" ");
            String dd = parts[0].trim();
            String tt = parts[1].trim();

            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("d/M/yyyy");
            LocalDate date = LocalDate.parse(dd, dateFormatter);

            if (tt.length() == 4) {
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

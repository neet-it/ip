package duke;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a storage manager that handles saving and loading tasks
 * to and from a file. Supports Todo, Deadline, and Event tasks.
 */
public class Storage {

    private String filepath;

    public Storage(String filepath) {
        this.filepath = filepath;
    }

    /**
     * Saves the tasks in the file.
     */
    public ArrayList<Task> loadtasks() {
        ArrayList<Task> list = new ArrayList<>();
        Path path = Paths.get(filepath);
        if (!Files.exists(path)) {
            return null;
        }

        try {
            List<String> file = Files.readAllLines(path);
            for (String line : file) {
                Task task = parseTask(line);
                if (task != null) {
                    list.add(task);
                }
            }
        } catch (IOException e) {
            System.out.println("\t" + "Oh no! Could not load tasks from your previous list.");
        }

        return list;
    }

    /**
     * Updates the  task file.
     *
     * @param list stores the new arraylist of tasks to store in the file.
     */
    public void saveTasks(ArrayList<Task> list) {
        try {
            File dir = new File("./data");
            if (!dir.exists()) {
                dir.mkdir();
            }

            FileWriter writer = new FileWriter(filepath);

            for (Task task : list) {
                writer.write(task.toFileString() + "\n");
            }
            writer.close();
        } catch (IOException e) {
            System.out.println("\t" + "Tasks could not be saved to file");
        }
    }

    /**
     * Converts the file string into task objects.
     *
     * @param line stores the task string to be converted into objects.
     */
    public Task parseTask(String line) {
        String[] parts = line.split(" \\| ");
        if (parts.length < 3) {
            return null;
        }
        String taskType = parts[0];
        boolean isDone = parts[1].equals("1");
        String description = parts[2];
        Task task;

        switch (taskType) {
        case "T":
            task = new Todo(description);
            break;
        case "D":
            String by = parts[3].trim();
            task = parseDeadlineStorageTask(description, by);
            break;
        case "E":
            String from = parts[3].trim();
            String to = parts[4].trim();
            task = parseEventStorageTask(description, from, to);
            break;
        default:
            task = null;
        }
        if (task != null && isDone) {
            task.markAsDone();
        }
        return task;
    }

    /**
     * Returns deadline task object.
     *
     * @param description stores the task description string to be converted into objects.
     * @param by stores the task by time as a string to be converted into objects.
     */
    public Task parseDeadlineStorageTask(String description, String by) {
        try {
            LocalDateTime byDateTime = parseDateTime(by);
            return new Deadline(description, byDateTime);
        } catch (DateTimeParseException e) {
            return new Deadline(description, by);
        }
    }

    /**
     * Returns deadline task object.
     *
     * @param description stores the task description string to be converted into objects.
     * @param from stores the task from time as a string to be converted into objects.
     * @param to stores the task to time as a string to be converted into objects.
     */
    public Task parseEventStorageTask(String description, String from, String to) {
        try {
            LocalDateTime fromDateTime = parseDateTime(from);
            LocalDateTime toDateTime = parseDateTime(to);
            return new Event(description, fromDateTime, toDateTime);
        } catch (DateTimeParseException e) {
            return  new Event(description, from, to);
        }
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

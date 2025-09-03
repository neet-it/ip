import java.util.ArrayList;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Cortex {
    public ArrayList<Task> list;
    private Storage storage;
    private Ui ui;


    public Cortex() {
        this.storage = new Storage("./data/duke.txt");
        this.list = storage.loadtasks();
        ui = new Ui();

    }
    public static void main(String[] args) {
        Cortex ob = new Cortex();
        ob.run();
    }

    /**
     * Starts the chatbot.
     */
    public void run() {


        ui.printHello();

        String command = ui.readCommand();


        while (!command.equalsIgnoreCase("bye")) {
            ui.printHorizontalLine();

            if (command.equalsIgnoreCase("list")) {
                ui.printList(list);

            } else if (command.startsWith("mark")) {
                markTask(command);

            } else if (command.startsWith("unmark")) {
                unmarkTask(command);

            }else if(command.startsWith("delete")) {
                deleteTask(command);

            } else {
                Task task = new Task(command);
                boolean isValidTask = false;
                int c = 0;
                if (command.startsWith("todo")) {
                    c++;
                    try {
                        String s = command.substring(5).trim();
                        task = new Todo(s);
                        isValidTask = true;
                    } catch (Exception e){
                        ui.printError("INVALID TODO TASK");
                    }

                } else if (command.startsWith("deadline")) {
                    c++;
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

                        isValidTask = true;
                    } catch (Exception e) {
                        ui.printError("INVALID DEADLINE TASK");
                    }

                } else if (command.startsWith("event")) {
                    c++;
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
                        
                        isValidTask = true;
                    } catch (Exception e) {
                        ui.printError("INVALID EVENT TASK");
                    }
                }

                if(isValidTask) {
                    addTask(task);
                } else if (c == 0){
                    ui.printError("INVALID TASK TYPE");
                }

            }

            ui.printHorizontalLine();
            command = ui.readCommand();
        }

        ui.printBye();
    }




    /**
     * Marks a task as done if the task number exists and update task file.
     *
     * @param command contains the information of the task number.
     */
    public void markTask(String command){
        try {
            int  i= Integer.parseInt(command.substring(5).trim()) - 1;
            if(i < 0 || i >= list.size()) {
                ui.printError("Invalid Task! Cannot mark task.");
            } else {
                Task t = list.get(i);

                t.markAsDone();
                ui.printMarked(t);
            }
        } catch (NumberFormatException e) {
            ui.printError("Invalid Task! Cannot mark task.");
        } catch (IndexOutOfBoundsException e) {
            ui.printError("Invalid Task! Cannot mark task.");
        }

        storage.saveTasks(list);
    }

    /**
     * Unmarks a task if the task number exists and updates task file.
     *
     * @param command contains the information of the task number.
     */
    public void unmarkTask(String command) {
        try {
            int i = Integer.parseInt(command.substring(7).trim()) - 1;

            if(i < 0 || i >= list.size()) {
                ui.printError("Invalid Task! Cannot unmark task.");
            } else {
                Task t = list.get(i);

                t.unmarkAsDone();
                ui.printUnmarked(t);
            }

        } catch (NumberFormatException e) {
            ui.printError("Invalid Task! Cannot unmark task.");
        } catch (IndexOutOfBoundsException e) {
            ui.printError("Invalid Task! Cannot unmark task.");
        }
        storage.saveTasks(list);

    }

    /**
     * Deletes a task as done if the task number exists.
     *
     * @param command contains the information of the task number and updates task fileli.
     */
    public void deleteTask(String command) {
        try {
            int i = Integer.parseInt(command.substring(7).trim()) - 1;

            if(i < 0 || i >= list.size()) {
                ui.printError("Invalid Task! Cannot delete task.");
            } else {
                Task t = list.get(i);
                list.remove(i);
               ui.printDeletedTask(t, list);
            }

        } catch (NumberFormatException e) {
            ui.printError("Invalid Task! Cannot unmark task.");
        } catch (IndexOutOfBoundsException e) {
            ui.printError("Invalid Task! Cannot unmark task.");
        }

        storage.saveTasks(list);
    }

    /**
     * Adds a task to the list of tasks and the task file.
     *
     * @param task the Task object needed to be added to list.
     */
    public void addTask(Task task) {
        list.add(task);
        ui.printAddedTask(task, list);
        storage.saveTasks(list);
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
            String tt = parts[1];

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
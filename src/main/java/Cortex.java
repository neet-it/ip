import java.util.ArrayList;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

public class Cortex {
    public ArrayList<Task> list;
    private Storage storage;
    private Ui ui;
    private Parser parser;


    public Cortex() {
        this.storage = new Storage("./data/duke.txt");
        this.list = storage.loadtasks();
        ui = new Ui();
        parser = new Parser();

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
                    task = parser.parseTodoCommand(command);

                } else if (command.startsWith("deadline")) {
                    c++;
                    task = parser.parseDeadlineCommand(command);

                } else if (command.startsWith("event")) {
                    c++;
                    task = parser.parseEventCommand(command);
                }

                if(task != null) {
                    addTask(task);
                }
                if (c == 0){
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
            int  i = parser.parseMarkCommand(command);

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
            int i = parser.parseUnmarkCommand(command);

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
            int i = parser.parseDeleteCommand(command);

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

}
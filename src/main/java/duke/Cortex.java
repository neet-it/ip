package duke;
import java.util.ArrayList;

/**
 * Represents a chatbot that stores tasks.
 */
public class Cortex {
    private TaskList list;
    private Storage storage;
    private Ui ui;
    private Parser parser;


    public Cortex() {
        this.storage = new Storage("./data/duke.txt");
        this.list = new TaskList(storage.loadtasks());
        ui = new Ui();
        parser = new Parser();

    }

    /**
     * Starts the chatbot.
     */
    public String getResponse(String command) {
        StringBuilder response = new StringBuilder();

        if (command.equalsIgnoreCase("bye")) {
            response.append(ui.printBye());
        } else if (command.equalsIgnoreCase("list")) {
            response.append(ui.printList(list.getAllTasks()));
        } else if (command.startsWith("mark")) {
            response.append(markTask(command));
        } else if (command.startsWith("unmark")) {
            response.append(unmarkTask(command));
        } else if (command.startsWith("delete")) {
            response.append(deleteTask(command));
        } else if (command.startsWith("find")) {
            response.append(findTask(command));
        } else {
            Task task = parser.parseTaskCommand(command);
            if (task != null) {
                response.append(addTask(task));
            } else {
                response.append(ui.printError("INVALID TASK TYPE"));
            }
        }
        response.append(ui.printHorizontalLine());
        return response.toString();
    }


    /**
     * Marks a task as done if the task number exists and update task file.
     *
     * @param command contains the information of the task number.
     */
    public StringBuilder markTask(String command) {
        try {
            int i = parser.parseMarkCommand(command);

            try {
                Task t = list.getTask(i);
                t.markAsDone();
                storage.saveTasks(list.getAllTasks());
                return ui.printMarked(t);
            } catch (DukeException e) {
                return ui.printError(e.getMessage());
            }
        } catch (NumberFormatException e) {
            return ui.printError("Invalid Task! Cannot mark task.");
        } catch (IndexOutOfBoundsException e) {
            return ui.printError("Invalid Task! Cannot mark task.");
        }
    }

    /**
     * Unmarks a task if the task number exists and updates task file.
     *
     * @param command contains the information of the task number.
     */
    public StringBuilder unmarkTask(String command) {
        try {
            int i = parser.parseUnmarkCommand(command);

            try {
                Task t = list.getTask(i);
                t.unmarkAsDone();
                storage.saveTasks(list.getAllTasks());
                return ui.printUnmarked(t);
            } catch (DukeException e) {
                return ui.printError(e.getMessage());
            }

        } catch (NumberFormatException e) {
            return ui.printError("Invalid Task! Cannot unmark task.");
        } catch (IndexOutOfBoundsException e) {
            return ui.printError("Invalid Task! Cannot unmark task.");
        }
    }

    /**
     * Deletes a task as done if the task number exists.
     *
     * @param command contains the information of the task number and updates task file.
     */
    public StringBuilder deleteTask(String command) {
        try {
            int i = parser.parseDeleteCommand(command);

            try {
                Task t = list.getTask(i);
                list.deleteTask(i);
                storage.saveTasks(list.getAllTasks());
                return ui.printDeletedTask(t, list.getAllTasks());
            } catch (DukeException e) {
                return ui.printError(e.getMessage());
            }
        } catch (NumberFormatException e) {
            return ui.printError("Invalid Task! Cannot unmark task.");
        } catch (IndexOutOfBoundsException e) {
            return ui.printError("Invalid Task! Cannot unmark task.");
        }
    }

    /**
     * Adds a task to the list of tasks and the task file.
     *
     * @param task the Task object needed to be added to list.
     */
    public StringBuilder addTask(Task task) {
        list.addTask(task);
        storage.saveTasks(list.getAllTasks());
        return ui.printAddedTask(task, list.getAllTasks());
    }

    /**
     * Finds tasks that match the find command.
     *
     */
    public StringBuilder findTask(String command) {
        try {
            String key = parser.parseFindCommand(command);

            if (key.isEmpty()) {
                return ui.printError("Invalid Search! Please specify item.");
            }

            ArrayList<Task> findList = new ArrayList<>();

            for (Task task : list.getAllTasks()) {
                if (task.description.toLowerCase().contains(key.toLowerCase())) {
                    findList.add(task);
                }
            }
            return ui.printFoundList(findList, key);

        } catch (StringIndexOutOfBoundsException e) {
            return ui.printError("Invalid Task! No tasks to find.");
        }
    }

    public String getHello() {
        return ui.printHello();
    }
}

package duke;
import java.util.ArrayList;

/**
 * Represents a list of tasks and provides methods to manage them.
 * Encapsulates operations such as adding, deleting, marking, and retrieving tasks.
 */
public class TaskList {

    private ArrayList<Task> list;

    public TaskList(ArrayList<Task> list) {
        this.list = list;
    }

    public void addTask(Task task) {
        list.add(task);
    }

    /**
     * Deletes the task at the specified index from the task list.
     *
     * @param index the index of the task to delete.
     * @return the Task that was removed from the list.
     * @throws DukeException if the index is invalid.
     */
    public Task deleteTask(int index) throws DukeException {
        if (index < 0 || index >= list.size()) {
            throw new DukeException("Invalid task index");
        }
        return list.remove(index);
    }

    /**
     * Returns the task at the specified index from the task list.
     *
     * @param index the index of the task to return.
     * @return the Task that was removed from the list.
     * @throws DukeException if the index is invalid.
     */
    public Task getTask(int index) throws DukeException {
        if (index < 0 || index >= list.size()) {
            throw new DukeException("Invalid Task!");
        }
        return list.get(index);
    }

    public ArrayList<Task> getAllTasks() {
        return new ArrayList<>(list);
    }

}

package duke;
import java.util.ArrayList;

public class TaskList {

    private ArrayList<Task> list;

    public TaskList(ArrayList<Task> list) {
        this.list = list;
    }

    public void addTask(Task task) {
        list.add(task);
    }

    public Task deleteTask(int index) throws DukeException {
        if (index < 0 || index >= list.size()) {
            throw new DukeException("Invalid task index");
        }
        return list.remove(index);
    }

    public Task getTask(int index) throws DukeException {
        if (index < 0 || index >= list.size()) {
            throw new DukeException("Invalid Task!");
        }
        return list.get(index);
    }

    public int getSize() {
        return this.list.size();
    }

    public ArrayList<Task> getAllTasks() {
        return new ArrayList<>(list);
    }

}

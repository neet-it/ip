package duke;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;


public class TaskListTest {
    private TaskList taskList;
    private ArrayList<Task> tasks = new ArrayList<>();

    //Solution below Ai-assisted using ChatGPT
    @BeforeEach
    public void setUp() {
        tasks = new ArrayList<>();
        tasks.add(new Todo("Test task 1"));
        tasks.add(new Deadline("Test task 2", "5/9/2025"));
        tasks.add(new Event("Meeting", "Friday", "Saturday"));
        taskList = new TaskList(tasks);
    }

    @Test
    public void getTask_validIndex_returnsCorrectTask() throws DukeException {
        Task task = taskList.getTask(1);
        assertEquals("Test task 2", task.getDescription());

        Task deletedTask = taskList.deleteTask(0);
        assertEquals("Test task 1", deletedTask.getDescription());
        assertEquals(2, taskList.getAllTasks().size());
    }

    //Solution below Ai-assisted using ChatGPT
    @Test
    public void getTask_invalidIndex_throwsDukeException() {
        DukeException exception = assertThrows(DukeException.class, () -> {
            taskList.getTask(-1);
        });
        assertEquals("Invalid Task!", exception.getMessage());

        DukeException exception1 = assertThrows(DukeException.class, () -> {
            taskList.getTask(5);
        });
        assertEquals("Invalid Task!", exception1.getMessage());

        DukeException exception2 = assertThrows(DukeException.class, () -> {
            taskList.deleteTask(-1);
        });
        assertEquals("Invalid task index", exception2.getMessage());

        DukeException exception3 = assertThrows(DukeException.class, () -> {
            taskList.deleteTask(10);
        });
        assertEquals("Invalid task index", exception3.getMessage());
    }

    //Solution below Ai-assisted using ChatGPT
    @Test
    public void getAllTasks_returnsCopyOfList() {
        ArrayList<Task> allTasks = taskList.getAllTasks();
        assertEquals(3, allTasks.size());

        allTasks.remove(0);
        assertEquals(2, allTasks.size());
        assertEquals(3, taskList.getAllTasks().size());

    }
}

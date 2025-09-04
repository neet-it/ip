package duke;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class TaskListTester {
    private TaskList taskList;
    private ArrayList<Task> initialTasks;

    @BeforeEach
    public void setUp() {
        initialTasks = new ArrayList<>();
        initialTasks.add(new Todo("Test task 1"));
        initialTasks.add(new Todo("Test task 2"));
        initialTasks.add(new Todo("Test task 3"));
        taskList = new TaskList(initialTasks);
    }

    @Test
    public void testTaskListConstructor() {
        // Test that constructor properly initializes with given list
        assertNotNull(taskList);
        assertEquals(3, taskList.getAllTasks().size());
        assertEquals(initialTasks, taskList.getAllTasks());

        // Test constructor with empty list
        TaskList emptyList = new TaskList(new ArrayList<>());
        assertNotNull(emptyList);
        assertTrue(emptyList.getAllTasks().isEmpty());

        // Test constructor with null list (should handle gracefully)
        TaskList nullList = new TaskList(null);
        assertNotNull(nullList);
        // Depending on implementation, might need to handle null in constructor
    }

    @Test
    public void testAddTask() {
        // Test adding a new task
        Todo newTask = new Todo("New task");
        taskList.addTask(newTask);

        assertEquals(4, taskList.getAllTasks().size());
        assertTrue(taskList.getAllTasks().contains(newTask));

        // Test adding multiple tasks
        Todo anotherTask = new Todo("Another task");
        taskList.addTask(anotherTask);
        assertEquals(5, taskList.getAllTasks().size());
        assertTrue(taskList.getAllTasks().contains(anotherTask));

        // Test adding null task (should be handled based on ArrayList behavior)
        taskList.addTask(null);
        assertEquals(6, taskList.getAllTasks().size());
        assertTrue(taskList.getAllTasks().contains(null));
    }

    @Test
    public void testDeleteTask_validIndices() throws DukeException {
        // Test deleting first task
        Task removedTask1 = taskList.deleteTask(0);
        assertNotNull(removedTask1);
        assertEquals("Test task 1", removedTask1.description);
        assertEquals(2, taskList.getAllTasks().size());
        assertFalse(taskList.getAllTasks().contains(removedTask1));

        // Test deleting middle task
        Task removedTask2 = taskList.deleteTask(0); // Now list has [task2, task3], removing first gives task2
        assertEquals("Test task 2", removedTask2.description);
        assertEquals(1, taskList.getAllTasks().size());

        // Test deleting last task
        Task removedTask3 = taskList.deleteTask(0); // Now list has [task3], removing first gives task3
        assertEquals("Test task 3", removedTask3.description);
        assertTrue(taskList.getAllTasks().isEmpty());
    }

    @Test
    public void testDeleteTask_invalidIndices() {
        // Test negative index
        DukeException exception1 = assertThrows(DukeException.class, () -> {
            taskList.deleteTask(-1);
        });
        assertEquals("Invalid task index", exception1.getMessage());

        // Test index equal to size
        DukeException exception2 = assertThrows(DukeException.class, () -> {
            taskList.deleteTask(taskList.getAllTasks().size());
        });
        assertEquals("Invalid task index", exception2.getMessage());

        // Test index greater than size
        DukeException exception3 = assertThrows(DukeException.class, () -> {
            taskList.deleteTask(100);
        });
        assertEquals("Invalid task index", exception3.getMessage());

        // Verify no tasks were deleted due to errors
        assertEquals(3, taskList.getAllTasks().size());
    }

    @Test
    public void testDeleteTask_emptyList() {
        TaskList emptyList = new TaskList(new ArrayList<>());

        // Test any index on empty list
        DukeException exception = assertThrows(DukeException.class, () -> {
            emptyList.deleteTask(0);
        });
        assertEquals("Invalid task index", exception.getMessage());
    }

    @Test
    public void testGetTask_validIndices() throws DukeException {
        // Test getting first task
        Task task1 = taskList.getTask(0);
        assertNotNull(task1);
        assertEquals("Test task 1", task1.description);

        // Test getting middle task
        Task task2 = taskList.getTask(1);
        assertNotNull(task2);
        assertEquals("Test task 2", task2.description);

        // Test getting last task
        Task task3 = taskList.getTask(2);
        assertNotNull(task3);
        assertEquals("Test task 3", task3.description);

        // Verify original list is unchanged
        assertEquals(3, taskList.getAllTasks().size());
    }

    @Test
    public void testGetTask_invalidIndices() {
        // Test negative index
        DukeException exception1 = assertThrows(DukeException.class, () -> {
            taskList.getTask(-1);
        });
        assertEquals("Invalid Task!", exception1.getMessage());

        // Test index equal to size
        DukeException exception2 = assertThrows(DukeException.class, () -> {
            taskList.getTask(taskList.getAllTasks().size());
        });
        assertEquals("Invalid Task!", exception2.getMessage());

        // Test index greater than size
        DukeException exception3 = assertThrows(DukeException.class, () -> {
            taskList.getTask(100);
        });
        assertEquals("Invalid Task!", exception3.getMessage());
    }

    @Test
    public void testGetTask_emptyList() {
        TaskList emptyList = new TaskList(new ArrayList<>());

        // Test any index on empty list
        DukeException exception = assertThrows(DukeException.class, () -> {
            emptyList.getTask(0);
        });
        assertEquals("Invalid Task!", exception.getMessage());
    }

    @Test
    public void testGetAllTasks() {
        // Test that getAllTasks returns a copy, not the original list
        ArrayList<Task> tasksCopy = taskList.getAllTasks();
        assertEquals(3, tasksCopy.size());
        assertEquals(initialTasks, tasksCopy);

        // Verify modifying the copy doesn't affect the original
        tasksCopy.add(new Todo("New task in copy"));
        assertEquals(4, tasksCopy.size());
        assertEquals(3, taskList.getAllTasks().size()); // Original unchanged

        // Verify modifying the original doesn't affect previous copies
        taskList.addTask(new Todo("New task in original"));
        assertEquals(4, taskList.getAllTasks().size());
        assertEquals(4, tasksCopy.size()); // Copy unchanged
    }

    @Test
    public void testTaskListIntegration() throws DukeException {
        // Test comprehensive workflow: add, get, delete
        assertEquals(3, taskList.getAllTasks().size());

        // Add a task
        Todo newTask = new Todo("Integration test task");
        taskList.addTask(newTask);
        assertEquals(4, taskList.getAllTasks().size());

        // Get the new task
        Task retrievedTask = taskList.getTask(3);
        assertEquals("Integration test task", retrievedTask.description);

        // Delete the new task
        Task deletedTask = taskList.deleteTask(3);
        assertEquals("Integration test task", deletedTask.description);
        assertEquals(3, taskList.getAllTasks().size());

        // Verify the task is gone
        assertFalse(taskList.getAllTasks().contains(newTask));
    }

    @Test
    public void testTaskListWithDifferentTaskTypes() throws DukeException {
        // Test with mixed task types
        TaskList mixedList = new TaskList(new ArrayList<>());

        Todo todo = new Todo("Todo task");
        Deadline deadline = new Deadline("Deadline task", "by Monday");
        Event event = new Event("Event task", "from 2pm", "to 4pm");

        mixedList.addTask(todo);
        mixedList.addTask(deadline);
        mixedList.addTask(event);

        assertEquals(3, mixedList.getAllTasks().size());

        // Verify all tasks can be retrieved correctly
        Task task0 = mixedList.getTask(0);
        assertTrue(task0 instanceof Todo);
        assertEquals("Todo task", task0.description);

        Task task1 = mixedList.getTask(1);
        assertTrue(task1 instanceof Deadline);
        assertEquals("Deadline task", task1.description);

        Task task2 = mixedList.getTask(2);
        assertTrue(task2 instanceof Event);
        assertEquals("Event task", task2.description);

        // Test deletion of specific task types
        Task removedEvent = mixedList.deleteTask(2);
        assertTrue(removedEvent instanceof Event);
        assertEquals(2, mixedList.getAllTasks().size());
    }

    @Test
    public void testTaskListBoundaryConditions() throws DukeException {
        // Test with single task
        ArrayList<Task> singleTaskList = new ArrayList<>();
        Todo singleTask = new Todo("Single task");
        singleTaskList.add(singleTask);
        TaskList singleTaskListObj = new TaskList(singleTaskList);

        assertEquals(1, singleTaskListObj.getAllTasks().size());
        assertEquals(singleTask, singleTaskListObj.getTask(0));

        Task removed = singleTaskListObj.deleteTask(0);
        assertEquals(singleTask, removed);
        assertTrue(singleTaskListObj.getAllTasks().isEmpty());

        // Test with large number of tasks
        ArrayList<Task> largeList = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            largeList.add(new Todo("Task " + i));
        }
        TaskList largeTaskList = new TaskList(largeList);

        assertEquals(1000, largeTaskList.getAllTasks().size());
        assertEquals("Task 999", largeTaskList.getTask(999).description);

        // Test deletion from large list
        Task removedTask = largeTaskList.deleteTask(500);
        assertEquals("Task 500", removedTask.description);
        assertEquals(999, largeTaskList.getAllTasks().size());
    }
}

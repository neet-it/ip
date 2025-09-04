package duke;

/**
 * Represents a general task with a description and completion status.
 * This is the superclass for specific task types such as Todo, Deadline, and Event.
 */
public class Task {
    protected String description;
    protected boolean isDone;

    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    public String getStatusIcon() {
        return (isDone ? "X" : " "); // mark done task with X
    }
    public String getDescription() {
        return this.description; // mark done task with X
    }

    public void markAsDone() {
        this.isDone = true;
    }

    public void unmarkAsDone() {
        this.isDone = false;
    }

    @Override
    public String toString() {
        return "[" + this.getStatusIcon() + "] " + this.getDescription();
    }

    public String toFileString() {
        return (isDone ? "1" : "0") + " | " + this.description;
    }

}

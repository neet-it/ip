package duke;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Represents a UI for the chatbot.
 */

public class Ui {

    public final String line = "__________________________________________________";
    private Scanner sc;

    public Ui() {
        sc = new Scanner(System.in);
    }

    public String readCommand() {
        return sc.nextLine().trim();
    }

    public String printHello() {
        return "\t" + line + "\n"
                + "\t" + "Hello! I'm Cortex" + "\n"
                + "\t" + "What can I do for you?" + "\n"
                + "\t" + line + "\n";
    }

    public StringBuilder printBye() {
        StringBuilder output = new StringBuilder();
        output.append("\t" + line);
        output.append("\n");
        output.append("\t" + "Bye. Hope to see you again soon!");
        output.append("\n");

        return output;
    }

    public String printHorizontalLine() {
        return "\t" + line + "\n";
    }

    public StringBuilder printError(String error) {
        StringBuilder output = new StringBuilder();
        output.append("\t" + error);
        output.append("\n");

        return output;
    }

    public StringBuilder printDeletedTask(Task t, ArrayList<Task> list) {
        StringBuilder output = new StringBuilder();
        output.append("\t" + "Noted. I've removed this task:");
        output.append("\n");
        output.append("\t" + t);
        output.append("\n");
        output.append("\t" + "Now you have " + list.size() + " tasks in the list.");
        output.append("\n");

        return output;
    }

    public StringBuilder printAddedTask(Task t, ArrayList<Task> list) {
        StringBuilder output = new StringBuilder();
        output.append("\t" + "Got it. I've added this task:");
        output.append("\n");
        output.append("\t" + t);
        output.append("\n");
        output.append("\t" + "Now you have " + list.size() + " tasks in the list.");
        output.append("\n");

        return output;
    }

    public StringBuilder printList(ArrayList<Task> list) {
        StringBuilder listString = new StringBuilder();
        if (list.isEmpty()) {
            listString.append("\t" + "There are no tasks in your list.");
            listString.append("\n");
        } else {
            listString.append("\t" + "Here are the tasks in your list:");
            listString.append("\n");
            int c = 1;
            for (int i = 0; i < list.size(); i++) {
                listString.append("\t" + c++ + ". " + list.get(i));
                listString.append("\n");
            }
        }
        return listString;
    }

    public StringBuilder printFoundList(ArrayList<Task> list, String key) {
        StringBuilder output = new StringBuilder();
        if (list.isEmpty()) {
            output.append("\t" + "No matching tasks found for: " + key);
            output.append("\n");
        } else {
            output.append("\t" + "Here are the matching tasks in your list:");
            output.append("\n");
            int c = 1;
            for (Task task : list) {
                output.append("\t" + c++ + ". " + task);
                output.append("\n");
            }
        }
        return output;
    }

    public StringBuilder printMarked(Task t) {
        StringBuilder output = new StringBuilder();
        output.append("\t" + "Nice! I've marked this task as done:");
        output.append("\n");
        output.append("\t" + t);
        output.append("\n");
        return output;
    }

    public StringBuilder printUnmarked(Task t) {
        StringBuilder output = new StringBuilder();
        output.append("\t" + "OK, I've marked this task as not done yet:");
        output.append("\n");
        output.append("\t" + t);
        output.append("\n");
        return output;
    }
}

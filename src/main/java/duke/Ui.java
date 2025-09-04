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

    public void printHello() {
        System.out.println("\t" + line);
        System.out.println("\t" + "Hello! I'm Cortex");
        System.out.println("\t" + "What can I do for you?");
        System.out.println("\t" + line);
    }

    public void printBye() {
        System.out.println("\t" + line);
        System.out.println("\t" + "Bye. Hope to see you again soon!");
        System.out.println("\t" + line);
    }

    public void printHorizontalLine() {
        System.out.println("\t" + line);
    }

    public void printError(String error) {
        System.out.println("\t" + error);
    }

    public void printDeletedTask(Task t, ArrayList<Task> list) {
        System.out.println("\t" + "Noted. I've removed this task:");
        System.out.println("\t" + t);
        System.out.println("\t" + "Now you have " + list.size() + " tasks in the list.");
    }

    public void printAddedTask(Task t, ArrayList<Task> list) {
        System.out.println("\t" + "Got it. I've added this task:");
        System.out.println("\t" + t);
        System.out.println("\t" + "Now you have " + list.size() + " tasks in the list.");
    }

    public void printList(ArrayList<Task> list) {
        if (list.isEmpty()) {
            System.out.println("\t" + "There are no tasks in your list.");
        } else {
            System.out.println("\t" + "Here are the tasks in your list:");
            int c = 1;
            for (int i = 0; i < list.size(); i++) {
                System.out.println("\t" + c++ + ". " + list.get(i));
            }
        }
    }

    public void printMarked(Task t) {
        System.out.println("\t" + "Nice! I've marked this task as done:");
        System.out.println("\t" + t);
    }

    public void printUnmarked(Task t) {
        System.out.println("\t" + "OK, I've marked this task as not done yet:");
        System.out.println("\t" + t);
    }

}

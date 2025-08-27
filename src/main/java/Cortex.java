import java.util.ArrayList;
import java.util.Scanner;

public class Cortex {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        String line = "__________________________________________________";

        System.out.println("\t" + line);
        System.out.println("\t" +"Hello! I'm Cortex");
        System.out.println("\t" +"What can I do for you?");
        System.out.println("\t" +line);

        String command = sc.nextLine().trim();
        ArrayList<Task> list = new ArrayList<>();

        while(!command.equalsIgnoreCase("bye")) {
            System.out.println("\t" + line);

            if(command.equalsIgnoreCase("LIST")) {
                if(list.isEmpty()) {
                    System.out.println("\t" + "There are no tasks in your list.");
                } else {
                    System.out.println("\t" + "Here are the tasks in your list:");
                    int c = 1;
                    for(Task t : list) {
                        System.out.println("\t" + c++ + ". " + "[" + t.getStatusIcon() + "] " + t.getDescription());
                    }
                }

            } else if (command.startsWith("mark ")) {
                int i = Integer.parseInt(command.substring(5).trim()) - 1;
                Task t = list.get(i);

                t.markAsDone();

                System.out.println("\t" + "Nice! I've marked this task as done:");
                System.out.println("\t" + "[" + t.getStatusIcon() + "] " + t.getDescription());


            } else if (command.startsWith("unmark ")) {
                int i = Integer.parseInt(command.substring(7).trim()) - 1;
                Task t = list.get(i);

                t.unmarkAsDone();

                System.out.println("\t" + "OK, I've marked this task as not done yet:");
                System.out.println("\t" + "[" + t.getStatusIcon() + "] " + t.getDescription());

            } else {
                Task task = new Task(command);
                list.add(task);
                System.out.println("\t" + "added: " + command);
            }

            System.out.println("\t" + line);
            command = sc.nextLine().trim();
        }

        System.out.println("\t" +line);
        System.out.println("\t" +"Bye. Hope to see you again soon!");
        System.out.println("\t" +line);
    }
}

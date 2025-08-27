import java.util.ArrayList;
import java.util.Scanner;

public class Cortex {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        String line = "__________________________________________________";

        System.out.println("\t" + line);
        System.out.println("\t" + "Hello! I'm Cortex");
        System.out.println("\t" + "What can I do for you?");
        System.out.println("\t" + line);

        String command = sc.nextLine().trim();
        ArrayList<Task> list = new ArrayList<>();

        while (!command.equalsIgnoreCase("bye")) {
            System.out.println("\t" + line);

            if (command.equalsIgnoreCase("LIST")) {
                if (list.isEmpty()) {
                    System.out.println("\t" + "There are no tasks in your list.");
                } else {
                    System.out.println("\t" + "Here are the tasks in your list:");
                    int c = 1;
                    for (Task t : list) {
                        System.out.println("\t" + c++ + ". " + t);
                    }
                }

            } else if (command.startsWith("mark")) {

                try {
                   int  i= Integer.parseInt(command.substring(5).trim()) - 1;
                    if(i < 0 || i >= list.size()) {
                        System.out.println("\t" + "Invalid Task! Cannot mark task.");
                    } else {
                        Task t = list.get(i);

                        t.markAsDone();

                        System.out.println("\t" + "Nice! I've marked this task as done:");
                        System.out.println("\t" + t);
                    }
                } catch (NumberFormatException e) {
                    System.out.println("\t" + "Invalid Task! Cannot mark task.");
                } catch (IndexOutOfBoundsException e) {
                    System.out.println("\t" + "Invalid Task! Cannot mark task.");
                }

            } else if (command.startsWith("unmark")) {
                try {
                    int i = Integer.parseInt(command.substring(7).trim()) - 1;

                    if(i < 0 || i >= list.size()) {
                        System.out.println("\t" + "Invalid Task! Cannot unmark task.");
                    } else {
                        Task t = list.get(i);

                        t.unmarkAsDone();

                        System.out.println("\t" + "OK, I've marked this task as not done yet:");
                        System.out.println("\t" + t);
                    }

                } catch (NumberFormatException e) {
                    System.out.println("\t" + "Invalid Task! Cannot unmark task.");
                } catch (IndexOutOfBoundsException e) {
                    System.out.println("\t" + "Invalid Task! Cannot unmark task.");
                }


            }else {
                Task task = new Task(command);
                boolean isValidTask = false;
                int c = 0;
                if (command.startsWith("todo")) {
                    c++;
                    try {
                        String s = command.substring(5).trim();
                        task = new Todo(s);
                        isValidTask = true;
                    } catch (Exception e){
                        System.out.println("\t" + "INVALID TODO TASK");
                    }

                } else if (command.startsWith("deadline")) {
                    c++;
                    try {
                        String s = command.substring(9).trim();
                        String[] bySplit = s.split("/by");
                        String desc = bySplit[0].trim();
                        String by = bySplit[1].trim();

                        task = new Deadline(desc, by);
                        isValidTask = true;
                    } catch (Exception e) {
                        System.out.println("\t" + "INVALID DEADLINE TASK");
                    }

                } else if (command.startsWith("event")) {
                    c++;
                    try {
                        String s = command.substring(6).trim();
                        String[] fromSplit = s.split("/from");
                        String desc = fromSplit[0].trim();

                        String[] toSplit = fromSplit[1].split("/to");
                        String from = toSplit[0];
                        String to = toSplit[1];

                        task = new Event(desc, from, to);
                        isValidTask = true;
                    } catch (Exception e) {
                        System.out.println("\t" + "INVALID EVENT TASK");
                    }
                }

                if(isValidTask) {
                    list.add(task);
                    System.out.println("\t" + "Got it. I've added this task:");
                    System.out.println("\t" + task);
                    System.out.println("\t" + "Now you have " + list.size() + " tasks in the list.");
                } else if (c == 0){
                    System.out.println("\t" + "INVALID TASK TYPE");
                }

            }


            System.out.println("\t" + line);
            command = sc.nextLine().trim();
        }

        System.out.println("\t" +line);
        System.out.println("\t" +"Bye. Hope to see you again soon!");
        System.out.println("\t" +line);
    }
}
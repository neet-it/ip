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
        ArrayList<String> list = new ArrayList<>();

        while(!command.equalsIgnoreCase("bye")) {
            System.out.println("\t" + line);

            if(command.equalsIgnoreCase("LIST")) {
                if(list.isEmpty()) {
                    System.out.println("\t" + "List Empty");
                } else {
                    int c = 1;
                    for(String s : list) {
                        System.out.println("\t" + c++ + ". " + s);
                    }
                }

            } else {
                list.add(command);
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

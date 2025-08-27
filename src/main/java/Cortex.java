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

        while(!command.equalsIgnoreCase("bye")) {
            System.out.println("\t" +line);
            System.out.println("\t" +command);
            System.out.println("\t" +line);

            command = sc.nextLine().trim();
        }

        System.out.println("\t" +line);
        System.out.println("\t" +"Bye. Hope to see you again soon!");
        System.out.println("\t" +line);
    }
}

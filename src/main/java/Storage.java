import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Storage {

    public String filepath;

    public Storage(String filepath){
        this.filepath = filepath;
    }

    public ArrayList<Task> loadtasks() {
        ArrayList<Task> list = new ArrayList<>();
        Path path = Paths.get(filepath);

        try {
            if (Files.exists(path)) {
                List<String> file = Files.readAllLines(path);

                for(String line : file){
                    try {
                        Task task = parseTask(line);
                        if(task != null) {
                            list.add(task);
                        }
                    } catch(Exception e) {
                        System.out.println("\t" + "Oh no! Could not load tasks from your previous list.");
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("\t" + "Oh no! Could not load tasks from your previous list.");
        }

        return list;
    }

    public void saveTasks(ArrayList<Task> list) {
        try {
            File dir = new File("./data");
            if(!dir.exists()) {
                dir.mkdir();
            }

            FileWriter writer = new FileWriter(filepath);

            for(Task task : list) {
                writer.write(task.toFileString() + "\n");
            }
            writer.close();
        } catch(IOException e) {
            System.out.println("\t" + "Tasks could not be saved to file");
        }
    }

    public Task parseTask(String line) {
        String[] parts = line.split(" \\| ");
        if (parts.length < 3) return null;

        String taskType = parts[0];
        boolean isDone = parts[1].equals("1");
        String description = parts[2];

        Task task = null;

        switch (taskType) {
        case "T":
            task = new Todo(description);
            break;
        case "D":
            try {
                String[] bySplit = description.split("/by");
                String desc = bySplit[0].trim();
                String by = bySplit[1].trim();

                task = new Deadline(desc, by);
            } catch (Exception e) {
                System.out.println("\t" + "INVALID DEADLINE TASK");
            }

            break;
        case "E":
            try {
                String[] fromSplit = description.split("/from");
                String desc = fromSplit[0].trim();

                String[] toSplit = fromSplit[1].split("/to");
                String from = toSplit[0];
                String to = toSplit[1];

                task = new Event(desc, from, to);
            } catch (Exception e) {
                System.out.println("\t" + "INVALID EVENT TASK");
            }
            break;
        }

        if (task != null && isDone) {
            task.markAsDone();
        }

        return task;

    }
}

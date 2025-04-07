package pl.coderslab;


import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TaskManager {

    public static void main(String[] args){
        try {
            String[][] tasks = getTasks();
            for (String[] task : tasks) {
                for (String detail : task) {
                    System.out.print(detail + " ");
                }
                System.out.println();
            }
        } catch (IOException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    public static void printMenu(){
        String[] menuOptions = {
                "add",
                "remove",
                "list",
                "exit"
        };
        System.out.println(ConsoleColors.BLUE + "Please select an option:"
                + ConsoleColors.RESET);
        for (String option: menuOptions){
            System.out.println(option);
        }
    }

    public static String[][] getTasks() throws IOException {
        Path path = Paths.get("tasks.csv");
        List<String[]> taskList = new ArrayList<>();

        try {
            Scanner scanner = new Scanner(path);

            while(scanner.hasNextLine()){
                String line = scanner.nextLine();
                String[] task = line.split(",");
                taskList.add(task);
            }
        } catch (IOException e) {
            throw new IOException("Error during reading file: " + path, e);
        }

        return taskList.toArray(new String[0][]);
    }


}

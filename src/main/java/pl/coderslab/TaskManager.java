package pl.coderslab;


import org.apache.commons.lang3.ArrayUtils;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class TaskManager {

    public static void main(String[] args){
        try{
            printMenu();
            String[][] tasks = loadTasks();
            String[][] newTasks = addTask(tasks);
            listTasks(newTasks);
            String[][] removedTasks = removeTask(newTasks);
            listTasks(removedTasks);
        }catch (IOException e){
            System.out.println(e.getMessage());}
    }

    public static String[][] removeTask(String[][] copyOfTasksArray) throws IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("remove");
        System.out.println("Please select number to remove");

        while (true) {
            if (scanner.hasNextInt()) {
                int numberOfTask = scanner.nextInt();
                if (numberOfTask >= 0 && numberOfTask < copyOfTasksArray.length) {
                    String[][] arrayWithDeletedTask = ArrayUtils.remove(copyOfTasksArray, numberOfTask);
                    copyOfTasksArray = null; // Zwolnienie pamięci
                    System.out.println("Value was successfully deleted.");
                    return arrayWithDeletedTask;
                } else {
                    System.out.println("Incorrect argument pass. Please give a number greater or equal 0 in range of tasks.");
                }
            } else {
                System.out.println("Incorrect argument pass. Please give a number.");
                scanner.next(); // Konsumowanie niepoprawnego wejścia
            }
        }
    }

    public static void listTasks(String[][] tasks){
       System.out.println("list");
        for(int i = 0; i < tasks.length; i++){
            System.out.println(i + " : "+ tasks[i][0] + " " + tasks[i][1] + " " + tasks[i][2]);
        }
    }

    public static String[][] addTask(String[][] tasks){
        // Collect data from the user
        System.out.println("add");
        Scanner scanner = new Scanner(System.in);
        System.out.println("Please add a task description:");
        String taskDescription = scanner.nextLine();
        System.out.println("Please add a task due date:");
        String taskDueDate = scanner.nextLine();
        System.out.println("Is it your task imporntant: true/false");
        String taskImportant = scanner.nextLine();

        // Create a new array with one additional row
        String[][] copyOfTasksArray = Arrays.copyOf(tasks, tasks.length + 1);
        copyOfTasksArray[tasks.length] = new String[]{taskDescription, taskDueDate, taskImportant};

        return copyOfTasksArray;
    }

    public static void getUserDecision(String[][] tasks){
        Scanner scanner = new Scanner(System.in);
        String userDecision = scanner.nextLine();
        switch (userDecision) {
            case "add":
                System.out.println("Adding a task...");
                addTask(tasks);
                break;
            case "remove":
                System.out.println("Removing a task...");
               // romeveTask();
                break;
            case "list":
                System.out.println("Listing a task...");
                //listTasks();
                break;
            case "exit":
                System.out.println("Exiting the program.");
                break;
            default:
                System.out.println("Please select a correct option.");
        }
    }

    /**
     * Reads tasks from a CSV file and returns them as a 2D array.
     *
     * @return A 2D array of tasks.
     * @throws IOException If an error occurs while reading the file.
     */
    public static String[][] loadTasks() throws IOException {
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

    /**
     * Prints the menu options to the console.
     */
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

}

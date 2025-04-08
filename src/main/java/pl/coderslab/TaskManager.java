package pl.coderslab;


import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class TaskManager {
    static String FILENAME = "tasks.csv";
    static String[][] tasks;

    public static void main(String[] args) {
        try {
            printMenu();
            tasks = loadTasks();
            getUserDecision();

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Exits the program and saves the tasks to a file.
     */
    public static void exit( ){
        try {
            saveToFile();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        System.out.println(ConsoleColors.RED + "Bye, bye.");
        System.exit(0);
    }

    /**
     * Saves the tasks to a CSV file.
     *
     * @throws IOException If an error occurs while writing to the file.
     */
    public static void saveToFile() throws IOException {
        StringBuilder csvData = new StringBuilder();

        for(String[] task: tasks){
            csvData.append(StringUtils.join(task, ",")).append("\n");
        }
        try {
            FileUtils.writeStringToFile(new File(FILENAME), csvData.toString(),
                    StandardCharsets.UTF_8);
        } catch (IOException e) {
            System.out.println("There is some problems with file to save: " +
                    e.getMessage());
        }
    }

    /**
     * Removes a task from the tasks array.
     *
     * @throws IOException If an error occurs while reading input.
     */
    public static void removeTask() throws IOException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("remove");
        System.out.println("Please select number to remove");

        while (true) {
            if (scanner.hasNextInt()) {
                int numberOfTask = scanner.nextInt();
                if (numberOfTask >= 0 && numberOfTask < tasks.length) {
                    tasks = ArrayUtils.remove(tasks, numberOfTask);
                    System.out.println("Value was successfully deleted.");
                    printMenu();
                    break;
                } else {
                    System.out.println(numberOfTask + "\nIncorrect argument pass. Please give a number greater or equal 0 in range of tasks.");
                }
            } else {
                String badInput = scanner.next();
                System.out.println(badInput + "\nIncorrect argument pass. Please give a number.");
            }
        }
    }

    /**
     * Prints the tasks to the console.
     */
    public static void listTasks() {
        System.out.println("list");
        for (int i = 0; i < tasks.length; i++) {
            System.out.println(i + " : " + tasks[i][0] + " " + tasks[i][1] + " " + tasks[i][2]);
        }
        printMenu();
    }

    /**
     * Adds a new task to the tasks array.
     */
    public static void addTask() {
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
        tasks = Arrays.copyOf(tasks, tasks.length + 1);
        tasks[tasks.length - 1] = new String[]{taskDescription, taskDueDate, taskImportant};

        System.out.println("Task added successfully.");
        printMenu();
    }

    /**
     * Prompts the user for a decision and executes the corresponding action.
     */
    public static void getUserDecision() {
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            String userDecision = scanner.nextLine();
            switch (userDecision) {
                case "add":
                    addTask();
                    break;
                case "remove":
                    try {
                        removeTask();
                        break;
                    } catch (IOException e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case "list":
                    listTasks();
                    break;
                case "exit":
                    exit();
                    break;
                default:
                    System.out.println("Please select a correct option.");
                    printMenu();
            }
        }
        printMenu();
    }

    /**
     * Reads tasks from a CSV file and returns them as a 2D array.
     *
     * @return A 2D array of tasks.
     * @throws IOException If an error occurs while reading the file.
     */
    public static String[][] loadTasks() throws IOException {
        Path path = Paths.get(FILENAME);
        List<String[]> taskList = new ArrayList<>();

        try {
            Scanner scanner = new Scanner(path);

            while (scanner.hasNextLine()) {
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
    public static void printMenu() {
        String[] menuOptions = {
                "add",
                "remove",
                "list",
                "exit"
        };
        System.out.println(ConsoleColors.BLUE + "Please select an option:"
                + ConsoleColors.RESET);
        for (String option : menuOptions) {
            System.out.println(option);
        }
    }

}

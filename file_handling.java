import java.io.*;
import java.util.*;

public class file_handling {

    static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        fileProcessing();
        scanner.close();
    }

    static void fileProcessing() {
        cls();
        System.out.println(
                "What would You Like to do? \n1.) Create a new '.txt' file (create) \n2.) Read a '.txt' file  (read) \n3.) Write / Update an existing '.txt' (write / update) file \n4.) Delete a '.txt' file (delete)");
        System.out.print("Enter your choice: ");
        String userChoice = scanner.nextLine();

        userChoice = userChoice.toLowerCase();

        switch (userChoice) {
            case "create":
                createFile();
                break;
            case "read":
                readFile();
                break;
            case "update":
            case "write":
                updateFile();
                break;
            case "delete":
                deleteFile();
                break;
            default:
                System.out.println("Enter a valid choice");
        }
    }

    static void createFile() {
        Scanner scanner = new Scanner(System.in);
        try {
            System.out.print("Enter the name of the file that you would like to create: ");
            String fileName = scanner.nextLine();
            File newFile = new File(fileName + ".txt");
            if (newFile.createNewFile()) {
                System.out.println(newFile.getName() + " has been created at the path " + newFile.getAbsolutePath());
            } else {
                System.out.println("File already exists.");
            }
        } catch (IOException e) {
            System.out.println("An error occurred while creating the file.");
            e.printStackTrace();
        }
        userChoiceToContinue();
    }

    static void readFile() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the name of the file that you would like to read: ");
        String fileName = scanner.nextLine();
        try {
            File fileRead = new File(fileName + ".txt");
            Scanner fileReader = new Scanner(fileRead);
            while (fileReader.hasNextLine()) {
                String data = fileReader.nextLine();
                System.out.println(data);
            }
            fileReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        userChoiceToContinue();
    }

    static void updateFile() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the name of the file that you would like to update: ");
        String fileName = scanner.nextLine();
        System.out.println("Enter the text that you would like to add to the file: ");
        String textToAdd = scanner.nextLine();
        try {
            FileWriter fileWrite = new FileWriter(fileName + ".txt", true);
            fileWrite.write(textToAdd);
            fileWrite.write("\n");
            fileWrite.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        userChoiceToContinue();
    }

    static void deleteFile() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the name of the file that you would like to delete: ");
        String fileName = scanner.nextLine();
        File fileDelete = new File(fileName);
        if (fileDelete.delete()) {
            System.out.println(fileDelete.getName() + " has been deleted.");
        } else {
            System.out.println("Failed to delete the file.");
        }
        userChoiceToContinue();
    }

    static void userChoiceToContinue() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Would you like to continue? (Y / N)");
        String userChoice = scanner.nextLine();
        if (userChoice.equalsIgnoreCase("y")) {
            fileProcessing();
        } else {
            System.out.println("Exiting the program.");
        }
    }

    static void cls() {
        try {
            if (System.getProperty("os.name").contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            }
        } catch (Exception e) {
            System.out.println("Error clearing the screen.");
        }
    }

}
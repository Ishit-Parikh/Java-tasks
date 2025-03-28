package Ishit_internshipTasks;

import java.util.LinkedHashMap;
import java.util.Scanner;
import java.util.Random;
import java.util.Map;

public class number_guessing_game {
    public static void main(String[] args) throws InterruptedException {
        Scanner scanner = new Scanner(System.in);

        int userChoice;
        int userChance = 5;
        boolean correctGuess = false;
        String guessedIntStat;
        Map<Integer, String> guessedIntegerAndStats = new LinkedHashMap<>();

        cls();

        Random randomObject = new Random();
        int randomChoice = randomObject.nextInt(100);
        while (userChance != 0) {
            System.out.println("Welcome to a random number guessing game!");
            if (!guessedIntegerAndStats.isEmpty()) {
                System.out.println("Your Previous Choices: ");
                System.out.print("[");
                for (Map.Entry<Integer, String> printGuessedIntegerAndStats : guessedIntegerAndStats.entrySet()) {
                    System.out.print(printGuessedIntegerAndStats.getKey() + " = " + printGuessedIntegerAndStats.getValue() + "| ");
                }
                System.out.print("]");
                System.out.println();
            }
            System.out.println("You have " + userChance + " chances Left.");
            System.out.print("Guess a number between 0 - 100: ");
            userChoice = scanner.nextInt();
            if (userChoice > 100 || userChoice < 0) {
                cls();
                System.out.println("Invalid Entry! Enter a number between 1 - 100");
                Thread.sleep(2500);
                cls();
            } else {
                if (randomChoice == userChoice) {
                    cls();
                    System.out.println("You guessed " + userChoice + " which is correct.");
                    System.out.println("Your Previous Choices: ");
                    System.out.print("[");
                    for (Map.Entry<Integer, String> printGuessedIntegerAndStats : guessedIntegerAndStats.entrySet()) {
                        System.out.print(printGuessedIntegerAndStats.getKey() + " = " + printGuessedIntegerAndStats.getValue() + "| ");
                    }
                    System.out.print("]");
                    System.out.println();
                    System.out.println("Congratulations! You won the game");
                    correctGuess = true;
                    break;
                } else {

                    if (Math.abs(userChoice - randomChoice) <= 10) {
                        System.out.println("You guessed " + userChoice + " which is incorrect, but VERY CLOSE!");
                        guessedIntStat = "CLOSE";
                    } else if (userChoice > randomChoice) {
                        System.out.println("You guessed " + userChoice + " which is incorrect, and Very HIGH");
                        guessedIntStat = "HIGH";
                    } else {
                        System.out.println("You guessed " + userChoice + " which is incorrect, and Very LOW");
                        guessedIntStat = "LOW";
                    }
                    userChance--;
                    guessedIntegerAndStats.put(userChoice, guessedIntStat);

                    Thread.sleep(2500);
                    cls();
                }
            }
        }

        if (!correctGuess) {
            System.out.println("You used up all your guesses.");
            System.out.println("Your Choices: ");
            System.out.print("[");
            for (Map.Entry<Integer, String> printGuessedIntegerAndStats : guessedIntegerAndStats.entrySet()) {
                System.out.print(printGuessedIntegerAndStats.getKey() + " = " + printGuessedIntegerAndStats.getValue() + "| ");
            }
            System.out.print("]");
            System.out.println();
            System.out.println("The random number was: " + randomChoice);
        }

        scanner.close();

    }

    public static void cls() {
        try {
            if (System.getProperty("os.name").contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            }
        } catch (Exception e) {
            System.out.println("Error clearing the screen.");
        }
    }
}
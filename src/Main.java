/**
 * -----------------------------------------------------
 * ES234211 - Programming Fundamental
 * Genap - 2023/2024
 * Group Capstone Project: Snake and Ladder Game
 * -----------------------------------------------------
 * Class    : C
 * Group    : XX
 * Members  :
 * 1. Student ID - Full Name
 * 2. Student ID - Full Name
 * 3. Student ID - Full Name
 * ------------------------------------------------------
 */

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        Scanner scanner = new Scanner(System.in);
        SnakeAndLadder game = new SnakeAndLadder(100);
        game.setSizeOfBoard();

        boolean exit = false;
        while (!exit) {
            System.out.println("\n===== Snake and Ladder Game Menu =====");
            System.out.println("1. Play");
            System.out.println("2. Check Scores");
            System.out.println("3. Exit");
            System.out.println("=====================================");

            int choice = 0;
            try {
                System.out.print("Enter your choice: ");
                choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline character
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a number.");
                scanner.nextLine(); // Clear input buffer
                continue;
            }

            switch (choice) {
                case 1:
                    game.play();
                    break;
                case 2:
                    displayScores(game);
                    break;
                case 3:
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a number between 1 and 3.");
                    break;
            }
        }
        System.out.println("Thanks for playing Snake and Ladder!");
    }

    private static void displayScores(SnakeAndLadder game) {
        System.out.println("\nCurrent Scores:");
        for (Player player : game.getPlayers()) {
            System.out.println(player.getUserName() + ": " + game.getScores()[game.getPlayers().indexOf(player)]);
        }
    }
}

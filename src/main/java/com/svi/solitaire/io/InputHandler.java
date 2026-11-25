package com.svi.solitaire.io;

import com.svi.solitaire.shuffle.ShuffleType;
import com.svi.solitaire.utils.Constants;

import java.util.Scanner;

/**
 * Retrieves and validates input from the user
 *
 * @author Sheryn Cagulada
 * @version 1.0
 */
public class InputHandler {
    private static final Scanner scanner = new Scanner(System.in);

    /**
     * Closes the scanner instance
     */
    public static void close() {
        scanner.close();
    }

    /**
     * Retrieves filename from the user
     *
     * @return the entered filename
     */
    public static String inputFileName() {
        String filename;
        String filePrompt = "\nEnter filename for card deck: ";

        while (true) {
            ConsoleView.printPrompt(filePrompt);
            filename = scanner.nextLine().trim();
            if (filename.isBlank()) {
                ConsoleView.printErrorMessage("Make sure to enter a filename.");
            } else {
                return filename;
            }
        }
    }

    /**
     * Retrieves the number of draws from the suer
     *
     * @return the entered draw count
     */
    public static int inputDrawCount() {
        int drawCount;

        String drawPrompt = """
                
                How many draws from talon?
                1 = Draw One
                3 = Draw Three
                (1/3)>\s""";

        while (true) {
            try {
                ConsoleView.printPrompt(drawPrompt);
                drawCount = Integer.parseInt(scanner.nextLine().trim());
                if (drawCount != 1 && drawCount != 3) {
                    ConsoleView.printErrorMessage("Invalid choice. Please enter 1 or 3.");
                } else {
                    return drawCount;
                }
            } catch (NumberFormatException nfe) {
                ConsoleView.printErrorMessage("Make sure to enter a number. Kindly re-enter a value.");
            }
        }
    }

    /**
     * Retrieves the shuffle choice from the user
     *
     * @return the chosen shuffle type
     */
    public static ShuffleType inputShuffleChoice() {
        int shuffleChoice;
        final int IN_FARO_SHUFFLE_CHOICE = 1;
        final int OUT_FARO_SHUFFLE_CHOICE = 2;
        final int RANDOM_SHUFFLE_CHOICE = 3;
        final int NO_SHUFFLE_CHOICE = 4;

        String shufflePrompt = """
                
                How do you want to shuffle?
                1. Perfect Shuffle (In-Faro)
                2. Perfect Shuffle (Out-Faro)
                3. Random Shuffle
                4. No Shuffle
                (1-4)>\s""";

        shuffleChoice = inputInt(shufflePrompt, IN_FARO_SHUFFLE_CHOICE, NO_SHUFFLE_CHOICE);

        return switch (shuffleChoice) {
            case IN_FARO_SHUFFLE_CHOICE -> ShuffleType.IN_FARO;
            case OUT_FARO_SHUFFLE_CHOICE -> ShuffleType.OUT_FARO;
            case RANDOM_SHUFFLE_CHOICE -> ShuffleType.RANDOM;
            case NO_SHUFFLE_CHOICE -> ShuffleType.NO_SHUFFLE;
            default -> throw new NullPointerException("Shuffle Choice does not exist");
        };
    }

    /**
     * Retrieves the simulation choice from the user
     *
     * @return the simulation choice
     */
    public static int inputSimulationChoice() {
        int simChoice;
        String simPrompt = """
                
                How do you want to simulate?
                1. Single Game
                2. Check win rate
                (1/2)>\s""";

        simChoice = inputInt(simPrompt, 1, 2);
        return simChoice;
    }

    /**
     * Retrieves the number of game from the user
     *
     * @return the number of games
     */
    public static int inputGameCount() {
        int gameCount;
        String gamePrompt = "Enter number of games to simulate (1-1000): ";

        gameCount = inputInt(gamePrompt, Constants.MIN_GAMES, Constants.MAX_GAMES);

        return gameCount;
    }

    /**
     * Prompts and checks if the user wants to play again
     *
     * @return true if play again; false otherwise
     */
    public static boolean playAgain() {
        while (true) {
            ConsoleView.printPrompt("\nDo you want to play again? (y/n): ");
            String input = scanner.nextLine();
            if (input.equalsIgnoreCase("y")) {
                return true;
            } else if (input.equalsIgnoreCase("n")) {
                return false;
            } else {
                ConsoleView.printErrorMessage("Make sure to enter either y/n only.");
            }
        }
    }

    /**
     * Prompts and checks integer if it is within the range
     *
     * @param prompt the prompt message
     * @param min minimum range
     * @param max maximum range
     * @return the entered integer
     */
    public static int inputInt(String prompt, int min, int max) {
        while (true) {
            try {
                ConsoleView.printPrompt(prompt);
                int input = Integer.parseInt(scanner.nextLine().trim());
                if (input < min || input > max) {
                    ConsoleView.printErrorMessage("Make sure to enter a number between " + min + " and " + max + ".");
                } else {
                    return input;
                }
            } catch (NumberFormatException nfe) {
                ConsoleView.printErrorMessage("Make sure to enter a number. Kindly re-enter a value.");
            }
        }
    }

}

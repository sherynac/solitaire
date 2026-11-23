package com.svi.solitaire.io;

import com.svi.solitaire.shuffle.ShuffleType;

import java.util.Scanner;

public class InputHandler {
    private static final Scanner scanner = new Scanner(System.in);

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

    public static int inputDrawCount() {
        int drawChoice;
        final int MINIMUM_DRAW = 1;
        final int MAXIMUM_DRAW = 3;
        String drawPrompt = """
                
                How many draws from talon?
                1 = Draw One
                3 = Draw Three
                (1/3)>\s""";

        drawChoice = inputInt(drawPrompt, MINIMUM_DRAW, MAXIMUM_DRAW);

        while (drawChoice != 1 && drawChoice != 3) {
            ConsoleView.printErrorMessage("Invalid choice. Please enter 1 or 3.");
            drawChoice = inputInt(drawPrompt, MINIMUM_DRAW, MAXIMUM_DRAW);
        }

        return drawChoice;
    }

    public static ShuffleType inputShuffleChoice() {
        int shuffleChoice;
        final int IN_FARO_SHUFFLE_CHOICE = 1;
        final int OUT_FARO_SHUFFLE_CHOICE = 2;
        final int RANDOM_SHUFFLE_CHOICE =3;
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

    public static int inputSimulationChoice() {
        int simChoice;
        String simPrompt = """
                
                How do you want to simulate?
                1. Single Game
                2. Check win rate (100 games)
                (1/2)>\s""";
        final int FIRST_CHOICE = 1;
        final int SECOND_CHOICE = 2;

        simChoice = inputInt(simPrompt, FIRST_CHOICE,SECOND_CHOICE);
        return simChoice;
    }

    public static int inputNumberOfGames() {

        while (true) {
            System.out.print("Enter number of games to simulate: ");
            String line = scanner.nextLine().trim();
            try {
                int games = Integer.parseInt(line);
                if (games > 0) return games;
            } catch (NumberFormatException ignored) {
                // fall through to the message below
            }
            System.out.println("Please enter a whole number greater than 0.");
        }
    }

    public static boolean playAgain() {
        while (true) {
            System.out.print("\nDo you want to play again? (y/n): ");
            String input = scanner.nextLine();
            if (input.equalsIgnoreCase("y")){
                return true;
            } else if (input.equalsIgnoreCase("n")) {
                return false;
            } else {
                ConsoleView.printErrorMessage("Make sure to enter either y/n only.");
            }
        }
    }

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
                ConsoleView.printErrorMessage("Make sure to enter a number.");
                ConsoleView.printErrorMessage("Kindly re-enter a value.");
            }
        }
    }

}

package com.svi.solitaire.utils;

import com.svi.solitaire.enums.Color;
import com.svi.solitaire.utils.ColorHelper;
import com.svi.solitaire.enums.ShuffleType;

import javax.print.DocFlavor;
import java.util.Scanner;

public class InputHandler {
    private static Scanner scanner = new Scanner(System.in);

    public static int inputDrawCount() {
        int drawChoice;
        final int MINIMUM_DRAW = 1;
        final int MAXIMUM_DRAW = 2;
        String drawPrompt = "\nHow many draws from talon?"
                + "\n1. Draw One"
                + "\n2. Draw Three"
                + "\n(1/2)> ";

        drawChoice = inputInt(drawPrompt, MINIMUM_DRAW, MAXIMUM_DRAW);
        return drawChoice;
    }

    public static ShuffleType inputShuffleChoice() {
        int shuffleChoice;
        final int IN_FARO_SHUFFLE_CHOICE = 1;
        final int OUT_FARO_SHUFFLE_CHOICE = 2;
        final int RANDOM_SHUFFLE_CHOICE =3;
        final int NO_SHUFFLE_CHOICE = 4;
        String shufflePrompt = "\nHow do you want to shuffle? "
                + "\n1. Perfect Shuffle (In-Faro)"
                + "\n2. Perfect Shuffle (Out-Faro)"
                + "\n3. Random Shuffle"
                + "\n4. No Shuffle"
                + "\n(1-4)> ";

        shuffleChoice = inputInt(shufflePrompt, IN_FARO_SHUFFLE_CHOICE, NO_SHUFFLE_CHOICE);

        switch (shuffleChoice) {
            case IN_FARO_SHUFFLE_CHOICE:
                return ShuffleType.IN_FARO;
            case OUT_FARO_SHUFFLE_CHOICE:
                return ShuffleType.OUT_FARO;
            case RANDOM_SHUFFLE_CHOICE:
                return ShuffleType.RANDOM;
            case NO_SHUFFLE_CHOICE:
                return ShuffleType.NO_SHUFFLE;
        }

        throw new NullPointerException("Shuffle Choice does not exist");
    }

    public static int inputSimulationChoice() {
        int simChoice;
        String simPrompt = "\nHow do you want to simulate?"
                + "\n1. Single Game"
                + "\n2. Check win rate (100 games)"
                +"\n(1/2)> ";
        final int FIRST_CHOICE = 1;
        final int SECOND_CHOICE = 2;

        simChoice = inputInt(simPrompt, FIRST_CHOICE,SECOND_CHOICE);
        return simChoice;
    }

    public static int inputInt(String prompt, int min, int max) {
        while (true) {
            try {
                System.out.print(prompt);
                int input = Integer.parseInt(scanner.nextLine().trim());
                if (input < min || input > max) {
                    System.out.println("Make sure to enter a number between " + min + " and " + max + ".");
                } else {
                    return input;
                }
            } catch (NumberFormatException nfe) {
                System.out.println("Make sure to enter a number.");
                System.out.println("Kindly re-enter a value. ");
            }
        }
    }

    public static boolean playAgain() {
        while (true) {
            System.out.print("Do you want to play again? (y/n): ");
            String input = scanner.nextLine();
            if (input.equalsIgnoreCase("y")){
                return true;
            } else if (input.equalsIgnoreCase("n")) {
                return false;
            }
        }
    }
}

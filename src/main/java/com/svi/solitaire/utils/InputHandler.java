package com.svi.solitaire.utils;

import com.svi.solitaire.enums.ShuffleType;

import java.util.Scanner;

public class InputHandler {
    private static Scanner scanner = new Scanner(System.in);

    public static int inputDrawCount() {
        int drawChoice;
        final int MINIMUM_DRAW = 1;
        final int MAXIMUM_DRAW = 3;
        String drawPrompt = "\nEnter '1' for draw one and '3' for draw three (1,3): ";

        while (true) {
            drawChoice = inputInt(drawPrompt, MINIMUM_DRAW, MAXIMUM_DRAW);
            if (drawChoice != 2) return drawChoice;
            System.out.println("Make sure to enter either 1 or 3 only.");
        }
    }

    public static ShuffleType inputShuffleChoice() {
        int shuffleChoice;
        final int FIRST_CHOICE = 1;
        final int SECOND_CHOICE = 2;
        String shufflePrompt = "\nEnter '1' for perfect shuffle and '2' for random shuffle (1,2): ";

        shuffleChoice = inputInt(shufflePrompt, FIRST_CHOICE, SECOND_CHOICE);

        if (shuffleChoice == FIRST_CHOICE) {
            System.out.println(ColorHelper.color("Shuffle type is set to perfect shuffle.", "YELLOW"));
            return ShuffleType.PERFECT;
        } else {
            System.out.println(ColorHelper.color("Shuffle type is set to random shuffle.", "YELLOW"));
            return ShuffleType.RANDOM;
        }
    }

    public static int inputSimulationChoice() {
        int simChoice;
        String simPrompt = "\nHow do you want to simulate? \n1. Single Game\n2. Check win rate (100 games)";
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

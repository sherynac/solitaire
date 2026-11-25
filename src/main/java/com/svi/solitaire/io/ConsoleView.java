package com.svi.solitaire.io;

import com.svi.solitaire.models.Deck;
import com.svi.solitaire.models.Game;
import com.svi.solitaire.solver.Move;

/**
 * Displays messages and states to the console
 *
 * @author Sheryn Cagulada
 * @version 1.0
 */
public class ConsoleView {

    /**
     * Prints the welcome message
     */
    public static void printWelcome() {
        System.out.println("========== Welcome to Solitaire! ==========");
        System.out.println("\n!! Game Settings !! ");
    }

    /**
     * Prints prompt messages
     *
     * @param prompt the prompt message
     */
    public static void printPrompt(String prompt) {
        System.out.print(prompt);
    }

    /**
     * Prints error messages
     *
     * @param error the error message
     */
    public static void printErrorMessage(String error) {
        System.out.println(ConsoleColor.RED + error + ConsoleColor.RESET);
    }

    /**
     * Prints action done by the program
     *
     * @param action the action done
     */
    public static void printAction(String action) {
        System.out.println(ConsoleColor.YELLOW + action + ConsoleColor.RESET);
    }

    /**
     * Print the content of the deck
     *
     * @param text message
     * @param deck the current deck state
     */
    public static void printDeck(String text, Deck deck) {
        System.out.println(ConsoleColor.YELLOW + text + ConsoleColor.RESET);
        System.out.println(deck);
    }

    /**
     * Print the current game state
     *
     * @param game the current game state
     * @param move the move executed
     * @param moveCount the move number
     */
    public static void printGameState(Game game, Move move, int moveCount) {

        if (move == null) {
            System.out.println(ConsoleColor.BLUE + "\n====================== Initial State ==========================" + ConsoleColor.RESET);
        } else {
            System.out.println(ConsoleColor.BLUE + "\n====================== Move " + moveCount + " ==========================" + ConsoleColor.RESET);
            System.out.println(ConsoleColor.GREEN + "Move: " + move + ConsoleColor.RESET);
        }

        System.out.println(game);
        System.out.println(ConsoleColor.BLUE + "=========================================================" + ConsoleColor.RESET);
    }

    public static void printGoodbye() {
        System.out.println("========== Thank you for playing Solitaire! ==========");
    }
}

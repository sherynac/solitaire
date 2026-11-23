package com.svi.solitaire.io;

import com.svi.solitaire.models.Deck;
import com.svi.solitaire.models.Game;
import com.svi.solitaire.solver.Move;

public class ConsoleView {

    public static void printWelcome() {
        System.out.println("========== Welcome to Solitaire! ==========");
        System.out.println("\n!! Game Settings !! ");
    }

    public static void printPrompt(String prompt) {
        System.out.print(prompt);
    }

    public static void printErrorMessage(String error) {
        System.out.println(ConsoleColor.RED + error + ConsoleColor.RESET);
    }

    public static void printSuccessfulAction(String action) {
        System.out.println(ConsoleColor.YELLOW + action + ConsoleColor.RESET);
    }

    public static void printDeck(String text, Deck deck) {
        System.out.println(ConsoleColor.YELLOW + text + ConsoleColor.RESET);
        System.out.println(deck);
    }

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
}

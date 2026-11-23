package com.svi.solitaire.io;

import com.svi.solitaire.config.Color;
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
        System.out.println(Color.RED + error + Color.RESET);
    }

    public static void printSuccessfulAction(String action) {
        System.out.println(Color.YELLOW + action + Color.RESET);
    }

    public static void printGameState(Game game, Move move) {

        if (move == null) {
            System.out.println(Color.BLUE + "\n====================== Initial State ==========================" + Color.RESET);
        } else {
            System.out.println(Color.BLUE + "\n====================== " + move + " ==========================" + Color.RESET);
        }

        System.out.println(game);
        System.out.println(Color.BLUE + "=========================================================" + Color.RESET);
    }
}

package com.svi.solitaire.io;

import com.svi.solitaire.enums.Color;
import com.svi.solitaire.utils.ColorHelper;

public class ConsoleView {

    public static void printWelcome() {
        System.out.println("========== Welcome to Solitaire! ==========");
        System.out.println("\n!! Game Settings !! ");
    }

    public static void printPrompt(String prompt) {
        System.out.print(prompt);
    }

    public static void printErrorMessage(String error) {
        System.out.println(ColorHelper.color(error, Color.RED));
    }

    public static void printSuccessfulAction(String action) {
        System.out.println(ColorHelper.color(action, Color.YELLOW));
    }

}

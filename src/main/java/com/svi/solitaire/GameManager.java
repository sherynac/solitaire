package com.svi.solitaire;

import com.svi.solitaire.utils.InputHandler;

public class GameManager {

    public void run() {
        boolean willPlayAgain = true;
        try {
            while (willPlayAgain) {
                Game game = new Game();
                game.run();
                willPlayAgain = InputHandler.playAgain();
            }
        } catch (Exception e) {
            System.out.println("Program has encountered an error: " + e.getMessage());
            System.out.println("Kindly rerun the program");
        }
    }
}

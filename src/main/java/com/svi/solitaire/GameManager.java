package com.svi.solitaire;

import com.svi.solitaire.enums.Color;
import com.svi.solitaire.enums.ShuffleType;
import com.svi.solitaire.models.Game;
import com.svi.solitaire.utils.ColorHelper;
import com.svi.solitaire.utils.InputHandler;
import com.svi.solitaire.shuffle.ShuffleStrategy;
import com.svi.solitaire.shuffle.ShuffleStrategyFactory;

public class GameManager {
    private Game game;

    //TODO: creates new Game for new games, supposedly clear past data
    public void run() {
        boolean willPlayAgain = true;
        try {
            while (willPlayAgain) {
                System.out.println("========== Welcome to Solitaire! ==========");
                System.out.println("\n!! Game Settings !! ");
                int drawCount = InputHandler.inputDrawCount();
                System.out.println(ColorHelper.color("Game is set to draw " + drawCount + " from talon.", Color.YELLOW));

                ShuffleType shuffleChoice = InputHandler.inputShuffleChoice();
                System.out.println(ColorHelper.color("Game is set to " + shuffleChoice.getLabel() + ".", Color.YELLOW));

                ShuffleStrategy shuffleStrategy = ShuffleStrategyFactory.create(shuffleChoice);

                game = GameFactory.create(drawCount);


//                game = new Game(drawCount, shuffleStrategy);
//                game.setupGame();

//                int simulationChoice = InputHandler.inputSimulationChoice();
//
//                if (simulationChoice == 1) {
//                    playSingleGame();
//                } else {
//                    testWinRate();
//                }

                willPlayAgain = InputHandler.playAgain();
            }
        } catch (Exception e) {
            System.out.println("Program has encountered an error: " + e.getMessage());
            System.out.println("Kindly rerun the program");
        }
    }


    public void playSingleGame() {
        game.setupGame();
        game.displayCurrentState();

//        setupGame();
//
////        while (!isGameOver()) {
//        displayCurrentState();
//        talon.drawCards();
//        displayCurrentState();
//        talon.drawCards();
//
//        Card card = talon.drawWaste();
//        System.out.println("DRAWN: " + card);
//
//
//        addToFoundation(card);
//
//        displayCurrentState();
//
//        displayCurrentState();
//
////            talon.returnToStock();
////            displayCurrentState();
//
//        addToFoundation(talon.drawWaste());
//        displayCurrentState();

//        }

    }

//    public void testWinRate() {
//
//        for (int index = 0; index < 100; index++) {
//            game.setupGame();
//            game.displayCurrentState();
//
//            game.restartGame();
//        }
//    }
}

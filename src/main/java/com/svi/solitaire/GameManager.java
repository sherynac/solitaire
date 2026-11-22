package com.svi.solitaire;

import com.svi.solitaire.enums.Color;
import com.svi.solitaire.enums.ShuffleType;
import com.svi.solitaire.enums.Suit;
import com.svi.solitaire.io.ConsoleView;
import com.svi.solitaire.io.FileHandler;
import com.svi.solitaire.models.Deck;
//import com.svi.solitaire.models.Game;
import com.svi.solitaire.models.Game;
import com.svi.solitaire.solver.Move;
import com.svi.solitaire.solver.MoveEvaluator;
import com.svi.solitaire.solver.MoveExecutor;
import com.svi.solitaire.solver.MoveGenerator;
import com.svi.solitaire.utils.ColorHelper;
import com.svi.solitaire.io.InputHandler;
import com.svi.solitaire.shuffle.ShuffleStrategy;
import com.svi.solitaire.shuffle.ShuffleStrategyFactory;

import java.io.IOException;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class GameManager {
    private InputHandler input;
    private FileHandler fileHandler;
    private Random random = new Random();

    public GameManager() {

    }

    //TODO: creates new Game for new games, supposedly clear past data
    public void run() {
        boolean willPlayAgain = true;

//        try {
            while (willPlayAgain) {
                ConsoleView.printWelcome();

                Deck deck = loadDeckWithRetry();
                ConsoleView.printSuccessfulAction("Game has successfully read list of cards.");

//                int simulationChoice = InputHandler.inputSimulationChoice();
//
//                if (simulationChoice == 1) {
//                    playSingleGame();
//                } else {
//                    testWinRate();
//                }
//
                int drawCount = InputHandler.inputDrawCount();
                ConsoleView.printSuccessfulAction("Game is set to draw " + drawCount + " from talon.");

                ShuffleType shuffleChoice = InputHandler.inputShuffleChoice();
                ConsoleView.printSuccessfulAction("Game is set to " + shuffleChoice.getLabel() + ".");

                ShuffleStrategy shuffleStrategy = ShuffleStrategyFactory.create(shuffleChoice);
                int shuffleCount = random.nextInt(100) + 1;
                System.out.println("number of shuffles: " + shuffleCount);

                deck.setShuffleStrategy(shuffleStrategy);

                ConsoleView.printSuccessfulAction("\nDisplaying initial deck of cards: ");
                System.out.println(deck);

                deck.shuffleDeck(shuffleCount);

                ConsoleView.printSuccessfulAction("\nDisplaying shuffled deck of cards: ");
                System.out.println(deck);

                Game game = GameFactory.create(deck, drawCount);

                game.displayCurrentState();

                MoveGenerator moveGenerator = new MoveGenerator();
                MoveEvaluator moveEvaluator = new MoveEvaluator();
                MoveExecutor moveExecutor = new MoveExecutor();


                int counter = 0;
                Scanner scanner  = new Scanner(System.in);
                while (!game.isGameOver()) {
                    if (counter % 10 == 0) {
                        System.out.print("Enter to print next 10 moves: ");
                        scanner.nextLine();
                    }
                    List<Move> moves = moveGenerator.generate(game);
                    Move bestMove = moveEvaluator.findBestMove(moves, game);
                    if (bestMove == null) break;
                    moveExecutor.execute(bestMove, game);
                    game.displayCurrentState();
                    counter++;
                }


                willPlayAgain = InputHandler.playAgain();
            }
//        } catch (Exception e) {
//            ConsoleView.printErrorMessage("Program has encountered an error: " + e.getMessage());
//            ConsoleView.printErrorMessage("Kindly rerun the program.");
//        }
    }

    private Deck loadDeckWithRetry() {
        while (true) {
            String filename = InputHandler.inputFileName();

            try {
                return FileHandler.loadDeck(filename);
            } catch (IOException e) {
                ConsoleView.printErrorMessage("Unable to load deck: " + e.getMessage());
                ConsoleView.printErrorMessage("Kindly enter another filename.");
            } catch (IllegalArgumentException ie) {
                ConsoleView.printErrorMessage(ie.getMessage());
                ConsoleView.printErrorMessage("Kindly enter another filename.");
            }
        }
    }

    public void playSingleGame() {
//        game.setupGame();
//        game.displayCurrentState();

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

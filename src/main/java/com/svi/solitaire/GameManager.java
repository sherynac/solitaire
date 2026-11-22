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

                int drawCount = InputHandler.inputDrawCount();
                ConsoleView.printSuccessfulAction("Game is set to draw " + drawCount + " from talon.");

                ShuffleType shuffleChoice = InputHandler.inputShuffleChoice();
                ConsoleView.printSuccessfulAction("Game is set to " + shuffleChoice.getLabel() + ".");

                int simulationChoice = InputHandler.inputSimulationChoice();

                if (simulationChoice == 1) {
                    boolean won = playSingleGame(deck, drawCount, shuffleChoice, true);
                    ConsoleView.printSuccessfulAction(won ? "Game solved!" : "No more moves. Game not solved.");
                } else {
                    double rate = testWinRate(deck, drawCount, shuffleChoice);
                    ConsoleView.printSuccessfulAction(String.format("Win rate over %d games: %.1f%%", WIN_RATE_GAMES, rate));
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

    private static final int MAX_MOVES = 5000;
    private static final int WIN_RATE_GAMES = 100;

    public boolean playSingleGame(Deck originalDeck, int drawCount,
                                  ShuffleType shuffleChoice, boolean verbose) {
        originalDeck.reset();                     // add a copy() method if you don't have one
        originalDeck.setShuffleStrategy(ShuffleStrategyFactory.create(shuffleChoice));

        if (verbose) {
            ConsoleView.printSuccessfulAction("\nDisplaying initial deck of cards: ");
            System.out.println(originalDeck);
        }
        originalDeck.shuffleDeck(random.nextInt(100) + 1);
        if (verbose) {
            ConsoleView.printSuccessfulAction("\nDisplaying shuffled deck of cards: ");
            System.out.println(originalDeck);
        }

        Game game = GameFactory.create(originalDeck, drawCount);
        if (verbose) game.displayCurrentState();

        MoveGenerator moveGenerator = new MoveGenerator();
        MoveEvaluator moveEvaluator = new MoveEvaluator();
        MoveExecutor moveExecutor = new MoveExecutor();

        int moveCount = 0;
        int talonMovesSinceProgress = 0;

        while (!game.isGameOver() && moveCount < MAX_MOVES) {
            List<Move> moves = moveGenerator.generate(game);
            Move bestMove = moveEvaluator.findBestMove(moves, game);
            if (bestMove == null) break;                        // no useful move

            moveExecutor.execute(bestMove, game);
            moveCount++;

            if (bestMove.isTalonCycle()) talonMovesSinceProgress++;
            else talonMovesSinceProgress = 0;

            int cycleLength = game.getTalon().size() + 1;
            if (talonMovesSinceProgress > cycleLength) break;   // a full pass with no progress: stuck

            if (verbose) game.displayCurrentState();
        }
        return game.isGameOver();
    }

    public double testWinRate(Deck deck, int drawCount, ShuffleType shuffleChoice) {
        int wins = 0;
        for (int i = 0; i < WIN_RATE_GAMES; i++) {
            if (playSingleGame(deck, drawCount, shuffleChoice, false)) wins++;
        }
        return 100.0 * wins / WIN_RATE_GAMES;
    }
}

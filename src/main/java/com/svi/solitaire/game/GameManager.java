package com.svi.solitaire.game;

import com.svi.solitaire.utils.Constants;
import com.svi.solitaire.shuffle.ShuffleType;
import com.svi.solitaire.io.ConsoleView;
import com.svi.solitaire.io.FileHandler;
import com.svi.solitaire.io.InputHandler;
import com.svi.solitaire.models.Deck;

import java.io.IOException;
import java.util.List;
import java.util.Random;


public class GameManager {
    private final Random random;
    private final GameRunner gameRunner;
    private final SimulationRunner simulationRunner;

    public GameManager(GameRunner gameRunner, SimulationRunner simulationRunner, Random random) {
        this.random = random;
        this.gameRunner = gameRunner;
        this.simulationRunner = simulationRunner;
    }

    public void run() {
        boolean willPlayAgain = true;

        ConsoleView.printWelcome();

        Deck deck = loadDeckWithRetry();
        ConsoleView.printSuccessfulAction("Game has successfully read list of cards.");

        while (willPlayAgain) {
            int drawCount = InputHandler.inputDrawCount();
            ConsoleView.printSuccessfulAction("Game is set to draw " + drawCount + " from talon.");

            ShuffleType shuffleChoice = InputHandler.inputShuffleChoice();
            ConsoleView.printSuccessfulAction("Game is set to " + shuffleChoice.getLabel() + ".");

            int simulationChoice = InputHandler.inputSimulationChoice();

            if (simulationChoice == 1) {
                int shuffleCount = pickShuffleCount(shuffleChoice);
                boolean won = gameRunner.playSingleGame(deck, drawCount, shuffleChoice, shuffleCount, true);
                ConsoleView.printSuccessfulAction(won ? "Game solved!" : "No more moves. Game not solved.");
            } else {
                int randomGames = shuffleChoice == ShuffleType.RANDOM
                        ? InputHandler.inputGameCount()
                        : 0;

                List<Integer> shuffleCounts = simulationRunner.buildShuffleCounts(shuffleChoice, randomGames);
                int gamesPlayed = shuffleCounts.size();
                int wins = simulationRunner.testWins(deck, drawCount, shuffleChoice, shuffleCounts);
                double winRate = simulationRunner.computeWinRate(wins, gamesPlayed);

                ConsoleView.printSuccessfulAction(
                        String.format("Won %d of %d games (%.1f%%)", wins, gamesPlayed, winRate));
            }

            willPlayAgain = InputHandler.playAgain();
        }
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

    /**
     * Shuffle count for a single, watched game: none for "no shuffle", otherwise 1 to 100.
     */
    private int pickShuffleCount(ShuffleType shuffleChoice) {
        return shuffleChoice == ShuffleType.NO_SHUFFLE ? 0 : random.nextInt(Constants.MAX_RANDOM_SHUFFLES) + 1;
    }
}

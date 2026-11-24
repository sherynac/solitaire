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
        ConsoleView.printAction("Game has successfully read list of cards.");

        while (willPlayAgain) {
            int drawCount = InputHandler.inputDrawCount();
            ConsoleView.printAction("Game is set to draw " + drawCount + " from talon.");

            ShuffleType shuffleChoice = InputHandler.inputShuffleChoice();
            ConsoleView.printAction("Game is set to " + shuffleChoice.getLabel() + ".");

            int simulationChoice = InputHandler.inputSimulationChoice();

            if (simulationChoice == 1) {
                playSingleGame(shuffleChoice, deck, drawCount);
            } else {
                simulateGame(shuffleChoice, deck, drawCount);
            }

            willPlayAgain = InputHandler.playAgain();
        }

        ConsoleView.printGoodbye();
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

    private void playSingleGame(ShuffleType shuffleChoice, Deck deck, int drawCount) {
        int shuffleCount = pickShuffleCount(shuffleChoice);
        boolean won = gameRunner.playSingleGame(deck, drawCount, shuffleChoice, shuffleCount, true);
        ConsoleView.printAction(won ? "\n!! Congratulations, Game was solved !!" : "\n!! No more moves. Game not solved. !!");
    }

    private void simulateGame(ShuffleType shuffleChoice, Deck deck, int drawCount) {
        int randomGames = shuffleChoice == ShuffleType.RANDOM
                ? InputHandler.inputGameCount() : 0;

        List<Integer> shuffleCounts = simulationRunner.buildShuffleCounts(shuffleChoice, randomGames);
        int gamesPlayed = shuffleCounts.size();

        ConsoleView.printAction("\nRunning " + gamesPlayed + " games to determine win rate...");

        int wins = simulationRunner.testWins(deck, drawCount, shuffleChoice, shuffleCounts);
        double winRate = simulationRunner.computeWinRate(wins, gamesPlayed);

        ConsoleView.printAction(
                String.format("\n!! Won %d of %d games (%.1f%%) !!", wins, gamesPlayed, winRate));
    }
}

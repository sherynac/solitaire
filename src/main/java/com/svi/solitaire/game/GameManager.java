package com.svi.solitaire.game;

import com.svi.solitaire.enums.ShuffleType;
import com.svi.solitaire.io.ConsoleView;
import com.svi.solitaire.io.FileHandler;
import com.svi.solitaire.io.InputHandler;
import com.svi.solitaire.models.Deck;
import com.svi.solitaire.models.Game;
import com.svi.solitaire.shuffle.ShuffleStrategyFactory;
import com.svi.solitaire.solver.Move;
import com.svi.solitaire.solver.MoveEvaluator;
import com.svi.solitaire.solver.MoveExecutor;
import com.svi.solitaire.solver.MoveGenerator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameManager {
    private static final int MAX_MOVES = 5000;

    // A faro shuffle is deterministic. After this many shuffles a 52-card deck is back in its
    // original order, so these are all the distinct outcomes there are to test.
    private static final int IN_FARO_CYCLE = 52;
    private static final int OUT_FARO_CYCLE = 8;

    private static final int MAX_RANDOM_SHUFFLES = 100;

    private final Random random = new Random();

    public GameManager() {

    }

    /**
     * Result of a win-rate run.
     */
    public static class WinRateResult {
        private final int games;
        private final int wins;

        public WinRateResult(int games, int wins) {
            this.games = games;
            this.wins = wins;
        }

        public int getGames() {
            return games;
        }

        public int getWins() {
            return wins;
        }

        public double getPercentage() {
            return games == 0 ? 0 : 100.0 * wins / games;
        }
    }

    //TODO: creates new Game for new games, supposedly clear past data
    public void run() {
        boolean willPlayAgain = true;

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
                int shuffleCount = pickShuffleCount(shuffleChoice);
                boolean won = playSingleGame(deck, drawCount, shuffleChoice, shuffleCount, true);
                ConsoleView.printSuccessfulAction(won ? "Game solved!" : "No more moves. Game not solved.");
            } else {
                // only a random shuffle needs the user to say how many games to play
                int randomGames = shuffleChoice == ShuffleType.RANDOM
                        ? InputHandler.inputNumberOfGames()
                        : 0;
                WinRateResult result = testWinRate(deck, drawCount, shuffleChoice, randomGames);
                ConsoleView.printSuccessfulAction(String.format("Won %d of %d games (%.1f%%)",
                        result.getWins(), result.getGames(), result.getPercentage()));
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

    // ------------------------------------------------------------ one game

    /**
     * Shuffle count for a single, watched game: none for "no shuffle", otherwise 1 to 100.
     */
    private int pickShuffleCount(ShuffleType shuffleChoice) {
        return shuffleChoice == ShuffleType.NO_SHUFFLE ? 0 : random.nextInt(MAX_RANDOM_SHUFFLES) + 1;
    }

    public boolean playSingleGame(Deck deck, int drawCount, ShuffleType shuffleChoice,
                                  int shuffleCount, boolean verbose) {
        deck.reset();                                           // always start from the file order
        deck.setShuffleStrategy(ShuffleStrategyFactory.create(shuffleChoice));

        if (verbose) {
            ConsoleView.printSuccessfulAction("\nDisplaying initial deck of cards: ");
            System.out.println(deck);
        }

        if (shuffleCount > 0) {
            deck.shuffleDeck(shuffleCount);
        }

        if (verbose) {
            System.out.println("Number of shuffles: " + shuffleCount);
            ConsoleView.printSuccessfulAction("\nDisplaying shuffled deck of cards: ");
            System.out.println(deck);
        }

        Game game = GameFactory.create(deck, drawCount);
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

    // ------------------------------------------------------------ win rate

    /**
     * Plays one game per shuffle count in the plan for the chosen shuffle type:
     * in faro    -> 1..52 shuffles, once each (deterministic, nothing random)
     * out faro   -> 1..8 shuffles, once each (deterministic, nothing random)
     * no shuffle -> a single game
     * random     -> randomGames games, each with a random number of shuffles
     */
    public WinRateResult testWinRate(Deck deck, int drawCount, ShuffleType shuffleChoice, int randomGames) {
        List<Integer> shuffleCounts = buildShuffleCounts(shuffleChoice, randomGames);

        int wins = 0;
        for (int shuffleCount : shuffleCounts) {
            if (playSingleGame(deck, drawCount, shuffleChoice, shuffleCount, false)) {
                wins++;
            }
        }
        return new WinRateResult(shuffleCounts.size(), wins);
    }

    private List<Integer> buildShuffleCounts(ShuffleType shuffleChoice, int randomGames) {
        List<Integer> counts = new ArrayList<>();

        switch (shuffleChoice) {
            case IN_FARO:
                for (int i = 1; i <= IN_FARO_CYCLE; i++) counts.add(i);
                break;
            case OUT_FARO:
                for (int i = 1; i <= OUT_FARO_CYCLE; i++) counts.add(i);
                break;
            case NO_SHUFFLE:
                counts.add(0);
                break;
            default:                                            // random shuffle
                for (int i = 0; i < randomGames; i++) {
                    counts.add(random.nextInt(MAX_RANDOM_SHUFFLES) + 1);
                }
                break;
        }
        return counts;
    }
}

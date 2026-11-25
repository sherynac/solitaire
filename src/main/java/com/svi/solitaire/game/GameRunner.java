package com.svi.solitaire.game;

import com.svi.solitaire.io.ConsoleView;
import com.svi.solitaire.models.Deck;
import com.svi.solitaire.models.Game;
import com.svi.solitaire.shuffle.strategies.ShuffleStrategy;
import com.svi.solitaire.shuffle.ShuffleStrategyFactory;
import com.svi.solitaire.shuffle.ShuffleType;
import com.svi.solitaire.solver.Move;
import com.svi.solitaire.solver.MoveEvaluator;
import com.svi.solitaire.solver.MoveExecutor;
import com.svi.solitaire.solver.MoveGenerator;
import com.svi.solitaire.utils.Constants;

import java.util.List;

/**
 * Executes a single Solitaire game by preparing the deck, creating the game, and repeatedly selecting moves
 * until the game ends
 *
 * @author Sheryn Cagulada
 * @version 1.0
 */
public class GameRunner {
    private final MoveGenerator moveGenerator;
    private final MoveEvaluator moveEvaluator;
    private final MoveExecutor moveExecutor;

    public GameRunner(MoveGenerator moveGenerator, MoveEvaluator moveEvaluator, MoveExecutor moveExecutor) {
        this.moveGenerator = moveGenerator;
        this.moveEvaluator = moveEvaluator;
        this.moveExecutor = moveExecutor;
    }

    /**
     * Executes a single Solitaire game using the specified deck configuration
     * and shuffle settings.
     *
     * @param deck the deck of cards to use for the game
     * @param drawCount the number of cards drawn from the talon at a time
     * @param shuffleChoice the selected shuffle type
     * @param shuffleCount the number of times to shuffle the deck
     * @param toDisplay whether to display the deck and game states during execution
     * @return true if the game is won; false otherwise
     */
    public boolean playSingleGame(Deck deck, int drawCount, ShuffleType shuffleChoice,
                                  int shuffleCount, boolean toDisplay) {

        prepareDeck(deck, shuffleChoice, shuffleCount, toDisplay);

        Game game = GameFactory.create(deck, drawCount);
        if (toDisplay) {
            ConsoleView.printGameState(game, null, 0);
        }

        playMoves(game, toDisplay);

        return game.isGameOver();
    }

    /**
     * Resets content of the deck and shuffles deck based on chosen shuffle type
     *
     * @param deck standard deck
     * @param shuffleChoice the selected shuffle type
     * @param shuffleCount the number of time to shuffle the deck
     * @param toDisplay whether to display the deck and game states during execution
     */
    private void prepareDeck(Deck deck, ShuffleType shuffleChoice, int shuffleCount, boolean toDisplay) {
        deck.reset();

        if (toDisplay) {
            ConsoleView.printDeck("Displaying initial deck of cards: ", deck);
        }

        if (shuffleChoice != ShuffleType.NO_SHUFFLE) {
            ShuffleStrategy shuffleStrategy = ShuffleStrategyFactory.create(shuffleChoice);
            deck.setShuffleStrategy(shuffleStrategy);
            deck.shuffleDeck(shuffleCount);
        }

        if (toDisplay) {
            ConsoleView.printDeck("Displaying shuffled deck of cards: ", deck);
        }
    }

    /**
     * Generate, evaluate and execute the best move repeatedly
     * until the game ends or stopping condition is reached
     *
     * @param game current game state
     * @param toDisplay whether to display the deck and game states during execution
     */
    private void playMoves(Game game, boolean toDisplay) {
        int moveCount = 0;
        int talonMovesSinceProgress = 0;

        while (!game.isGameOver() && moveCount < Constants.MAX_MOVES) {
            List<Move> moves = moveGenerator.generate(game);
            Move bestMove = moveEvaluator.findBestMove(moves, game);

            if (bestMove == null) { // no found moves
                break;
            }

            moveExecutor.execute(bestMove, game);
            moveCount++;

            if (bestMove.isTalonCycle()) {
                talonMovesSinceProgress++;
            } else {
                talonMovesSinceProgress = 0;
            }

            int cycleLength = game.getTalon().size() + 1;
            if (talonMovesSinceProgress > cycleLength) {
                break;
            }

            if (toDisplay) {
                ConsoleView.printGameState(game, bestMove, moveCount);
            }
        }
    }
}

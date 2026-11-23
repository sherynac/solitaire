package com.svi.solitaire.game;

import com.svi.solitaire.io.ConsoleView;
import com.svi.solitaire.models.Deck;
import com.svi.solitaire.models.Game;
import com.svi.solitaire.shuffle.ShuffleStrategy;
import com.svi.solitaire.shuffle.ShuffleStrategyFactory;
import com.svi.solitaire.shuffle.ShuffleType;
import com.svi.solitaire.solver.Move;
import com.svi.solitaire.solver.MoveEvaluator;
import com.svi.solitaire.solver.MoveExecutor;
import com.svi.solitaire.solver.MoveGenerator;
import com.svi.solitaire.utils.Constants;

import java.util.List;

public class GameRunner {
    private final MoveGenerator moveGenerator;
    private final MoveEvaluator moveEvaluator;
    private final MoveExecutor moveExecutor;

    public GameRunner(MoveGenerator moveGenerator, MoveEvaluator moveEvaluator, MoveExecutor moveExecutor) {
        this.moveGenerator = moveGenerator;
        this.moveEvaluator = moveEvaluator;
        this.moveExecutor = moveExecutor;
    }

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

    private void prepareDeck(Deck deck, ShuffleType shuffleChoice,  int shuffleCount, boolean verbose) {
        deck.reset();

        if (verbose) {
            ConsoleView.printDeck("Displaying initial deck of cards: ", deck);
        }

        if (shuffleChoice != ShuffleType.NO_SHUFFLE) {
            ShuffleStrategy shuffleStrategy = ShuffleStrategyFactory.create(shuffleChoice);
            deck.setShuffleStrategy(shuffleStrategy);
            deck.shuffleDeck(shuffleCount);
        }

        if (verbose) {
            ConsoleView.printDeck("Displaying shuffled deck of cards: ", deck);
        }
    }

    private void playMoves(Game game, boolean toDisplay) {
        int moveCount = 0;
        int talonMovesSinceProgress = 0;

        while (!game.isGameOver() && moveCount < Constants.MAX_MOVES) {
            List<Move> moves = moveGenerator.generate(game);
            Move bestMove = moveEvaluator.findBestMove(moves, game);
            if (bestMove == null) {
                break;
            }

            moveExecutor.execute(bestMove, game);
            moveCount++;

            if (bestMove.isTalonCycle()) talonMovesSinceProgress++;
            else talonMovesSinceProgress = 0;

            int cycleLength = game.getTalon().size() + 1;
            if (talonMovesSinceProgress > cycleLength) {
                break;   // a full pass with no progress: stuck
            }

            if (toDisplay) {
                ConsoleView.printGameState(game, bestMove, moveCount);
            }
        }
    }


}

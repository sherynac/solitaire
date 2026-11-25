package com.svi.solitaire.game;

import com.svi.solitaire.models.Deck;
import com.svi.solitaire.shuffle.ShuffleType;
import com.svi.solitaire.utils.Constants;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Executes multiple Solitaire games to test overall winrate
 *
 * @author Sheryn Cagulada
 * @version 1.0
 */
public class SimulationRunner {

    private final GameRunner gameRunner;
    private final Random random;

    public SimulationRunner(GameRunner gameRunner, Random random) {
        this.gameRunner = gameRunner;
        this.random = random;
    }

    /**
     * Runs multiple game simulations using the specified shuffle counts
     * and counts the number of games won.
     *
     * @param deck the deck of cards to use for each game
     * @param drawCount the number of cards drawn from the talon at a time
     * @param shuffleChoice the selected shuffle type
     * @param shuffleCounts the list of shuffle counts to test
     * @return the total number of games won
     */
    public int testWins(Deck deck, int drawCount, ShuffleType shuffleChoice, List<Integer> shuffleCounts) {
        int wins = 0;
        for (int shuffleCount : shuffleCounts) {
            if (gameRunner.playSingleGame(deck, drawCount, shuffleChoice, shuffleCount, false)) {
                wins++;
            }
        }

        return wins;
    }

    /**
     * Calculates win rate
     *
     * @param wins number of wins
     * @param gameCount number of games
     * @return calculated win rate
     */
    public double computeWinRate(int wins, int gameCount) {
        return gameCount == 0 ? 0.0 : ((double) wins / gameCount) * 100;
    }

    /**
     * Create a list with the number of shuffle counts
     *
     * @param shuffleChoice the selected shuffle type
     * @param randomGames number of random games
     * @return list containing number of shuffles per simulated game
     */
    public List<Integer> buildShuffleCounts(ShuffleType shuffleChoice, int randomGames) {
        List<Integer> counts = new ArrayList<>();

        switch (shuffleChoice) {
            case IN_FARO:
                for (int count = 1; count <= Constants.IN_FARO_CYCLE; count++) {
                    counts.add(count);
                }
                break;

            case OUT_FARO:
                for (int count = 1; count <= Constants.OUT_FARO_CYCLE; count++) {
                    counts.add(count);
                }
                break;

            case NO_SHUFFLE:
                counts.add(0);
                break;

            default:
                for (int count = 0; count < randomGames; count++) {
                    counts.add(random.nextInt(Constants.MAX_RANDOM_SHUFFLES) + 1);
                }
                break;
        }
        return counts;
    }
}

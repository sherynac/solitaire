package com.svi.solitaire.game;

import com.svi.solitaire.models.Deck;
import com.svi.solitaire.shuffle.ShuffleType;
import com.svi.solitaire.utils.Constants;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SimulationRunner {

    private final GameRunner gameRunner;
    private final Random random;

    public SimulationRunner(GameRunner gameRunner, Random random) {
        this.gameRunner = gameRunner;
        this.random = random;
    }

    public int testWins(Deck deck, int drawCount, ShuffleType shuffleChoice, List<Integer> shuffleCounts) {
        int wins = 0;
        for (int shuffleCount : shuffleCounts) {
            if (gameRunner.playSingleGame(deck, drawCount, shuffleChoice, shuffleCount, false)) {
                wins++;
            }
        }

        return wins;
    }

    public double computeWinRate(int wins, int gameCount) {
        return gameCount == 0 ? 0.0 : ((double) wins / gameCount) * 100;
    }

    public List<Integer> buildShuffleCounts(ShuffleType shuffleChoice, int randomGames) {
        List<Integer> counts = new ArrayList<>();

        switch (shuffleChoice) {
            case IN_FARO:
                for (int i = 1; i <= Constants.IN_FARO_CYCLE; i++) counts.add(i);
                break;
            case OUT_FARO:
                for (int i = 1; i <= Constants.OUT_FARO_CYCLE; i++) counts.add(i);
                break;
            case NO_SHUFFLE:
                counts.add(0);
                break;
            default:                                            // random shuffle
                for (int i = 0; i < randomGames; i++) {
                    counts.add(random.nextInt(Constants.MAX_RANDOM_SHUFFLES) + 1);
                }
                break;
        }
        return counts;
    }
}

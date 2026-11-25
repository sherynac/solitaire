package com.svi.solitaire.shuffle;

import com.svi.solitaire.shuffle.strategies.InFaroShuffle;
import com.svi.solitaire.shuffle.strategies.OutFaroShuffle;
import com.svi.solitaire.shuffle.strategies.RandomShuffle;
import com.svi.solitaire.shuffle.strategies.ShuffleStrategy;

/**
 * Creates the ShuffleStrategy object
 *
 * @author Sheryn Cagulada
 * @version 1.0
 */
public class ShuffleStrategyFactory {

    /**
     * Creates the Shuffle Strategy based on Shuffle type
     *
     * @param type the shuffle type
     * @return the corresponding Shuffle strategy
     */
    public static ShuffleStrategy create(ShuffleType type) {
        return switch (type) {
            case RANDOM -> new RandomShuffle();
            case IN_FARO -> new InFaroShuffle();
            case OUT_FARO -> new OutFaroShuffle();
            case NO_SHUFFLE ->
                    throw new IllegalArgumentException(
                            "No strategy required for NO_SHUFFLE"
                    );
        };
    }

}

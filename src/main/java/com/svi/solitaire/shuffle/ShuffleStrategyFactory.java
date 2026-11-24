package com.svi.solitaire.shuffle;

import com.svi.solitaire.shuffle.strategies.InFaroShuffle;
import com.svi.solitaire.shuffle.strategies.OutFaroShuffle;
import com.svi.solitaire.shuffle.strategies.RandomShuffle;
import com.svi.solitaire.shuffle.strategies.ShuffleStrategy;

public class ShuffleStrategyFactory {

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

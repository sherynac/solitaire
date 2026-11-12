package com.svi.solitaire.shuffle.strategies;

import com.svi.solitaire.models.Card;
import com.svi.solitaire.shuffle.ShuffleStrategy;

import java.util.Collections;
import java.util.List;

public class RandomShuffle implements ShuffleStrategy {

    @Override
    public List<Card> shuffle(int numShuffles, List<Card> gameDeck) {
        for (int i = 0; i < numShuffles; i++){
            Collections.shuffle(gameDeck);
        }
        return gameDeck;
    }
}

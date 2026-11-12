package com.svi.solitaire.shuffle.strategies;

import com.svi.solitaire.models.Card;
import com.svi.solitaire.shuffle.ShuffleStrategy;

import java.util.List;

public class NoShuffle implements ShuffleStrategy {

    @Override
    public List<Card> shuffle(int numShuffles, List<Card> gameDeck) {
        return gameDeck;
    }
}

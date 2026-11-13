package com.svi.solitaire.shuffle.strategies;

import com.svi.solitaire.models.Card;
import com.svi.solitaire.shuffle.ShuffleStrategy;

import java.util.List;
import java.util.Random;

public class NoShuffle implements ShuffleStrategy {

    @Override
    public List<Card> shuffle(Random random, List<Card> gameDeck) {
        return gameDeck;
    }
}

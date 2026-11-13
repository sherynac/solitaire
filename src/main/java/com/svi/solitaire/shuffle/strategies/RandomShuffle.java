package com.svi.solitaire.shuffle.strategies;

import com.svi.solitaire.models.Card;
import com.svi.solitaire.shuffle.ShuffleStrategy;

import java.util.Collections;
import java.util.List;
import java.util.Random;

public class RandomShuffle implements ShuffleStrategy {

    @Override
    public List<Card> shuffle(Random random, List<Card> gameDeck) {
        int numShuffles = random.nextInt(100) + 1;
        for (int i = 0; i < numShuffles; i++){
            Collections.shuffle(gameDeck);
        }
        return gameDeck;
    }
}

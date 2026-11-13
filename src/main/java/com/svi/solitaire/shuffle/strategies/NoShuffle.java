package com.svi.solitaire.shuffle.strategies;

import com.svi.solitaire.models.Card;
import com.svi.solitaire.shuffle.ShuffleStrategy;

import java.util.Deque;
import java.util.Random;

public class NoShuffle implements ShuffleStrategy {

    @Override
    public Deque<Card> shuffle(Random random, Deque<Card> gameDeck) {
        return gameDeck;
    }
}

package com.svi.solitaire.shuffle.strategies;

import com.svi.solitaire.models.Card;

import java.util.Deque;

public interface ShuffleStrategy {
    Deque<Card> shuffle(int shuffleCount, Deque<Card> gameDeck);
}

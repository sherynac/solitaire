package com.svi.solitaire.shuffle;

import com.svi.solitaire.models.Card;

import java.util.Deque;
import java.util.Random;

public interface ShuffleStrategy {
    Deque<Card> shuffle(int shuffleCount, Deque<Card> gameDeck);
}

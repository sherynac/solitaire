package com.svi.solitaire.shuffle;

import com.svi.solitaire.models.Card;

import java.util.Deque;
import java.util.List;
import java.util.Random;

public interface ShuffleStrategy {
    Deque<Card> shuffle(Random random, Deque<Card> gameDeck);
}

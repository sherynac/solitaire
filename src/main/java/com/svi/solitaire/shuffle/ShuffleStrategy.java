package com.svi.solitaire.shuffle;

import com.svi.solitaire.models.Card;
import com.svi.solitaire.models.Deck;

import java.util.List;

public interface ShuffleStrategy {
    List<Card> shuffle(int numShuffles, List<Card> gameDeck);
}

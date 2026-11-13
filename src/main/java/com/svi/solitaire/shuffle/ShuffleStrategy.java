package com.svi.solitaire.shuffle;

import com.svi.solitaire.models.Card;
import java.util.List;
import java.util.Random;

public interface ShuffleStrategy {
    List<Card> shuffle(Random random, List<Card> gameDeck);
}

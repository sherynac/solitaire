package com.svi.solitaire.shuffle.strategies;

import com.svi.solitaire.models.Card;
import com.svi.solitaire.shuffle.ShuffleStrategy;

import java.util.*;

public class RandomShuffle implements ShuffleStrategy {

    @Override
    public Deque<Card> shuffle(int shuffleCount, Deque<Card> gameDeck) {
        List<Card> cardList = new ArrayList<>(gameDeck);
        for (int i = 0; i < shuffleCount; i++){
            Collections.shuffle(cardList);
        }
        return new ArrayDeque<>(cardList);
    }
}

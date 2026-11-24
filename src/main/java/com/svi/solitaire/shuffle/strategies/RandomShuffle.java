package com.svi.solitaire.shuffle.strategies;

import com.svi.solitaire.models.Card;

import java.util.*;

public class RandomShuffle implements ShuffleStrategy {

    @Override
    public Deque<Card> shuffle(int shuffleCount, Deque<Card> gameDeck) {
        List<Card> cardList = new ArrayList<>(gameDeck);
        for (int currShuffle = 0; currShuffle < shuffleCount; currShuffle++){
            Collections.shuffle(cardList);
        }
        return new ArrayDeque<>(cardList);
    }
}

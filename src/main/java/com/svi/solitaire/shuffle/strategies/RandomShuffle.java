package com.svi.solitaire.shuffle.strategies;

import com.svi.solitaire.models.Card;

import java.util.*;

/**
 * Contains the random faro shuffle logic
 *
 * @author Sheryn Cagulada
 * @version 1.0
 */
public class RandomShuffle implements ShuffleStrategy {

    /**
     * Performs in faro shuffle to game deck
     *
     * @param shuffleCount number of shuffles
     * @param gameDeck the deck to be shuffled
     * @return shuffled deque of cards
     */
    @Override
    public Deque<Card> shuffle(int shuffleCount, Deque<Card> gameDeck) {
        List<Card> cardList = new ArrayList<>(gameDeck);
        for (int currShuffle = 0; currShuffle < shuffleCount; currShuffle++){
            Collections.shuffle(cardList);
        }
        return new ArrayDeque<>(cardList);
    }
}

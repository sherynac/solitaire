package com.svi.solitaire.shuffle.strategies;

import com.svi.solitaire.models.Card;
import com.svi.solitaire.utils.Constants;

import java.util.*;

public class OutFaroShuffle implements ShuffleStrategy {

    @Override
    public Deque<Card> shuffle(int shuffleCount, Deque<Card> gameDeck) {
        List<Card> cardList = new ArrayList<>(gameDeck);

        for (int currShuffle = 0; currShuffle < shuffleCount; currShuffle++) {
            List<Card> list1 = cardList.subList(Constants.DECK_START, Constants.DECK_HALF);
            List<Card> list2 = cardList.subList(Constants.DECK_HALF, Constants.DECK_SIZE);
            List<Card> shuffledDeck = new ArrayList<>();

            for (int index = 0; index < list1.size(); index++) {
                shuffledDeck.add(list1.get(index));
                shuffledDeck.add(list2.get(index));
            }
            cardList = shuffledDeck;
        }

        return new ArrayDeque<>(cardList);
    }
}

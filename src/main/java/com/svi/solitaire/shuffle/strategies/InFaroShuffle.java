package com.svi.solitaire.shuffle.strategies;

import com.svi.solitaire.models.Card;
import com.svi.solitaire.shuffle.ShuffleStrategy;

import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.Random;

public class InFaroShuffle implements ShuffleStrategy {

    @Override
    public Deque<Card> shuffle(Random random, Deque<Card> gameDeck) {
        int numShuffles = random.nextInt(100) + 1;
        List<Card> cardList = new ArrayList<>(gameDeck);

        for (int k = 0; k < numShuffles; k++) {
            List<Card> list1 = cardList.subList(0,26);
            List<Card> list2 = cardList.subList(26,52);
            List<Card> shuffledDeck = new ArrayList<>();

            for (int i = 0; i < list1.size(); i++) {
                shuffledDeck.add(list2.get(i));
                shuffledDeck.add(list1.get(i));
            }
            cardList = shuffledDeck;
        }

        return gameDeck;
    }
}

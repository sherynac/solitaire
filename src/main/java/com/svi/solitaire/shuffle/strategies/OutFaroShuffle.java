package com.svi.solitaire.shuffle.strategies;

import com.svi.solitaire.models.Card;
import com.svi.solitaire.shuffle.ShuffleStrategy;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class OutFaroShuffle implements ShuffleStrategy {

    @Override
    public List<Card> shuffle(Random random, List<Card> gameDeck) {
        int numShuffles = random.nextInt(100) + 1;
        for (int k = 0; k < numShuffles; k++) {
            List<Card> list1 = gameDeck.subList(0,26);
            List<Card> list2 = gameDeck.subList(26,52);
            List<Card> shuffledDeck = new ArrayList<>();

            for (int i = 0; i < list1.size(); i++) {
                shuffledDeck.add(list1.get(i));
                shuffledDeck.add(list2.get(i));
            }
            gameDeck = shuffledDeck;
        }

        return gameDeck;
    }
}

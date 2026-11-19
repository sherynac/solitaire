package com.svi.solitaire.models;

import com.svi.solitaire.shuffle.ShuffleStrategy;

import java.util.*;

public class Deck {
    private Deque<Card> originalDeck;
    private Deque<Card> gameDeck;
    private ShuffleStrategy shuffleStrategy;

    public Deck(Deque<Card> readDeck) {
        originalDeck = readDeck;
        gameDeck = new ArrayDeque<>(originalDeck);
    }

    public Deck(ShuffleStrategy shuffleStrategy) {
        this.shuffleStrategy = shuffleStrategy;
    }

    public void addCard(Card card) {
        originalDeck.addLast(card);
    }

    public Card removeFirst() {
        return gameDeck.pop();
    }

    public boolean hasNext() {
        return gameDeck.iterator().hasNext();
    }

    public int size() {
        return originalDeck.size();
    }

    public void shuffleDeck(int shuffleCount) {
        Deque<Card> shuffled = shuffleStrategy.shuffle(shuffleCount, new ArrayDeque<>(gameDeck));
        gameDeck.clear();
        gameDeck.addAll(shuffled);
    }

    public void setShuffleStrategy(ShuffleStrategy shuffleStrategy) {
        this.shuffleStrategy = shuffleStrategy;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        Iterator<Card> deck = gameDeck.iterator();

        int count = 0;

        while (deck.hasNext()) {
            stringBuilder.append(deck.next());
            count++;

            if (deck.hasNext()) {
                if (count % 13 == 0) {
                    stringBuilder.append(System.lineSeparator());
                } else {
                    stringBuilder.append(",");
                }
            }
        }

        return stringBuilder.toString();
    }
}

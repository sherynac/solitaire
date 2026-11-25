package com.svi.solitaire.models;

import com.svi.solitaire.shuffle.strategies.ShuffleStrategy;

import java.util.*;

/**
 * Deck representation stores the standard deck, game deck, and shuffle strategy
 *
 * @author Sheryn Cagulada
 * @version 1.0
 */
public class Deck {
    private final Deque<Card> originalDeck;
    private final Deque<Card> gameDeck;
    private ShuffleStrategy shuffleStrategy;

    public Deck(Deque<Card> readDeck) {
        originalDeck = readDeck;
        gameDeck = new ArrayDeque<>(originalDeck);
    }

    /**
     * Sets a shuffle strategy
     *
     * @param shuffleStrategy chosen shuffle strategy
     */
    public void setShuffleStrategy(ShuffleStrategy shuffleStrategy) {
        this.shuffleStrategy = shuffleStrategy;
    }

    /**
     * Retrieve deck size
     *
     * @return deck size
     */
    public int size() {
        return originalDeck.size();
    }

    /**
     * Removes from the front of the game deck
     *
     * @return removed Card
     */
    public Card removeFirst() {
        return gameDeck.removeFirst();
    }

    /**
     * Removes from the end of the game deck
     *
     * @return removed Card
     */
    public Card removeLast() {
        return gameDeck.removeLast();
    }

    /**
     * Checks if there are more cards in the deck
     *
     * @return true if there is another card; false otherwise
     */
    public boolean hasNext() {
        return gameDeck.iterator().hasNext();
    }

    /**
     * Shuffles the deck based on shuffle strategy and number of shuffles
     *
     * @param shuffleCount number of shuffles
     */
    public void shuffleDeck(int shuffleCount) {
        Deque<Card> shuffled = shuffleStrategy.shuffle(shuffleCount, new ArrayDeque<>(gameDeck));
        gameDeck.clear();
        gameDeck.addAll(shuffled);
    }

    /**
     * Clears all elements inside the game deck and sets it to the standard deck
     */
    public void reset() {
        gameDeck.clear();
        gameDeck.addAll(originalDeck);
    }

    /**
     * Creates String representation of the deck
     *
     * @return deck string representation
     */
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

package com.svi.solitaire.models.piles;

import com.svi.solitaire.models.Card;

import java.util.List;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;

/**
 * Generic pile of cards using a Deque data structure
 *
 * @author Sheryn Cagulada
 * @version 1.0
 */
public class DequePile implements Pile {

    private final Deque<Card> cards;

    public DequePile() {
        this.cards = new ArrayDeque<>();
    }

    public DequePile(Deque<Card> cards) {
        this.cards = new ArrayDeque<>(cards);
    }

    /**
     * Checks if the pile is empty
     *
     * @return true if pile is empty; false otherwise
     */
    @Override
    public boolean isEmpty() {
        return cards.isEmpty();
    }

    /**
     * Retrieve the size of the pile
     *
     * @return the number of cards in the pile
     */
    @Override
    public int size() {
        return cards.size();
    }

    /**
     * Check the card at the top of the pile
     *
     * @return card at the top of the pil
     */
    @Override
    public Card peekTop() {
        return cards.peekLast();
    }

    /**
     * Add card to the top of the pile
     *
     * @param card card to be added
     */
    @Override
    public void add(Card card) {
        cards.addLast(card);
    }

    /**
     * Remove card from top of the pile
     *
     * @return card to be removed
     */
    @Override
    public Card removeTop() {
        return cards.removeLast();
    }

    /**
     * Retrieve a copy of the list of cards
     *
     * @return a copy card list
     */
    protected List<Card> snapshot() {              // bottom to top
        return new ArrayList<>(cards);
    }
}

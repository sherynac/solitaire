package com.svi.solitaire.models.piles;

import com.svi.solitaire.models.Card;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.stream.Collectors;
import java.util.List;

/**
 * Waste representation containing the copy and retrieval of top cards
 *
 * @author Sheryn Cagulada
 * @version 1.0
 */
public class Waste extends DequePile {
    private final int visibleCount;

    public Waste(int visibleCount) {
        this.visibleCount = visibleCount;
    }

    public Waste(int visibleCount, Deque<Card> cardDeque) {
        super(cardDeque);
        this.visibleCount = visibleCount;
    }

    /**
     * Creates a copy of the waste
     *
     * @return waste copy
     */
    public Waste copy() {
        Deque<Card> cardDeque = new ArrayDeque<>(snapshot());
        return new Waste(visibleCount, cardDeque);
    }

    /**
     * Gets the top cards of the waste
     *
     * @param n the count from the top
     * @return the list of top n cards
     */
    public List<Card> getTopCards(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("n must not be negative");
        }

        List<Card> all = snapshot();
        return List.copyOf(all.subList(Math.max(0, all.size() - n), all.size()));
    }

    /**
     * Creates the String representation of Waste
     *
     * @return the waste string representation
     */
    @Override
    public String toString() {
        return getTopCards(visibleCount).stream()
                .map(Object::toString)
                .collect(Collectors.joining(", ", "[", "] x" + size()));
    }
}

package com.svi.solitaire.models.piles;

import com.svi.solitaire.enums.Rank;
import com.svi.solitaire.models.Card;

import java.util.*;

/**
 * Represents a maneuver column containing hidden and visible cards.
 *
 * @author Sheryn Cagulada
 * @version 1.0
 */
public class ManeuverPile implements Pile {

    private final Deque<Card> hiddenCards;
    private final Deque<Card> visibleCards;

    /**
     * Creates an empty maneuver pile.
     */
    public ManeuverPile() {
        hiddenCards = new ArrayDeque<>();
        visibleCards = new ArrayDeque<>();
    }

    /**
     * Creates a maneuver pile initialized with the given hidden and visible cards.
     *
     * @param hiddenCards the hidden cards, ordered from bottom to top
     * @param visibleCards the visible cards, ordered from bottom to top
     */
    public ManeuverPile(Deque<Card> hiddenCards, Deque<Card> visibleCards) {
        this.hiddenCards = new ArrayDeque<>(hiddenCards);
        this.visibleCards = new ArrayDeque<>(visibleCards);
    }

    /**
     * Creates an independent copy of this maneuver pile.
     *
     * @return a new maneuver pile containing copies of the hidden and visible cards
     */
    public ManeuverPile copy() {
        return new ManeuverPile(hiddenCards, visibleCards);
    }

    /**
     * Returns the total number of hidden and visible cards in the pile.
     *
     * @return the total number of cards
     */
    @Override
    public int size() {
        return hiddenCards.size() + visibleCards.size();
    }

    /**
     * Returns the number of visible cards in the pile.
     *
     * @return the number of visible cards
     */
    public int visibleCount() {
        return visibleCards.size();
    }

    /**
     * Returns the number of hidden cards in the pile.
     *
     * @return the number of hidden cards
     */
    public int hiddenCount() {
        return hiddenCards.size();
    }

    /**
     * Checks whether the pile contains no hidden or visible cards.
     *
     * @return true if the pile is empty; false otherwise
     */
    @Override
    public boolean isEmpty() {
        return hiddenCards.isEmpty() && visibleCards.isEmpty();
    }

    /**
     * Returns the topmost visible card without removing it.
     *
     * @return the topmost visible card, or null if no visible cards exist
     */
    @Override
    public Card peekTop() {
        return visibleCards.peekLast();
    }

    /**
     * Checks whether the pile contains no visible cards.
     *
     * @return true if no visible cards exist; false otherwise
     */
    public boolean hasNoVisibleCards() {
        return visibleCards.isEmpty();
    }

    /**
     * Checks whether the pile contains hidden cards.
     *
     * @return true if hidden cards exist; false otherwise
     */
    public boolean hasHiddenCards() {
        return !hiddenCards.isEmpty();
    }

    /**
     * Adds a visible card to the top of the pile.
     *
     * @param card the card to add
     */
    @Override
    public void add(Card card) {
        visibleCards.add(card);
    }

    /**
     * Adds multiple visible cards to the top of the pile.
     *
     * @param cards the cards to add, ordered from bottom to top
     */
    public void add(List<Card> cards) {
        visibleCards.addAll(cards);
    }

    /**
     * Adds a hidden card to the pile.
     *
     * @param card the card to add
     */
    public void addHidden(Card card) {
        hiddenCards.addLast(card);
    }

    /**
     * Removes and returns the topmost visible card.
     *
     * @return the removed topmost visible card
     * @throws NoSuchElementException if no visible cards exist
     */
    @Override
    public Card removeTop() {
        return visibleCards.removeLast();
    }

    /**
     * Reveals the topmost hidden card by moving it to the visible cards.
     *
     * @throws NoSuchElementException if no hidden cards exist
     */
    public void flipTop() {
        Card card = hiddenCards.peekLast();
        hiddenCards.removeLast();
        visibleCards.addFirst(card);
    }

    /**
     * Returns the nth visible card counting from the top of the pile.
     *
     * @param n the zero-based position from the top
     * @return the card at the specified position, or {@code null} if the
     * position is outside the range of visible cards
     */
    public Card cardFromTop(int n) {
        if (n < 0 || n >= visibleCards.size()) {
            return null;
        }

        Iterator<Card> it = visibleCards.descendingIterator();

        for (int i = 0; i < n; i++) {
            it.next();
        }

        return it.next();
    }

    /**
     * Returns the display representation of the card at the specified index.
     *
     * @param index the zero-based index of the card from the bottom
     * @return the display representation of the card
     * @throws IndexOutOfBoundsException if the index is outside the range
     * of cards in the pile
     */
    public String getDisplayCardAt(int index) {
        if (index < 0 || index >= size()) {
            throw new IndexOutOfBoundsException("Invalid card index: " + index);
        }

        if (index < hiddenCards.size()) {
            return " ?";
        }

        int visibleIndex = index - hiddenCards.size();

        Iterator<Card> iterator = visibleCards.iterator();

        for (int i = 0; i < visibleIndex; i++) {
            iterator.next();
        }

        return iterator.next().toString();
    }

    /**
     * Checks whether the specified card can be placed on top of this pile
     * according to the maneuver column rules.
     *
     * @param card the card to check
     * @return {@code true} if the card can be placed; {@code false} otherwise
     */
    public boolean canAccept(Card card) {
        if (card == null) {
            return false;
        }

        if (visibleCards.isEmpty()) {
            return card.rank() == Rank.KING;
        }

        Card exposedCard = visibleCards.peekLast();

        return card.isOneLowerThan(exposedCard);
    }
}
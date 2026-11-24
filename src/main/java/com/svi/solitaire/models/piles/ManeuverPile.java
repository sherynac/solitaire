package com.svi.solitaire.models.piles;

import com.svi.solitaire.enums.Rank;
import com.svi.solitaire.models.Card;

import java.util.*;

public class ManeuverPile implements Pile {
    private final Deque<Card> hiddenCards;
    private final Deque<Card> visibleCards;

    public ManeuverPile() {
        hiddenCards = new ArrayDeque<>();
        visibleCards = new ArrayDeque<>();
    }

    public ManeuverPile(Deque<Card> hiddenCards, Deque<Card> visibleCards) {
        this.hiddenCards = new ArrayDeque<>(hiddenCards);
        this.visibleCards = new ArrayDeque<>(visibleCards);
    }

    public ManeuverPile copy() {
        return new ManeuverPile(hiddenCards, visibleCards);
    }

    @Override
    public int size() {
        return hiddenCards.size() + visibleCards.size();
    }

    public int visibleCount() {
        return visibleCards.size();
    }

    public int hiddenCount() {
        return hiddenCards.size();
    }

    public boolean isEmpty() {
        return (hiddenCards.isEmpty() && visibleCards.isEmpty());
    }

    @Override
    public Card peekTop() {
        return visibleCards.peekLast();
    }

    public boolean hasNoVisibleCards() {
        return visibleCards.isEmpty();
    }

    public boolean hasHiddenCards() {
        return !hiddenCards.isEmpty();
    }

    @Override
    public void add(Card card) {
        visibleCards.add(card);
    }

    public void add(List<Card> cards) {
        visibleCards.addAll(cards);
    }

    public void addHidden(Card card) {
        hiddenCards.addLast(card);
    }

    @Override
    public Card removeTop() {
        return visibleCards.removeLast();
    }

    public void flipTop() {
        Card card = hiddenCards.peekLast();
        hiddenCards.removeLast();
        visibleCards.addFirst(card);
    }

    /**
     * n-th visible card from the top (0 = top card), or null if there is none.
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

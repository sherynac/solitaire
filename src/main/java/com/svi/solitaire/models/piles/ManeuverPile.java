package com.svi.solitaire.models.piles;

import com.svi.solitaire.enums.Rank;
import com.svi.solitaire.models.Card;

import java.util.*;

public class ManeuverPile implements Pile {
    private final Deque<Card> hiddenDeque;
    private final Deque<Card> visibleDeque;

    public ManeuverPile(){
        hiddenDeque = new ArrayDeque<>();
        visibleDeque = new ArrayDeque<>();
    }

    @Override
    public void push(Card card) {
        visibleDeque.push(card);
    }

    public Card peekLast() {
        return visibleDeque.peekLast();
    }

    public void addLast(Card card) {
        hiddenDeque.addLast(card);
    }

    public void addFlippedCard(Card card) {
        visibleDeque.addLast(card);
    }

    public void addFlippedCards(Deque<Card> cards) {
        visibleDeque.addAll(cards);
    }

    public void removeLast() {
        visibleDeque.removeLast();
    }

    public boolean hasNoVisibleCards() {
        return visibleDeque.isEmpty();
    }

    public boolean hasHiddenCards() {
        return !hiddenDeque.isEmpty();
    }


    public void flipTop() {
        Card card = hiddenDeque.peekLast();
        hiddenDeque.removeLast();
        visibleDeque.addLast(card);
    }

    @Override
    public int size() {
        return hiddenDeque.size() + visibleDeque.size();
    }

    public int visibleCount() {
        return visibleDeque.size();
    }

    public int hiddenCount() {
        return hiddenDeque.size();
    }

    @Override
    public void reset() {
        hiddenDeque.clear();
        visibleDeque.clear();
    }

    public boolean isEmpty() {
        return (hiddenDeque.isEmpty() && visibleDeque.isEmpty());
    }

    /** n-th card from the top (0 = top), or null. */
    public Card cardFromTop(int n) {
        int i = 0;
        for (Card card : visibleDeque) if (i++ == n) return card;
        return null;
    }

//    public void addCards(Deque<Card> movingCard) {
//        for (Card card : movingCard) {
//            cardDeque.push(card);
//        }
//    }

    public String getDisplayCardAt(int index) {
        if (index < 0 || index >= size()) {
            throw new IndexOutOfBoundsException("Invalid card index: " + index);
        }

        if (index < hiddenDeque.size()) {
            return " ?";
        }

        int visibleIndex = index - hiddenDeque.size();

        Iterator<Card> iterator = visibleDeque.iterator();

        for (int i = 0; i < visibleIndex; i++) {
            iterator.next();
        }

        return iterator.next().toString();
    }

    public boolean canAccept(Card card) {
        if (card == null) {
            return false;
        }

        if (visibleDeque.isEmpty()) {
            return card.getRank() == Rank.KING;
        }

        Card exposedCard = visibleDeque.peekLast();

        return card.isOneLowerThan(exposedCard);
    }

}

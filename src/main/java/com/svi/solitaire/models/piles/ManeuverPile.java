package com.svi.solitaire.models.piles;

import com.svi.solitaire.enums.Rank;
import com.svi.solitaire.models.Card;

import java.util.*;

public class ManeuverPile implements Pile {
    private final Deque<Card> hiddenCards;
    private final List<Card> visibleCards;

    public ManeuverPile(){
        hiddenCards = new ArrayDeque<>();
        visibleCards = new ArrayList<>();
    }

    public void addHidden(Card card) {
        hiddenCards.addLast(card);
    }

    public boolean hasNoVisibleCards() {
        return visibleCards.isEmpty();
    }

    public boolean hasHiddenCards() {
        return !hiddenCards.isEmpty();
    }


    public void flipTop() {
        Card card = hiddenCards.peekLast();
        hiddenCards.removeLast();
        visibleCards.add(card);
    }

    @Override
    public int size() {
        return hiddenCards.size() + visibleCards.size();
    }

    @Override
    public Card peekTop() {
        return visibleCards.isEmpty() ? null : visibleCards.get(visibleCards.size() - 1);
    }

    @Override
    public void add(Card card) {
        visibleCards.add(card);
    }

    public void add (List<Card> cards) {
        visibleCards.addAll(cards);
    }

    @Override
    public Card removeTop() {
        return visibleCards.remove(visibleCount() -1);
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

    /** n-th card from the top (0 = top), or null. */
    public Card cardFromTop(int n) {
        int i = 0;
        for (Card card : visibleCards) if (i++ == n) return card;
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
            return card.getRank() == Rank.KING;
        }

        Card exposedCard = visibleCards.get(visibleCount()-1);

        return card.isOneLowerThan(exposedCard);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("HIDDEN DEQUE: ");
        for (Card card : hiddenCards) {
            stringBuilder.append(card).append(",");
        }

        stringBuilder.append("\nVISIBLE DEQUE: ");
        for (Card card : visibleCards) {
            stringBuilder.append(card).append(",");
        }

        return stringBuilder.toString();
    }
}

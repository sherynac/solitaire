package com.svi.solitaire.models.piles;

import com.svi.solitaire.enums.Rank;
import com.svi.solitaire.models.Card;

import java.util.*;

public class ManeuverPile implements Pile {
    private final Deque<Card> hiddenCards;
    private final Deque<Card> visibleCards;

    public ManeuverPile(){
        hiddenCards = new ArrayDeque<>();
        visibleCards = new ArrayDeque<>();
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
        System.out.println("FLIPPED " + card);

        hiddenCards.removeLast();
        visibleCards.addFirst(card);
    }

    @Override
    public int size() {
        return hiddenCards.size() + visibleCards.size();
    }

    @Override
    public Card peekTop() {
        return visibleCards.peekLast();
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
        return visibleCards.removeLast();
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
    /** n-th visible card from the top (0 = top card), or null if there is none. */
    public Card cardFromTop(int n) {
        if (n < 0 || n >= visibleCards.size()) {
            return null;
        }

        List<Card> visibleList = new ArrayList<>(visibleCards);

        return visibleList.get(visibleCount() - 1 - n);
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

        Card exposedCard = visibleCards.peekLast();

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

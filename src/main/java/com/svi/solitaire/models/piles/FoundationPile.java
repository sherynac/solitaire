package com.svi.solitaire.models.piles;

import com.svi.solitaire.enums.Rank;
import com.svi.solitaire.enums.Suit;
import com.svi.solitaire.models.Card;

import java.util.ArrayDeque;
import java.util.Deque;

public class FoundationPile implements Pile {
    private Suit suit;
    private Deque<Card> foundation;

    public FoundationPile(Suit suit) {
        this.suit = suit;
        foundation = new ArrayDeque<>();
    }

    public Suit getSuit() {
        return suit;
    }

    public Card peek() {
        return foundation.peek();
    }

    public boolean canAccept(Card card) {
        if (card == null || card.getSuit() != suit) {
            return false;
        }

        if (foundation.isEmpty()) {
            return card.getRank() == Rank.ACE;
        }

        Card foundationTop = foundation.peek();

        return card.getRank().getValue() == foundationTop.getRank().getValue() + 1;
    }

    @Override
    public void push(Card card) {
        if (!canAccept(card)) {
            throw new IllegalArgumentException("Invalid card for foundation");
        }

        foundation.push(card);
    }

    @Override
    public int size() {
        return foundation.size();
    }

    @Override
    public void reset() {
        foundation.clear();
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("[");

        if (foundation.isEmpty()) {
            stringBuilder.append(suit.getSymbol());
        } else {
            stringBuilder.append(foundation.peek().toString());
        }

        stringBuilder.append("]");

        return stringBuilder.toString();
    }

}

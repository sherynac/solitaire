package com.svi.solitaire.models;

import com.svi.solitaire.enums.Suit;

import java.util.Stack;

public class Foundation {
    Suit suit;
    Stack<Card> foundation;

    public Foundation(Suit suit) {
        this.suit = suit;
        foundation = new Stack<>();
    }

    public int getCardCount() {
        return foundation.size();
    }

    public void addToFoundation(Card card) {
        if (card.getSuit() == suit) {
            foundation.push(card);
        } else {
            throw new IllegalArgumentException("Incorrect card suit being added to foundation");
        }
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("[");

        if (foundation.isEmpty()) {
            stringBuilder.append(suit.name());
        } else {
            stringBuilder.append(foundation.peek().toString());
        }

        stringBuilder.append("]");

        return stringBuilder.toString();
    }
}

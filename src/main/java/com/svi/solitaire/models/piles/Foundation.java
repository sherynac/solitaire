package com.svi.solitaire.models.piles;

import com.svi.solitaire.enums.Rank;
import com.svi.solitaire.enums.Suit;
import com.svi.solitaire.models.Card;

import java.util.ArrayDeque;
import java.util.Deque;

public class Foundation implements Pile {
    Suit suit;
    Deque<Card> foundation;

    public Foundation(Suit suit) {
        this.suit = suit;
        foundation = new ArrayDeque<>();
    }

    public int getCardCount() {
        return foundation.size();
    }

    public Suit getSuit() {
        return suit;
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

    @Override
    public void add(Card card) {
        if (card.getSuit() == suit) {

            Card topCard = foundation.peek();

            if (topCard == null) {

                if (card.getRank() == Rank.ACE) {
                    foundation.push(card);
                }

            } else if (card.getRank().getStrength() == topCard.getRank().getStrength() + 1) {
                System.out.println("EQUAL");
                foundation.push(card);
            }

        } else {
            throw new IllegalArgumentException("Incorrect card suit being added to foundation");
        }
    }
}

package com.svi.solitaire.models.piles;

import com.svi.solitaire.enums.Rank;
import com.svi.solitaire.enums.Suit;
import com.svi.solitaire.models.Card;

public class Foundation extends DequePile {
    private Suit suit;

    public Foundation(Suit suit) {
        this.suit = suit;
    }

    public Suit getSuit() {
        return suit;
    }

    public boolean canAccept(Card card) {
        if (card == null || card.getSuit() != suit) {
            return false;
        }

        if (isEmpty()) {
            return card.getRank() == Rank.ACE;
        }

        Card foundationTop = peekTop();

        return card.getRank().getValue() == foundationTop.getRank().getValue() + 1;
    }

//    public void push(Card card) {
//        if (!canAccept(card)) {
//            throw new IllegalArgumentException("Invalid card for foundation");
//        }
//
//        add(card);
//    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("[");

        if (isEmpty()) {
            stringBuilder.append(suit.getSymbol());
        } else {
            stringBuilder.append(peekTop().toString());
        }

        stringBuilder.append("]");

        return stringBuilder.toString();
    }

}

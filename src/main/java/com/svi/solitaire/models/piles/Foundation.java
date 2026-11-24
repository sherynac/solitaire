package com.svi.solitaire.models.piles;

import com.svi.solitaire.enums.Rank;
import com.svi.solitaire.enums.Suit;
import com.svi.solitaire.models.Card;

import java.util.ArrayDeque;
import java.util.Deque;

public class Foundation extends DequePile {
    private Suit suit;

    public Foundation(Suit suit) {
        this.suit = suit;
    }

    public Foundation(Suit suit, Deque<Card> cardDeque) {
        this.suit = suit;
        super(cardDeque);
    }

    public Foundation copy() {
        Deque<Card> cardDeque = new ArrayDeque<>(snapshot());
        return new Foundation(suit, cardDeque);
    }

    public boolean canAccept(Card card) {
        if (card == null || card.suit() != suit) {
            return false;
        }

        if (isEmpty()) {
            return card.rank() == Rank.ACE;
        }

        Card foundationTop = peekTop();

        return card.rank().getValue() == foundationTop.rank().getValue() + 1;
    }

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

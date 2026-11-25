package com.svi.solitaire.models.piles;

import com.svi.solitaire.enums.Rank;
import com.svi.solitaire.enums.Suit;
import com.svi.solitaire.models.Card;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * Foundation representation containing add, remove and accept conditions
 *
 * @author Sheryn Cagulada
 * @version 1.0
 */
public class Foundation extends DequePile {
    private Suit suit;

    public Foundation(Suit suit) {
        this.suit = suit;
    }

    public Foundation(Suit suit, Deque<Card> cardDeque) {
        super(cardDeque);
        this.suit = suit;
    }

    /**
     * Creates a copy of the foundation
     *
     * @return foundation copy
     */
    public Foundation copy() {
        Deque<Card> cardDeque = new ArrayDeque<>(snapshot());
        return new Foundation(suit, cardDeque);
    }

    /**
     * Checks if the foundation can accept a card
     *
     * @param card the card to be added
     * @return true if card can be accepted; false otherwise
     */
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

    /**
     * Creates String representation of a foundation
     *
     * @return foundation string representation
     */
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

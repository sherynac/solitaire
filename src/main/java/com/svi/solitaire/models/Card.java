package com.svi.solitaire.models;

import com.svi.solitaire.enums.Rank;
import com.svi.solitaire.enums.Suit;

public record Card(Rank rank, Suit suit) {
    public Card(Suit suit, Rank rank) {
        this(rank, suit);
    }

    public boolean isRed() {
        return Suit.HEARTS == suit || Suit.DIAMONDS == suit;
    }

    public boolean isOppositeColor(Card card) {
        return !suit.isRed() == card.suit().isRed();
    }

    public boolean isOneLowerThan(Card other) {
        return rank.getValue() == other.rank().getValue() - 1
                && isOppositeColor(other);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Card(Rank rank1, Suit suit1))) return false;

        return suit == suit1 && rank == rank1;
    }

    @Override
    public String toString() {
        return suit.getSymbol() + "-" + rank.getSymbol();
    }

}

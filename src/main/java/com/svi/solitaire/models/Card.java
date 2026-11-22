package com.svi.solitaire.models;

import com.svi.solitaire.enums.Rank;
import com.svi.solitaire.enums.Suit;

import java.util.Objects;

public class Card implements Comparable<Card> {
    private final Rank rank;
    private final Suit suit;

    public Card(Suit suit, Rank rank)  {
        this.rank = rank;
        this.suit = suit;
    }

    public Rank getRank() {
        return rank;
    }

    public Suit getSuit() {
        return suit;
    }

    public boolean isRed() {
        return Suit.HEARTS == suit || Suit.DIAMONDS == suit;
    }

    public boolean isOppositeColor(Card card) {
        return !suit.isRed() == card.getSuit().isRed();
    }

    public boolean isOneLowerThan(Card other) {
        return rank.getValue() == other.getRank().getValue() - 1
                && isOppositeColor(other);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Card other)) return false;

        return suit == other.suit && rank == other.rank;
    }

    @Override
    public int hashCode() {
        return Objects.hash(suit, rank);
    }

    @Override
    public String toString() {
        return suit.getSymbol() + "-" + rank.getSymbol();
    }

    @Override
    public int compareTo(Card o) {
        return 0;
    }
}

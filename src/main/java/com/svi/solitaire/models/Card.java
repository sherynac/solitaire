package com.svi.solitaire.models;

import com.svi.solitaire.enums.Rank;
import com.svi.solitaire.enums.Suit;

import java.util.Objects;

public class Card implements Comparable<Card> {
    private Rank rank;
    private Suit suit;

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
        if (Suit.HEARTS == suit || Suit.DIAMONDS == suit) {
            return true;
        } else {
            return false;
        }
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
        if (!(obj instanceof Card)) return false;

        Card other = (Card) obj;

        return suit == other.suit && rank == other.rank;
    }

    @Override
    public int hashCode() {
        return Objects.hash(suit, rank);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append(suit.getSymbol()).append("-");
        stringBuilder.append(rank.getSymbol());

        return stringBuilder.toString();
    }

    @Override
    public int compareTo(Card o) {
        return 0;
    }
}

package com.svi.solitaire.models;

import com.svi.solitaire.enums.Rank;
import com.svi.solitaire.enums.Suit;

import java.util.Objects;

public class Card {
    private Rank rank;
    private Suit suit;
    private boolean isVisible;

    public Card(Suit suit, Rank rank){
        this.rank = rank;
        this.suit = suit;
        isVisible = true;
    }

    public Rank getRank() {
        return rank;
    }

    public Suit getSuit() {
        return suit;
    }

    public boolean getVisibility() {
        return isVisible;
    }

    public void setVisible() {
        this.isVisible = true;
    }

    public void setInvisible() {
        this.isVisible = false;
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

        return (isVisible
                ? stringBuilder.toString()
                : " ?");
    }
}

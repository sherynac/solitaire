package com.svi.solitaire.models;

import com.svi.solitaire.enums.Rank;
import com.svi.solitaire.enums.Suit;

/**
 * Card representation stores the Suit and Rank of an object
 *
 * @param rank the card rank
 * @param suit the card suit
 */
public record Card(Rank rank, Suit suit) {
    public Card(Suit suit, Rank rank) {
        this(rank, suit);
    }

    /**
     * Checks if the card is red
     *
     * @return true if red; false otherwise
     */
    public boolean isRed() {
        return Suit.HEARTS == suit || Suit.DIAMONDS == suit;
    }

    /**
     * Checks if the card and another card is of opposite colors
     *
     * @param other the other card
     * @return true if opposite colors; false otherwise
     */
    public boolean isOppositeColor(Card other) {
        return !suit.isRed() == other.suit().isRed();
    }

    /**
     * Checks if the card is one rank lower than the other card
     *
     * @param other the other card
     * @return true if card is one lower than other rank and opposite colors; false otherwise
     */
    public boolean isOneLowerThan(Card other) {
        return rank.getValue() == other.rank().getValue() - 1
                && isOppositeColor(other);
    }

    /**
     * Checks if card is equal to another object
     *
     * @param obj   the reference object with which to compare.
     * @return  true if equal; false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Card(Rank rank1, Suit suit1))) return false;

        return suit == suit1 && rank == rank1;
    }

    /**
     * Creates the card String representation
     *
     * @return card string representation
     */
    @Override
    public String toString() {
        return suit.getSymbol() + "-" + rank.getSymbol();
    }

}

/**
 * Represents the ranks of playing cards and their corresponding numerical values.
 *
 * @author Sheryn Cagulada
 * @version October 30, 2026
 */

package com.svi.solitaire.enums;

public enum Suit {
    CLUBS(1, 'C'),
    SPADES(2, 'S'),
    HEARTS(3, 'H'),
    DIAMONDS(4, 'D');

    private final int strength;
    private final char symbol;

    /**
     * Constructs a suit with its corresponding value
     *
     * @param strength numerical value assigned to the suit
     */
    Suit(int strength, char symbol) {
        this.strength = strength;
        this.symbol = symbol;
    }

    public int getStrength() {
        return strength;
    }
}
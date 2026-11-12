/**
 * Represents the ranks of playing cards and their corresponding numerical values.
 *
 * @author Sheryn Cagulada
 * @version October 30, 2026
 */

package com.svi.solitaire.enums;

public enum Rank {
    ACE(1,"A"),
    TWO(2, "2"),
    THREE(3, "3"),
    FOUR(4, "4"),
    FIVE(5, "5"),
    SIX(6, "6"),
    SEVEN(7, "7"),
    EIGHT(8, "8"),
    NINE(9,"9"),
    TEN(10,"10"),
    JACK(11,"J"),
    QUEEN(12,"Q"),
    KING(13,"K");

    private final int strength;
    private final String symbol;

    /**
     * Constructs a rank with its corresponding numerical value
     *
     * @param strength numerical value assigned to the rank
     */
    Rank(int strength, String symbol) {
        this.strength = strength;
        this.symbol = symbol;
    }

    public int getStrength() {
        return strength;
    }
}
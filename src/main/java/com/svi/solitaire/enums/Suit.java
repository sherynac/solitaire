/**
 * Represents the ranks of playing cards and their corresponding numerical values.
 *
 * @author Sheryn Cagulada
 * @version October 30, 2026
 */

package com.svi.solitaire.enums;

public enum Suit {
    D(4),
    H(3),
    S(2),
    C(1);

    private final int value;

    /**
     * Constructs a suit with its corresponding value
     *
     * @param value numerical value assigned to the suit
     */
    Suit(int value) {
        this.value = value;
    }

    /**
     * Returns numerical value of suit
     *
     * @return value of Suit
     */
    public int getValue() {
        return value;
    }

}
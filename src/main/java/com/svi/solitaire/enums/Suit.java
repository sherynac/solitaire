/**
 * Represents the ranks of playing cards and their corresponding numerical values.
 *
 * @author Sheryn Cagulada
 * @version October 30, 2026
 */

package com.svi.solitaire.enums;

public enum Suit {
    DIAMONDS('D'),
    HEARTS('H'),
    SPADES( 'S'),
    CLUBS('C');

    private final char symbol;

    /**
     * Constructs a suit with its corresponding value
     *
     */
    Suit(char symbol) {
        this.symbol = symbol;
    }

    public char getSymbol() {
        return symbol;
    }
}
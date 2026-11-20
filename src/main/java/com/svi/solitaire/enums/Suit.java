/**
 * Represents the ranks of playing cards and their corresponding numerical values.
 *
 * @author Sheryn Cagulada
 * @version October 30, 2026
 */

package com.svi.solitaire.enums;

public enum Suit {
    DIAMONDS("D", true),
    HEARTS("H", true),
    SPADES( "S", false),
    CLUBS("C", false);

    private final String symbol;
    private final boolean isRed;

    /**
     * Constructs a suit with its corresponding value
     *
     */
    Suit(String symbol, boolean isRed) {
        this.symbol = symbol;
        this.isRed = isRed;
    }

    public String getSymbol() {
        return symbol;
    }

    public boolean isRed() {
        return isRed;
    }

    public static Suit fromText(String text) {
        for (Suit suit : values()) {
            if (suit.getSymbol().equals(text)) {
                return suit;
            }
        }

        throw new IllegalArgumentException("Suit does not exist: " + text);
    }
}
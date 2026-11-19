/**
 * Represents the ranks of playing cards and their corresponding numerical values.
 *
 * @author Sheryn Cagulada
 * @version October 30, 2026
 */

package com.svi.solitaire.enums;

public enum Suit {
    DIAMONDS("D"),
    HEARTS("H"),
    SPADES( "S"),
    CLUBS("C");

    private final String symbol;

    /**
     * Constructs a suit with its corresponding value
     *
     */
    Suit(String symbol) {
        this.symbol = symbol;
    }

    public String getSymbol() {
        return symbol;
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
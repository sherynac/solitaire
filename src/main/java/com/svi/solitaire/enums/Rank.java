/**
 * Represents the ranks of playing cards and their corresponding numerical values.
 *
 * @author Sheryn Cagulada
 * @version October 30, 2026
 */

package com.svi.solitaire.enums;

public enum Rank {
    ACE("A"),
    TWO( "2"),
    THREE( "3"),
    FOUR( "4"),
    FIVE("5"),
    SIX("6"),
    SEVEN("7"),
    EIGHT("8"),
    NINE("9"),
    TEN("10"),
    JACK("J"),
    QUEEN("Q"),
    KING("K");

    private final String symbol;

    /**
     * Constructs a rank with its corresponding numerical value
     *
     * @param strength numerical value assigned to the rank
     */
    Rank(String symbol) {
        this.symbol = symbol;
    }

    public String getSymbol() {
        return symbol;
    }

    public static Rank fromText(String text) {

        for (Rank rank : values()) {
            if (rank.getSymbol().equals(text)) {
                return rank;
            }
        }

        throw new IllegalArgumentException("Rank does not exist: " + text);
    }
}
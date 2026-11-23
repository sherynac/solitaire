package com.svi.solitaire.enums;

public enum Suit {
    DIAMONDS("D", true),
    HEARTS("H", true),
    SPADES("S", false),
    CLUBS("C", false);

    private final String symbol;
    private final boolean isRed;

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
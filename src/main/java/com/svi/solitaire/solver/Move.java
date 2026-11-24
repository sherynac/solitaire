package com.svi.solitaire.solver;

import com.svi.solitaire.enums.Suit;
import com.svi.solitaire.models.Card;

/**
 * @param source      column index, or Constants.NONE for the talon
 * @param destination column index, or Suit.ordinal() for a foundation
 */
public record Move(MoveType type, Card card, int source, int destination, int cardCount) {

    public boolean isTalonCycle() {
        return type.isTalonCycle();
    }

    @Override
    public String toString() {
        return switch (type) {
            case TALON_TO_FOUNDATION -> "Talon -> Foundation " + Suit.values()[destination].getSymbol();
            case TALON_TO_MANEUVER -> "Talon -> Column " + (destination + 1);
            case MANEUVER_TO_FOUNDATION ->
                    "Column " + (source + 1) + " -> Foundation " + Suit.values()[destination].getSymbol();
            case MANEUVER_TO_MANEUVER ->
                    "Column " + (source + 1) + " -> Column " + (destination + 1) + " (" + cardCount + " card(s)) ";
            case DRAW -> "Draw from stock";
            case RESTACK -> "Restack waste into stock";
        };
    }
}
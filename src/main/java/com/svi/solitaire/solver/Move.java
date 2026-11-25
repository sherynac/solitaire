package com.svi.solitaire.solver;

import com.svi.solitaire.enums.Suit;
import com.svi.solitaire.models.Card;

/**
 * Represents a move that can be performed during a Solitaire game.
 *
 * @param type        type of move to perform
 * @param card        card involved in the move, or {@code null} for talon cycle moves
 * @param source      source column index, or {@code Constants.NONE} for the talon
 * @param destination destination column index, or {@link Suit#ordinal()} for a foundation
 * @param cardCount   number of cards to move
 *
 * @author Sheryn Cagulada
 * @version 1.0
 */
public record Move(MoveType type, Card card, int source, int destination, int cardCount) {

    /**
     * Checks whether this move involves cycling the talon.
     *
     * @return true if the move draws from the stock or restacks the waste;
     *         false otherwise
     */
    public boolean isTalonCycle() {
        return type.isTalonCycle();
    }

    /**
     * Returns a human-readable description of this move.
     *
     * @return formatted description of the move
     */
    @Override
    public String toString() {
        return switch (type) {
            case TALON_TO_FOUNDATION -> "Talon -> Foundation " + Suit.values()[destination].getSymbol();
            case TALON_TO_MANEUVER -> "Talon -> Column " + (destination + 1);
            case MANEUVER_TO_FOUNDATION ->
                    "Column " + (source + 1) + " -> Foundation " + Suit.values()[destination].getSymbol();
            case MANEUVER_TO_MANEUVER ->
                    "Column " + (source + 1) + " -> Column " + (destination + 1) + " (" + cardCount + " card(s))";
            case DRAW -> "Draw from stock";
            case RESTACK -> "Restack waste into stock";
        };
    }
}
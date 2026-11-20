package com.svi.solitaire.solver;

import com.svi.solitaire.enums.MoveType;
import com.svi.solitaire.enums.Suit;

public class Move {
    private final MoveType type;
    private final int source;        // column index, or Constants.NONE for the talon
    private final int destination;   // column index, or Suit.ordinal() for a foundation
    private final int cardCount;
    private final int score;

    public Move(MoveType type, int source, int destination, int cardCount) {
        this.type = type;
        this.source = source;
        this.destination = destination;
        this.cardCount = cardCount;
        this.score = 0;
    }

    public MoveType getType() { return type; }
    public int getSource() { return source; }
    public int getDestination() { return destination; }
    public int getCardCount() { return cardCount; }
    public boolean isTalonCycle() { return type.isTalonCycle(); }

    @Override
    public String toString() {
        switch (type) {
            case TALON_TO_FOUNDATION:    return "Talon -> Foundation " + Suit.values()[destination].getSymbol();
            case TALON_TO_MANEUVER:      return "Talon -> Column " + destination;
            case MANEUVER_TO_FOUNDATION: return "Column " + (source + 1) + " -> Foundation " + Suit.values()[destination].getSymbol();
            case MANEUVER_TO_MANEUVER:   return "Column " + (source + 1) + " -> Column " + (destination + 1) + " (" + cardCount + " card(s)) ";
            case DRAW:                   return "Draw from stock";
            case RESTACK:                return "Restack waste into stock";
            default:                     return type.name();
        }
    }
}
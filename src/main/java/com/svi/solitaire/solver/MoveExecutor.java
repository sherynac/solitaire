package com.svi.solitaire.solver;

import com.svi.solitaire.models.Game;

public class MoveExecutor {

    public void execute(Move move, Game game) {
        switch (move.getType()) {
            case TALON_TO_FOUNDATION    -> game.moveWasteToFoundation();
            case TALON_TO_MANEUVER      -> game.moveWasteToColumn(move.getDestination());
            case MANEUVER_TO_FOUNDATION -> game.moveManeuverToFoundation(move.getSource());
            case MANEUVER_TO_MANEUVER   -> game.moveManeuverToManeuver(
                    move.getSource(), move.getDestination(), move.getCardCount());
            case DRAW                   -> game.drawTalon();
            case RESTACK                -> game.restackTalon();
            default -> throw new IllegalStateException("Unhandled move type: " + move.getType());
        }
    }
}
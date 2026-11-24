package com.svi.solitaire.solver;

import com.svi.solitaire.models.Game;

public class MoveExecutor {

    public void execute(Move move, Game game) {
        switch (move.type()) {
            case TALON_TO_FOUNDATION    -> game.moveWasteToFoundation();
            case TALON_TO_MANEUVER      -> game.moveWasteToColumn(move.destination());
            case MANEUVER_TO_FOUNDATION -> game.moveManeuverToFoundation(move.source());
            case MANEUVER_TO_MANEUVER   -> game.moveManeuverToManeuver(
                    move.source(), move.destination(), move.cardCount());
            case DRAW                   -> game.drawTalon();
            case RESTACK                -> game.restackTalon();
            default -> throw new IllegalStateException("Unhandled move type: " + move.type());
        }
    }
}
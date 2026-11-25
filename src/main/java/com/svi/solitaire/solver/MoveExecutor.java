package com.svi.solitaire.solver;

import com.svi.solitaire.models.Game;

/**
 * Executes generated moves by applying the corresponding state changes
 * to a Solitaire game.
 *
 * @author Sheryn Cagulada
 * @version 1.0
 */
public class MoveExecutor {

    /**
     * Executes the specified move on the given game.
     *
     * @param move the move to execute
     * @param game the game whose state will be modified
     * @throws IllegalStateException if the move type is not handled
     */
    public void execute(Move move, Game game) {
        switch (move.type()) {
            case TALON_TO_FOUNDATION -> game.moveWasteToFoundation();
            case TALON_TO_MANEUVER -> game.moveWasteToColumn(move.destination());
            case MANEUVER_TO_FOUNDATION -> game.moveManeuverToFoundation(move.source());
            case MANEUVER_TO_MANEUVER -> game.moveManeuverToManeuver(
                    move.source(), move.destination(), move.cardCount());
            case DRAW -> game.drawTalon();
            case RESTACK -> game.restackTalon();
            default -> throw new IllegalStateException("Unhandled move type: " + move.type());
        }
    }
}
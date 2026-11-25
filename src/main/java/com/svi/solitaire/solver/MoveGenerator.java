package com.svi.solitaire.solver;

import com.svi.solitaire.models.piles.Maneuver;
import com.svi.solitaire.models.piles.Talon;
import com.svi.solitaire.utils.Constants;
import com.svi.solitaire.models.Card;
import com.svi.solitaire.models.Game;
import com.svi.solitaire.models.piles.Foundation;
import com.svi.solitaire.models.piles.ManeuverPile;

import java.util.ArrayList;
import java.util.List;

/**
 * Generates all currently available moves based on the state of a Solitaire game.
 *
 * @author Sheryn Cagulada
 * @version 1.0
 */
public class MoveGenerator {

    /**
     * Generates all available moves for the current game state.
     *
     * @param game the current game state
     * @return a list of generated moves
     */
    public List<Move> generate(Game game) {
        List<Move> moves = new ArrayList<>();
        addTalonMoves(game, moves);
        addManeuverMoves(game, moves);
        addTalonCycleMove(game, moves);
        return moves;
    }

    /**
     * Generates all available moves from the talon's waste pile.
     *
     * @param game the current game state
     * @param moves the list to which generated moves are added
     */
    private void addTalonMoves(Game game, List<Move> moves) {
        generateTalonToFoundation(game, moves);
        generateTalonToManeuver(game, moves);
    }

    /**
     * Generates a move from the top of the waste pile to its corresponding
     * foundation if the card can be placed there.
     *
     * @param game the current game state
     * @param moves the list to which the generated move is added
     */
    private void generateTalonToFoundation(Game game, List<Move> moves) {
        Card card = game.getTalon().peekWaste();

        if (card == null) {
            return;
        }

        Foundation foundation = game.getFoundation(card.suit());

        if (foundation.canAccept(card)) {
            moves.add(new Move(
                    MoveType.TALON_TO_FOUNDATION,
                    card,
                    Constants.NONE,
                    card.suit().ordinal(),
                    1
            ));
        }
    }

    /**
     * Generates moves from the top of the waste pile to maneuver columns
     * that can accept the card.
     *
     * @param game the current game state
     * @param moves the list to which generated moves are added
     */
    private void generateTalonToManeuver(Game game, List<Move> moves) {
        Card card = game.getTalon().peekWaste();

        if (card == null) {
            return;
        }

        Maneuver maneuver = game.getManeuver();

        for (int i = 0; i < Constants.MANEUVER_COLUMNS; i++) {
            ManeuverPile destination = maneuver.getColumn(i);

            if (destination.canAccept(card)) {
                moves.add(new Move(
                        MoveType.TALON_TO_MANEUVER,
                        card,
                        -1,
                        i,
                        1
                ));
            }
        }
    }

    /**
     * Generates all available moves originating from maneuver columns.
     *
     * @param game the current game state
     * @param moves the list to which generated moves are added
     */
    private void addManeuverMoves(Game game, List<Move> moves) {
        Maneuver maneuver = game.getManeuver();

        for (int from = 0; from < Constants.MANEUVER_COLUMNS; from++) {

            ManeuverPile source = maneuver.getColumn(from);

            if (source.isEmpty()) {
                continue;
            }

            generateManeuverToFoundation(source, game, from, moves);
            generateManeuverToManeuver(source, from, moves, maneuver);
        }
    }

    /**
     * Generates a move from the topmost visible card of a maneuver column
     * to its corresponding foundation if the card can be placed there.
     *
     * @param source the source maneuver column
     * @param game the current game state
     * @param from the zero-based index of the source column
     * @param moves the list to which the generated move is added
     */
    private void generateManeuverToFoundation(
            ManeuverPile source,
            Game game,
            int from,
            List<Move> moves
    ) {
        Card topCard = source.peekTop();
        Foundation foundation = game.getFoundation(topCard.suit());

        if (foundation.canAccept(topCard)) {
            moves.add(new Move(
                    MoveType.MANEUVER_TO_FOUNDATION,
                    topCard,
                    from,
                    topCard.suit().ordinal(),
                    1
            ));
        }
    }

    /**
     * Generates moves of visible card sequences between maneuver columns.
     *
     * @param source the source maneuver column
     * @param from the zero-based index of the source column
     * @param moves the list to which generated moves are added
     * @param maneuver the maneuver area containing the destination columns
     */
    private void generateManeuverToManeuver(
            ManeuverPile source,
            int from,
            List<Move> moves,
            Maneuver maneuver
    ) {
        for (int count = 1; count <= source.visibleCount(); count++) {
            Card runBase = source.cardFromTop(count - 1);

            for (int to = 0; to < Constants.MANEUVER_COLUMNS; to++) {
                if (to == from) {
                    continue;
                }

                ManeuverPile destination = maneuver.getColumn(to);

                if (!destination.canAccept(runBase)) {
                    continue;
                }

                moves.add(new Move(
                        MoveType.MANEUVER_TO_MANEUVER,
                        runBase,
                        from,
                        to,
                        count
                ));
            }
        }
    }

    /**
     * Generates the next available talon cycling action.
     *
     * @param game the current game state
     * @param moves the list to which the generated move is added
     */
    private void addTalonCycleMove(Game game, List<Move> moves) {
        Talon talon = game.getTalon();

        if (talon.canDraw()) {
            moves.add(new Move(
                    MoveType.DRAW,
                    null,
                    Constants.NONE,
                    Constants.NONE,
                    0
            ));
        } else if (talon.canRestack()) {
            moves.add(new Move(
                    MoveType.RESTACK,
                    null,
                    Constants.NONE,
                    Constants.NONE,
                    0
            ));
        }
    }
}
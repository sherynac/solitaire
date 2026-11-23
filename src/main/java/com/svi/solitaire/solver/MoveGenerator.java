package com.svi.solitaire.solver;

import com.svi.solitaire.config.Constants;
import com.svi.solitaire.enums.MoveType;
import com.svi.solitaire.models.Card;
import com.svi.solitaire.models.Game;
import com.svi.solitaire.models.Maneuver;
import com.svi.solitaire.models.piles.Foundation;
import com.svi.solitaire.models.piles.ManeuverPile;
import com.svi.solitaire.models.Talon;

import java.util.ArrayList;
import java.util.List;

public class MoveGenerator {

    public List<Move> generate(Game game) {
        List<Move> moves = new ArrayList<>();
        addTalonMoves(game, moves);
        addManeuverMoves(game, moves);
        addTalonCycleMove(game, moves);
        return moves;
    }

    private void addTalonMoves(Game game, List<Move> moves) {
        generateTalonToFoundation(game, moves);
        generateTalonToManeuver(game, moves);
    }

    private void generateTalonToFoundation(Game game, List<Move> moves) {
        Card card = game.getTalon().peekWaste();

        if (card == null) {
            return;
        }

        Foundation foundation = game.getFoundation(card.suit());

        if (foundation.canAccept(card)) {
            moves.add(new Move(
                    MoveType.TALON_TO_FOUNDATION,
                    Constants.NONE,
                    card.suit().ordinal(),
                    1
            ));
        }
    }

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
                        -1,
                        i,
                        1
                ));
            }
        }
    }

    private void addManeuverMoves(Game game, List<Move> moves) {
        Maneuver maneuver = game.getManeuver();

        for (int from = 0; from < Constants.MANEUVER_COLUMNS; from++) {

            ManeuverPile source = maneuver.getColumn(from);

            if (source.isEmpty()) continue;

            generateManeuverToFoundation(source, game, from, moves);
            generateManeuverToManeuver(source, from, moves, maneuver);
        }
    }

    private void generateManeuverToFoundation(ManeuverPile source, Game game, int from, List<Move> moves) {
        Card top = source.peekTop();
        if (game.getFoundation(top.suit()).canAccept(top)) {
            moves.add(new Move(MoveType.MANEUVER_TO_FOUNDATION, from, top.suit().ordinal(), 1));
        }
    }

    private void generateManeuverToManeuver(ManeuverPile source, int from, List<Move> moves, Maneuver maneuver) {
        for (int count = 1; count <= source.visibleCount(); count++) {     // every face-up run
            Card runBase = source.cardFromTop(count - 1);
            for (int to = 0; to < Constants.MANEUVER_COLUMNS; to++) {
                if (to == from) continue;

                ManeuverPile destination = maneuver.getColumn(to);
                if (!destination.canAccept(runBase)) continue;

                moves.add(new Move(MoveType.MANEUVER_TO_MANEUVER, from, to, count));
            }
        }
    }

    private void addTalonCycleMove(Game game, List<Move> moves) {
        Talon talon = game.getTalon();
        if (talon.canDraw()) moves.add(new Move(MoveType.DRAW, Constants.NONE, Constants.NONE, 0));
        else if (talon.canRestack()) moves.add(new Move(MoveType.RESTACK, Constants.NONE, Constants.NONE, 0));
    }
}
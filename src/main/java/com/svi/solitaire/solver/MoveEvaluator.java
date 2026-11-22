package com.svi.solitaire.solver;

import com.svi.solitaire.Constants;
import com.svi.solitaire.enums.Suit;
import com.svi.solitaire.models.Card;
import com.svi.solitaire.models.Game;
import com.svi.solitaire.models.Maneuver;
import com.svi.solitaire.models.piles.ManeuverPile;

import java.util.List;

public class MoveEvaluator {
    // tiers
    private static final int SAFE_FOUNDATION = 1000;
    private static final int KING_TO_EMPTY_COLUMN = 500;
    private static final int COLUMN_TO_COLUMN = 300;
    private static final int TALON_TO_COLUMN = 200;
    private static final int UNSAFE_FOUNDATION_USEFUL = 150;
    private static final int UNSAFE_FOUNDATION = 20;
    private static final int DRAW_SCORE = 10;
    private static final int RESTACK_SCORE = 5;
    // bonuses
    private static final int REVEAL_BONUS = 30;        // plus 1 per hidden card (longer column = better)
    private static final int EMPTY_COLUMN_BONUS = 25;
    private static final int FOLLOW_UP_BONUS = 15;
    private static final int SAME_SUIT_BONUS = 10;

    public Move findBestMove(List<Move> moves, Game game) {
        Move best = null;
        int bestScore = 0;                              // moves scoring 0 are never chosen
        for (Move move : moves) {
            int score = evaluate(move, game);
            if (score > bestScore) {
                best = move;
                bestScore = score;
            }
            System.out.println(move + ": " + score);
        }
        return best;
    }

    public int evaluate(Move move, Game game) {
        switch (move.getType()) {
            case TALON_TO_FOUNDATION:
            case MANEUVER_TO_FOUNDATION: return toFoundationEvaluator(move, game);
            case TALON_TO_MANEUVER:      return talonToManeuverEvaluator(move, game);
            case MANEUVER_TO_MANEUVER:   return maneuverToManeuverEvaluator(move, game);
            case DRAW:                   return DRAW_SCORE;
            case RESTACK:                return RESTACK_SCORE;
            default: throw new IllegalStateException("Unhandled move type: " + move.getType());
        }
    }

    // ---------------- one evaluator per kind of move ----------------

    private int toFoundationEvaluator(Move move, Game game) {
        if (isSafeForFoundation(movedCard(move, game), game)) {
            return SAFE_FOUNDATION + columnBonus(move, game);
        }
        boolean useful = revealsHiddenCard(move, game) || emptiesColumn(move, game);
        return useful ? UNSAFE_FOUNDATION_USEFUL + columnBonus(move, game) : UNSAFE_FOUNDATION;
    }

    private int talonToManeuverEvaluator(Move move, Game game) {
        boolean followUp = hasFollowUp(move, game);

        if (isMoveToEmptyColumn(move, game)) {                       // a King from the waste
            return followUp ? KING_TO_EMPTY_COLUMN + FOLLOW_UP_BONUS : 0;   // not always worth it
        }
        int score = TALON_TO_COLUMN;
        if (followUp) score += FOLLOW_UP_BONUS;
        return score;
    }

    private int maneuverToManeuverEvaluator(Move move, Game game) {
        boolean reveals = revealsHiddenCard(move, game);
        boolean empties = emptiesColumn(move, game);
        if (!reveals && !empties) return 0;

        int score = isMoveToEmptyColumn(move, game) ? KING_TO_EMPTY_COLUMN : COLUMN_TO_COLUMN;
        score += columnBonus(move, game);
        if (hasFollowUp(move, game)) score += FOLLOW_UP_BONUS;
        return score;
    }

    private int columnBonus(Move move, Game game) {
        int bonus = 0;
        if (revealsHiddenCard(move, game)) bonus += REVEAL_BONUS + hiddenCardsUnder(move, game);
        if (emptiesColumn(move, game)) bonus += EMPTY_COLUMN_BONUS;
        return bonus;
    }

    // ---------------- yes/no questions about a move (read-only) ----------------

    private Card movedCard(Move move, Game game) {
        if (move.getType().isFromTalon()) return game.getTalon().peekWaste();
        return game.getManeuver().getColumn(move.getSource()).cardFromTop(move.getCardCount() - 1);
    }

    /** A and 2 are always safe. Otherwise both opposite-colour cards one rank lower must already be up. */
    private boolean isSafeForFoundation(Card card, Game game) {
        int rank = card.getRank().getValue();
        if (rank <= 2) return true;
        for (Suit suit : Suit.values()) {
            if (suit.isRed() != card.isRed() && game.getFoundation(suit).size() < rank - 1) {
                return false;
            }
        }
        return true;
    }

    private boolean revealsHiddenCard(Move move, Game game) {
        if (move.getSource() == Constants.NONE) return false;
        ManeuverPile source = game.getManeuver().getColumn(move.getSource());
        return source.hasHiddenCards() && move.getCardCount() == source.visibleCount();
    }

    private int hiddenCardsUnder(Move move, Game game) {
        if (move.getSource() == Constants.NONE) return 0;
        return game.getManeuver().getColumn(move.getSource()).hiddenCount();
    }

    private boolean emptiesColumn(Move move, Game game) {
        if (move.getSource() == Constants.NONE) return false;
        ManeuverPile source = game.getManeuver().getColumn(move.getSource());
        return source.size() == move.getCardCount() && !isMoveToEmptyColumn(move, game);
    }

    private boolean isMoveToEmptyColumn(Move move, Game game) {
        return move.getType().isToManeuver() && game.getManeuver().getColumn(move.getDestination()).isEmpty();
    }

    private boolean hasFollowUp(Move move, Game game) {
        Card moved = movedCard(move, game);

        if (!move.getType().isFromTalon()) {
            Card wasteTop = game.getTalon().peekWaste();
            if (wasteTop != null && wasteTop.isOneLowerThan(moved)) return true;
        }
        for (int col = 0; col < Constants.MANEUVER_COLUMNS; col++) {
            boolean isSource = col == move.getSource();
            boolean isDestination = move.getType().isToManeuver() && col == move.getDestination();
            if (isSource || isDestination) continue;

            Card runBase = game.getManeuver().getColumn(col).peekTop();
            if (runBase != null && runBase.isOneLowerThan(moved)) return true;
        }
        return false;
    }
}
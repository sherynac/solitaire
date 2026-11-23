package com.svi.solitaire.solver;

import com.svi.solitaire.utils.Constants;
import com.svi.solitaire.enums.Suit;
import com.svi.solitaire.models.Card;
import com.svi.solitaire.models.Game;
import com.svi.solitaire.models.piles.ManeuverPile;

import java.util.List;

/**
 * Scores every legal move and picks the best one. Read-only: it never changes the game.
 *
 * Priority order (highest first):
 *   1. Safe foundation move (A, 2, or a card no tableau card still needs)
 *   2. Any column move that flips a hidden card (deeper columns first)
 *   3. Any column move that empties a column
 *   4. Talon to column (a king only if something can follow it)
 *   5. Talon to foundation (unsafe)
 *   6. Column to foundation that helps nothing else
 *   7. Draw
 *   8. Restack
 *
 * A score of 0 means "never play this move". Tier gaps are far larger than any bonus,
 * so a bonus can never lift a move into a higher tier.
 */
public class MoveEvaluator {
    /** Returns the highest-scoring move, or null when nothing scores above 0. */
    public Move findBestMove(List<Move> moves, Game game) {
        Move best = null;
        int bestScore = 0;                               // moves scoring 0 are never chosen
        for (Move move : moves) {
            int score = evaluate(move, game);
            if (score > bestScore) {                     // on a tie, the first generated move wins
                best = move;
                bestScore = score;
            }
        }
        return best;
    }

    public int evaluate(Move move, Game game) {
        switch (move.getType()) {
            case TALON_TO_FOUNDATION:
            case MANEUVER_TO_FOUNDATION: return toFoundationEvaluator(move, game);
            case TALON_TO_MANEUVER:      return talonToManeuverEvaluator(move, game);
            case MANEUVER_TO_MANEUVER:   return maneuverToManeuverEvaluator(move, game);
            case DRAW:                   return Constants.DRAW_SCORE;
            case RESTACK:                return Constants.RESTACK_SCORE;
            default: throw new IllegalStateException("Unhandled move type: " + move.getType());
        }
    }

    // ---------------- one evaluator per kind of move ----------------

    private int toFoundationEvaluator(Move move, Game game) {
        Card card = movedCard(move, game);
        if (card == null) return 0;

        // 1. A, 2, or any card nothing in the tableau still needs
        if (isSafeForFoundation(card, game)) {
            return Constants.SAFE_FOUNDATION + hiddenCardsUnder(move, game);   // tie-break: dig the deepest column
        }
        // 5. unsafe card from the waste: allowed, but low priority
        if (move.getType().isFromTalon()) {
            return Constants.TALON_TO_FOUNDATION;
        }
        // 2 and 3. unsafe card from a column, but it flips a card or empties the column
        if (revealsHiddenCard(move, game)) {
            return Constants.REVEAL + hiddenCardsUnder(move, game);
        }
        if (emptiesColumn(move, game)) {
            return Constants.EMPTY_COLUMN;
        }
        // 6. nothing else to gain
        return Constants.USELESS_FOUNDATION;
    }

    private int talonToManeuverEvaluator(Move move, Game game) {
        boolean followUp = hasFollowUp(move, game);

        // a king from the waste only goes to an empty column if something can follow it
        if (isMoveToEmptyColumn(move, game) && !followUp) {
            return 0;
        }
        return Constants.TALON_TO_COLUMN + (followUp ? Constants.FOLLOW_UP_BONUS : 0);
    }

    private int maneuverToManeuverEvaluator(Move move, Game game) {
        boolean reveals = revealsHiddenCard(move, game);
        boolean empties = emptiesColumn(move, game);

        if (!reveals && !empties) return 0;              // never shuffle cards for nothing

        int score = reveals ? Constants.REVEAL + hiddenCardsUnder(move, game) : Constants.EMPTY_COLUMN;
        if (hasFollowUp(move, game)) score += Constants.FOLLOW_UP_BONUS;


        return score;
    }

    // ---------------- yes/no questions about a move (read-only) ----------------

    /** The card that must fit on the destination: the waste card, or the BASE of a moved run. */
    private Card movedCard(Move move, Game game) {
        if (move.getType().isFromTalon()) return game.getTalon().peekWaste();
        return game.getManeuver().getColumn(move.getSource()).cardFromTop(move.getCardCount() - 1);
    }

    /** The card left exposed at the destination after the move: the TOP of the moved cards. */
    private Card exposedCardAfterMove(Move move, Game game) {
        if (move.getType().isFromTalon()) return game.getTalon().peekWaste();
        return game.getManeuver().getColumn(move.getSource()).peekTop();
    }

    /** A and 2 are always safe. Otherwise both opposite-colour foundations must already be at rank - 1. */
    private boolean isSafeForFoundation(Card card, Game game) {
        int rank = card.rank().getValue();

        if (rank <= 2) { // automatic A & 2
            return true;
        }

        for (Suit suit : Suit.values()) {
            if (suit.isRed() != card.isRed() && game.getFoundation(suit).size() < rank - 1) {
                return false;
            }
        }

        return true;
    }

    /** True if the move takes the whole visible run, so a hidden card flips. */
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
        if (move.getType().isFromTalon()) return false;
        ManeuverPile source = game.getManeuver().getColumn(move.getSource());
        return source.size() == move.getCardCount() && !isMoveToEmptyColumn(move, game);
    }

    private boolean isMoveToEmptyColumn(Move move, Game game) {
        return move.getType().isToManeuver()
                && game.getManeuver().getColumn(move.getDestination()).isEmpty();
    }

    /**
     * True if, after the move, some column's whole visible run could be placed on the card
     * the move leaves exposed (so a king placed in an empty column has something to follow it).
     */
    private boolean hasFollowUp(Move move, Game game) {
        Card exposed = exposedCardAfterMove(move, game);
        if (exposed == null) return false;

        if (!move.getType().isFromTalon()) {
            Card wasteTop = game.getTalon().peekWaste();
            if (wasteTop != null && wasteTop.isOneLowerThan(exposed)) return true;
        }
        for (int col = 0; col < Constants.MANEUVER_COLUMNS; col++) {
            boolean isSource = col == move.getSource();
            boolean isDestination = move.getType().isToManeuver() && col == move.getDestination();
            if (isSource || isDestination) continue;

            // The card that would move is the BASE of the column's whole visible run
            // (for a single visible card, base and top are the same card).
            ManeuverPile column = game.getManeuver().getColumn(col);
            Card runBase = column.cardFromTop(column.visibleCount() - 1);   // null if no visible cards
            if (runBase != null && runBase.isOneLowerThan(exposed)) return true;
        }

        // Talon cards the player has already SEEN (waste below the top, and stock cards flipped
        // in an earlier pass). Unseen stock cards are never included, so this is not cheating.
        for (Card known : game.getTalon().knownCardsBelowTop()) {
            if (known.isOneLowerThan(exposed)) return true;
        }
        return false;
    }
}

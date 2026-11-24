package com.svi.solitaire.solver;

import com.svi.solitaire.models.piles.Foundation;
import com.svi.solitaire.utils.Constants;
import com.svi.solitaire.enums.Suit;
import com.svi.solitaire.models.Card;
import com.svi.solitaire.models.Game;
import com.svi.solitaire.models.piles.ManeuverPile;

import java.util.List;

/**
 * Scores every legal move and picks the best one. Read-only: it never changes the game.
 * <p>
 * Priority order (highest first):
 * 1. Safe foundation move (A, 2, or a card no tableau card still needs)
 * 2. Any column move that flips a hidden card (deeper columns first)
 * 3. Any column move that empties a column
 * 4. Talon to column (a king only if something can follow it)
 * 5. Talon to foundation (unsafe)
 * 6. Column to foundation that helps nothing else
 * 7. Draw
 * 8. Restack
 * <p>
 * A score of 0 means "never play this move". Tier gaps are far larger than any bonus,
 * so a bonus can never lift a move into a higher tier.
 */
public class MoveEvaluator {

    /**
     * Returns the highest-scoring move, or null when nothing scores above 0.
     */
    public Move findBestMove(List<Move> moves, Game game) {
        Move best = null;
        int bestScore = 0;
        for (Move move : moves) {
            int score = evaluate(move, game);
            if (score > bestScore) {
                best = move;
                bestScore = score;
            }
        }
        return best;
    }

    public int evaluate(Move move, Game game) {
        return switch (move.type()) {
            case TALON_TO_FOUNDATION, MANEUVER_TO_FOUNDATION -> 
                    evaluateToFoundation(move, game);
            case TALON_TO_MANEUVER -> 
                    evaluateTalonToManeuver(move, game);
            case MANEUVER_TO_MANEUVER -> 
                    evaluateManeuverToManeuver(move, game);
            
            case DRAW -> Constants.DRAW_SCORE;
            case RESTACK -> Constants.RESTACK_SCORE;
        };
    }

    private int evaluateToFoundation(Move move, Game game) {
        Card card = move.card();
        if (card == null) {
            return 0;
        }

        if (isSafeForFoundation(card, game)) {
            return Constants.SAFE_FOUNDATION + hiddenCardsUnder(move, game);   // tie-break: dig the deepest column
        }

        if (move.type().isFromTalon()) {
            return Constants.TALON_TO_FOUNDATION;
        }

        if (revealsHiddenCard(move, game)) {
            return Constants.REVEAL + hiddenCardsUnder(move, game);
        }

        if (emptiesColumn(move, game)) {
            return Constants.EMPTY_COLUMN;
        }

        return Constants.USELESS_FOUNDATION;
    }

    private int evaluateTalonToManeuver(Move move, Game game) {
        boolean followUp = hasFollowUp(move, game);

        // a king from the waste only goes to an empty column if something can follow it
        if (isMoveToEmptyColumn(move, game) && !followUp) {
            return 0;
        }

        return Constants.TALON_TO_COLUMN + (followUp ? Constants.FOLLOW_UP_BONUS : 0);
    }

    private int evaluateManeuverToManeuver(Move move, Game game) {
        boolean reveals = revealsHiddenCard(move, game);
        boolean empties = emptiesColumn(move, game);

        if (!reveals && !empties) {
            return 0;              // never shuffle cards for nothing
        }

        int score = reveals ? Constants.REVEAL + hiddenCardsUnder(move, game) : Constants.EMPTY_COLUMN;

        if (hasFollowUp(move, game)) {
            score += Constants.FOLLOW_UP_BONUS;
        }

        return score;
    }

    /**
     * The card left exposed at the destination after the move: the TOP of the moved cards.
     */
    private Card getMoveSequenceTopCard(Move move, Game game) {
        if (move.type().isFromTalon()) {
            return game.getTalon().peekWaste();
        }

        ManeuverPile maneuverPile = game.getManeuver().getColumn(move.source());
        return maneuverPile.peekTop();
    }

    /**
     * A and 2 are always safe. Otherwise both opposite-colour foundations must already be at rank - 1.
     */
    private boolean isSafeForFoundation(Card card, Game game) {
        int rank = card.rank().getValue();

        if (rank <= 2) { // automatic A & 2
            return true;
        }

        for (Suit suit : Suit.values()) {
            Foundation foundationPile = game.getFoundation(suit);

            if (suit.isRed() != card.isRed() && foundationPile.size() < rank - 1) {
                return false;
            }
        }

        return true;
    }

    /**
     * True if the move takes the whole visible run, so a hidden card flips.
     */
    private boolean revealsHiddenCard(Move move, Game game) {
        if (move.source() == Constants.NONE) {
            return false;
        }

        ManeuverPile sourcePile = game.getManeuver().getColumn(move.source());

        return sourcePile.hasHiddenCards()
                && move.cardCount() == sourcePile.visibleCount(); // when visible card is moved, hidden is flipped
    }

    private int hiddenCardsUnder(Move move, Game game) {
        if (move.source() == Constants.NONE) {
            return 0;
        }

        ManeuverPile sourcePile = game.getManeuver().getColumn(move.source());

        return sourcePile.hiddenCount();
    }

    private boolean emptiesColumn(Move move, Game game) {
        if (move.type().isFromTalon()) {
            return false;
        }

        ManeuverPile sourcePile = game.getManeuver().getColumn(move.source());

        return sourcePile.size() == move.cardCount()
                && !isMoveToEmptyColumn(move, game);
    }

    private boolean isMoveToEmptyColumn(Move move, Game game) {
        ManeuverPile destinationPile = game.getManeuver().getColumn(move.destination());

        return move.type().isToManeuver() && destinationPile.isEmpty();
    }

    /**
     * True if, after the move, some column's whole visible run could be placed on the card
     * the move leaves exposed (so a king placed in an empty column has something to follow it).
     */
    private boolean hasFollowUp(Move move, Game game) {
        Card exposed = getMoveSequenceTopCard(move, game);

        if (exposed == null) {
            return false;
        }

        if (!move.type().isFromTalon()) {
            Card wasteTop = game.getTalon().peekWaste();
            if (wasteTop != null && wasteTop.isOneLowerThan(exposed))  {
                return true;
            }
        }

        for (int col = 0; col < Constants.MANEUVER_COLUMNS; col++) {
            boolean isSource = col == move.source();
            boolean isDestination = move.type().isToManeuver() && col == move.destination();
            if (isSource || isDestination) {
                continue;
            }

            ManeuverPile column = game.getManeuver().getColumn(col);
            Card topCard = column.cardFromTop(column.visibleCount() - 1);   // null if no visible cards

            if (topCard != null && topCard.isOneLowerThan(exposed)) {
                return true;
            }
        }

        for (Card known : game.getTalon().knownCardsBelowTop()) {
            if (known.isOneLowerThan(exposed)) return true;
        }

        return false;
    }
}

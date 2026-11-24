package com.svi.solitaire.solver;

import com.svi.solitaire.models.piles.Foundation;
import com.svi.solitaire.utils.Constants;
import com.svi.solitaire.enums.Suit;
import com.svi.solitaire.models.Card;
import com.svi.solitaire.models.Game;
import com.svi.solitaire.models.piles.ManeuverPile;

import java.util.List;

/**
 * Scores every legal move and return the best one based on priority order
 *
 * @author Sheryn Cagulada
 * @version 1.0
 */
public class MoveEvaluator {

    /**
     * Returns the highest-scoring move, or null when nothing scores above 0.
     *
     * @param moves list of legal moves
     * @param game current game state
     * @return best move
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

    /**
     * Score a move based on move type and current game state
     *
     * @param move to be evaluated move
     * @param game current game state
     * @return evaluation score of a move
     */
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

    /**
     * Score moves that places a card to foundation
     *
     * @param move to be evaluated move
     * @param game current game state
     * @return evaluation score of a move
     */
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

    /**
     * Score moves from talon to maneuver
     *
     * @param move to be evaluated move
     * @param game current game state
     * @return evaluation score of a move
     */
    private int evaluateTalonToManeuver(Move move, Game game) {
        boolean followUp = hasFollowUp(move, game);

        // a king from the waste only goes to an empty column if something can follow it
        if (isMoveToEmptyColumn(move, game) && !followUp) {
            return 0;
        }

        return Constants.TALON_TO_COLUMN + (followUp ? Constants.FOLLOW_UP_BONUS : 0);
    }

    /**
     * Score moves between maneuver piles
     *
     * @param move to be evaluated move
     * @param game current game state
     * @return evaluation score of a move
     */
    private int evaluateManeuverToManeuver(Move move, Game game) {
        boolean reveals = revealsHiddenCard(move, game);
        boolean empties = emptiesColumn(move, game);

        if (!reveals && !empties) {
            return 0;
        }

        int score = reveals ? Constants.REVEAL + hiddenCardsUnder(move, game) : Constants.EMPTY_COLUMN;

        if (hasFollowUp(move, game)) {
            score += Constants.FOLLOW_UP_BONUS;
        }

        return score;
    }

    /**
     * Retrieves the top card of the sequence being moved.
     *
     * @param move to be evaluated move
     * @param game current game state
     * @return top card of the sequence being moved
     */
    private Card getMoveSequenceTopCard(Move move, Game game) {
        if (move.type().isFromTalon()) {
            return game.getTalon().peekWaste();
        }

        ManeuverPile maneuverPile = game.getManeuver().getColumn(move.source());
        return maneuverPile.peekTop();
    }

    /**
     * Checks if card can still be used to build sequences in the maneuver
     *
     * @param card the card to be placed in the foundation
     * @param game current game state
     * @return true if no sequences can be built; false otherwise
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
     * Checks if the move will flip a hidden card
     *
     * @param move to be evaluated move
     * @param game current game state
     * @return true if card will be revealed; false otherwise
     */
    private boolean revealsHiddenCard(Move move, Game game) {
        if (move.source() == Constants.NONE) {
            return false;
        }

        ManeuverPile sourcePile = game.getManeuver().getColumn(move.source());

        return sourcePile.hasHiddenCards()
                && move.cardCount() == sourcePile.visibleCount(); // when visible card is moved, hidden is flipped
    }

    /**
     * Checks how much hidden cards are in a maneuver pile
     *
     * @param move to be evaluated move
     * @param game current game state
     * @return number of hidden cards in the pile
     */
    private int hiddenCardsUnder(Move move, Game game) {
        if (move.source() == Constants.NONE) {
            return 0;
        }

        ManeuverPile sourcePile = game.getManeuver().getColumn(move.source());

        return sourcePile.hiddenCount();
    }

    /**
     * Check if move will empty a column
     *
     * @param move to be evaluated move
     * @param game current game state
     * @return true if column is emptied; false otherwise
     */
    private boolean emptiesColumn(Move move, Game game) {
        if (move.type().isFromTalon()) {
            return false;
        }

        ManeuverPile sourcePile = game.getManeuver().getColumn(move.source());

        return sourcePile.size() == move.cardCount()
                && !isMoveToEmptyColumn(move, game);
    }

    /**
     * Checks if a move is towards an empty column
     *
     * @param move to be evaluated move
     * @param game current game state
     * @return true if move is to empty column; false otherwise
     */
    private boolean isMoveToEmptyColumn(Move move, Game game) {
        ManeuverPile destinationPile = game.getManeuver().getColumn(move.destination());

        return move.type().isToManeuver() && destinationPile.isEmpty();
    }

    /**
     * Checks if the move would lead to more possible moves to reveal cards
     *
     * @param move to be evaluated move
     * @param game current game state
     * @return true if there is a follow-up move; false otherwise
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
            if (known.isOneLowerThan(exposed)) {
                return true;
            }
        }

        return false;
    }
}

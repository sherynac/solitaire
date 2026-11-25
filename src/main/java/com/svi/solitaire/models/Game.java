package com.svi.solitaire.models;

import com.svi.solitaire.models.piles.Maneuver;
import com.svi.solitaire.models.piles.Talon;
import com.svi.solitaire.utils.Constants;
import com.svi.solitaire.enums.Suit;
import com.svi.solitaire.models.piles.Foundation;
import com.svi.solitaire.models.piles.ManeuverPile;

import java.util.Map;

/**
 * Game representation containing the movement between piles and checking for winning conditions
 *
 * @author Sheryn Cagulada
 * @version 1.0
 */
public class Game {
    private final Maneuver maneuver;
    private final Talon talon;
    private final Map<Suit, Foundation> foundations;

    public Game(Talon talon, Map<Suit, Foundation> foundations, Maneuver maneuver) {
        this.talon = talon;
        this.foundations = foundations;
        this.maneuver = maneuver;
    }

    /**
     * Retrieves a copy of the current maneuver
     *
     * @return copy of the maneuver
     */
    public Maneuver getManeuver() {
        return maneuver.copy();
    }

    /**
     * Retrieves a copy of the current talon
     *
     * @return copy of the talon
     */
    public Talon getTalon() {
        return talon.copy();
    }

    /**
     * Retrieves the copy of a foundation
     *
     * @param suit the suit of the foundation
     * @return the copy of the corresponding foundation
     */
    public Foundation getFoundation(Suit suit) {
        return foundations.get(suit).copy();
    }

    /**
     * Move card from top of waste to foundation
     */
    public void moveWasteToFoundation() {
        Card card = talon.peekWaste();
        if (card == null) {
            throw new IllegalArgumentException("The waste is empty. ");
        }

        foundations.get(card.suit()).add(card);
        talon.removeFromWaste();
    }

    /**
     * Move card from top of waste to maneuver column
     *
     * @param column the index of the maneuver column
     */
    public void moveWasteToColumn(int column) {
        Card card = talon.peekWaste();
        if (card == null) {
            throw new IllegalStateException("The waste is empty.");
        }

        maneuver.getColumn(column).add(card);
        talon.removeFromWaste();
    }

    /**
     * Move card from maneuver to foundation
     *
     * @param column the index of the maneuver column
     */
    public void moveManeuverToFoundation(int column) {
        ManeuverPile source = maneuver.getColumn(column);
        Card card = source.peekTop();

        if (card == null) {
            throw new IllegalStateException("Column " + column + " is empty.");
        }

        foundations.get(card.suit()).add(card);
        source.removeTop();

        if (source.hasHiddenCards() && source.hasNoVisibleCards()) {
            source.flipTop();
        }
    }

    /**
     * Moves a run of cards between maneuver columns
     *
     * @param source the index of where the run of cards came from
     * @param destination the index of where the run of cards is going to
     * @param count the number of cards to be moves from the top
     */
    public void moveManeuverToManeuver(int source, int destination, int count) {
        maneuver.moveCards(source, destination, count);
    }

    /**
     * Draw from the talon
     */
    public void drawTalon() {
        talon.drawCards();
    }

    /**
     * Restack the talon
     */
    public void restackTalon() {
        talon.restack();
    }

    /**
     * Check if all foundations have been completed
     *
     * @return true if all foundations have finished sequences; false otherwise
     */
    public boolean isGameOver() {
        for (Map.Entry<Suit, Foundation> foundation : foundations.entrySet()) {
            if (foundation.getValue().size() != Constants.CARD_PER_SUIT) {
                return false;
            }
        }
        return true;
    }

    /**
     * Create string representation of current game state
     *
     * @return string representation of current game state
     */
    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append(String.format("%n%-13s", "Stock"));
        stringBuilder.append(String.format("%-20s", "Waste"));
        stringBuilder.append("Foundation");

        stringBuilder.append(String.format("%n%-13s", talon.stockString()));
        stringBuilder.append(String.format("%-20s", talon.wasteString()));

        for (Map.Entry<Suit, Foundation> entry : foundations.entrySet()) {
            stringBuilder.append(entry.getValue());
        }

        stringBuilder.append("\n\n---------------------------------------------------------\n");
        stringBuilder.append(maneuver);

        return stringBuilder.toString();
    }
}

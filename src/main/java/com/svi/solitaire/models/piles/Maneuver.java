package com.svi.solitaire.models.piles;

import com.svi.solitaire.models.Card;
import com.svi.solitaire.solver.Move;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Represents the maneuver area of a Solitaire game, consisting of seven columns
 * where cards are arranged and moved according to the game rules.
 *
 * @author Sheryn Cagulada
 * @version 1.0
 */
public class Maneuver {

    private final List<ManeuverPile> columnList;

    /**
     * Creates a maneuver area containing seven empty columns.
     */
    public Maneuver() {
        columnList = new ArrayList<>();

        for (int i = 0; i < 7; i++) {
            ManeuverPile tableauColumn = new ManeuverPile();
            columnList.add(tableauColumn);
        }
    }

    /**
     * Creates a maneuver area using copies of the provided columns.
     *
     * @param columnList the columns used to initialize the maneuver area
     */
    public Maneuver(List<ManeuverPile> columnList) {
        this.columnList = new ArrayList<>();

        for (ManeuverPile col : columnList) {
            this.columnList.add(col.copy());
        }
    }

    /**
     * Creates an independent copy of the maneuver area.
     *
     * @return a new maneuver containing copies of the current columns
     */
    public Maneuver copy() {
        return new Maneuver(columnList);
    }

    /**
     * Returns the column at the specified index.
     *
     * @param index the zero-based index of the column
     * @return the column at the specified index
     * @throws IndexOutOfBoundsException if the index is out of range
     */
    public ManeuverPile getColumn(int index) {
        return columnList.get(index);
    }

    /**
     * Adds a card face down to the specified column.
     *
     * @param columnIndex the zero-based index of the destination column
     * @param card the card to add
     */
    public void addCard(int columnIndex, Card card) {
        columnList.get(columnIndex).addHidden(card);
    }

    /**
     * Adds a card face up to the specified column.
     *
     * @param columnIndex the zero-based index of the destination column
     * @param card the card to add
     */
    public void addVisibleCard(int columnIndex, Card card) {
        columnList.get(columnIndex).add(card);
    }

    /**
     * Moves the specified number of cards from one column to another.
     *
     * @param source the zero-based index of the source column
     * @param destination the zero-based index of the destination column
     * @param cardCount the number of cards to move
     */
    public void moveCards(int source, int destination, int cardCount) {
        ManeuverPile sourcePile = getColumn(source);
        ManeuverPile destPile = getColumn(destination);

        List<Card> cardsToMove = new ArrayList<>();

        for (int counter = 0; counter < cardCount; counter++) {
            cardsToMove.add(sourcePile.removeTop());
        }

        Collections.reverse(cardsToMove);

        destPile.add(cardsToMove);

        if (sourcePile.hasNoVisibleCards() && !sourcePile.isEmpty()) {
            sourcePile.flipTop();
        }
    }

    /**
     * Returns a formatted representation of the maneuver area.
     *
     * @return the formatted representation of the maneuver area
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < columnList.size(); i++) {
            sb.append(String.format("%-8s", "[" + (i + 1) + "]"));
        }

        sb.append("\n\n");

        int maxHeight = 0;

        for (ManeuverPile col : columnList) {
            maxHeight = Math.max(maxHeight, col.size());
        }

        for (int row = 0; row < maxHeight; row++) {
            for (ManeuverPile col : columnList) {
                if (row < col.size()) {
                    sb.append(String.format("%-8s", col.getDisplayCardAt(row)));
                } else {
                    sb.append(String.format("%-8s", ""));
                }
            }

            sb.append("\n");
        }

        return sb.toString();
    }
}
package com.svi.solitaire.models.piles;

import com.svi.solitaire.models.Card;
import com.svi.solitaire.solver.Move;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Maneuver {

    private final List<ManeuverPile> columnList;

    public Maneuver(){
        columnList = new ArrayList<>();

        for (int i = 0 ; i < 7; i++){
            ManeuverPile tableauColumn = new ManeuverPile();
            columnList.add(tableauColumn);
        }
    }

    public Maneuver(List<ManeuverPile> columnList) {
        this.columnList = new ArrayList<>();

        for (ManeuverPile col : columnList) {
            this.columnList.add(col.copy());
        }
    }

    public ManeuverPile getColumn(int index) {
        return columnList.get(index);
    }

    public ManeuverPile getSourceColumn(Move move) {
        return columnList.get(move.getSource());
    }

    public void addCard(int columnIndex, Card card) {
        columnList.get(columnIndex).addHidden(card);
    }

    public void addVisibleCard(int columnIndex, Card card) {
        columnList.get(columnIndex).add(card);
    }

    public void moveCards(int from, int to, int cardCount) {
        ManeuverPile fromPile = getColumn(from);
        ManeuverPile toPile = getColumn(to);

        List<Card> cardsToMove = new ArrayList<>();

        for (int counter = 0; counter < cardCount; counter++) {
            cardsToMove.add(fromPile.removeTop());   // top first
        }

        Collections.reverse(cardsToMove);            // now bottom to top: C-9, D-8, S-7

        toPile.add(cardsToMove);

        if (fromPile.hasNoVisibleCards() && !fromPile.isEmpty()) {
            fromPile.flipTop();
        }
    }

    public Maneuver copy() {
        return new Maneuver(columnList);
    }

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


package com.svi.solitaire.models;

import com.svi.solitaire.models.piles.ManeuverPile;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

public class Maneuver {

    private List<ManeuverPile> columnList;

    public Maneuver(){
        columnList = new ArrayList<>();

        for (int i = 0 ; i < 7; i++){
            ManeuverPile tableauColumn = new ManeuverPile();
            columnList.add(tableauColumn);
        }
    }

    public Maneuver(List<ManeuverPile> columns){
        this.columnList = columns;
    }

    public ManeuverPile getColumn(int index) {
        return columnList.get(index);
    }

    public void addCard(int columnIndex, Card card) {
        columnList.get(columnIndex).addLast(card);
    }

    public void addVisibleCard(int columnIndex, Card card) {
        columnList.get(columnIndex).addFlippedCard(card);
    }

    public void moveCards(int from, int to, int cardCount) {
        ManeuverPile fromPile = getColumn(from);
        ManeuverPile toPile = getColumn(to);

        Deque<Card> cardsToMove = new ArrayDeque<>();

        for (int counter = 0; counter < cardCount; counter++) {
            Card card = fromPile.peekLast();
            cardsToMove.addFirst(card);
            fromPile.removeLast();
        }

        toPile.addFlippedCards(cardsToMove);

        if (fromPile.hasNoVisibleCards()) {
            fromPile.flipTop();
        }
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

package com.svi.solitaire.models;

import com.svi.solitaire.models.piles.ManeuverPile;

import java.util.ArrayList;
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

    public void addCard(int columnIndex, Card card) {
        columnList.get(columnIndex).addLast(card);
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
                    sb.append(String.format("%-8s", col.getCardAt(row)));
                } else {
                    sb.append(String.format("%-8s", ""));
                }
            }

            sb.append("\n");
        }

        return sb.toString();
    }
}

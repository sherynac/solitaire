package com.svi.solitaire.models;

import com.svi.solitaire.models.piles.ManeuverColumn;

import java.util.ArrayList;
import java.util.List;

public class Maneuver {

    private List<ManeuverColumn> columnList;

    public Maneuver(){
        columnList = new ArrayList<>();

        for (int i = 0 ; i < 7; i++){
            ManeuverColumn tableauColumn = new ManeuverColumn();
            columnList.add(tableauColumn);
        }
    }

    public Maneuver(List<ManeuverColumn> columns){
        this.columnList = columns;
    }

    public List<ManeuverColumn> getColumnList() {
        return columnList;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        // Display column headers
        for (int i = 0; i < columnList.size(); i++) {
            sb.append(String.format("%-8s", "[" + (i + 1) + "]"));
        }

        sb.append("\n\n");

        // Find the maximum number of cards
        int maxHeight = 0;

        for (ManeuverColumn col : columnList) {
            maxHeight = Math.max(maxHeight, col.getCardCount());
        }

        // Display cards row by row
        for (int row = 0; row < maxHeight; row++) {
            for (ManeuverColumn col : columnList) {
                if (row < col.getCardCount()) {
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

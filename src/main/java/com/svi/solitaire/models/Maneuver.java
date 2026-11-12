package com.svi.solitaire.models;

import java.util.ArrayList;
import java.util.List;

public class Tableau {

    private List<ManueverColumn> columnList;

    public Tableau (){
        columnList = new ArrayList<>();

        for (int i = 0 ; i < 7; i++){
            ManueverColumn tableauColumn = new ManueverColumn();
            columnList.add(tableauColumn);
        }
    }

    public Tableau(List<ManueverColumn> columns){
        this.columnList = columns;
    }

    public List<ManueverColumn> getColumnList() {
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

        for (ManueverColumn col : columnList) {
            maxHeight = Math.max(maxHeight, col.getCardCount());
        }

        // Display cards row by row
        for (int row = 0; row < maxHeight; row++) {
            for (ManueverColumn col : columnList) {
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

package com.svi.solitaire.models;

import java.util.ArrayList;
import java.util.List;

public class Tableau {

    private List<TableauColumn> columnList;

    public Tableau (){
        columnList = new ArrayList<>();

        for (int i = 0 ; i < 7; i++){
            TableauColumn tableauColumn = new TableauColumn();
            columnList.add(tableauColumn);
        }
    }

    public Tableau(List<TableauColumn> columns){
        this.columnList = columns;
    }

    public List<TableauColumn> getColumnList() {
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

        for (TableauColumn col : columnList) {
            maxHeight = Math.max(maxHeight, col.getCardCount());
        }

        // Display cards row by row
        for (int row = 0; row < maxHeight; row++) {
            for (TableauColumn col : columnList) {
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

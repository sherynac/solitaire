package com.svi.solitaire.models;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Talon {
    int drawCount;
    List<Card> stock;
    List<Card> waste;

    public Talon(int drawCount, List<Card> stock){
        this.drawCount = drawCount;
        this.stock = stock;
        waste = new ArrayList<>();
    }

    /**
     * TODO: can reuse drawing method for cards
     */
    public void drawCards(){
        hideWaste();

        for (int i = 0; i < drawCount; i++) {
            Card drawnCard = stock.removeFirst(); // D-A,S-A,D-5,C-2 => D-A
            drawnCard.setVisible();
            waste.add(drawnCard); // D-A
        }

    }

    public void hideWaste(){
        waste.forEach((Card::setInvisible));
    }

    public void returnToStock(){
        waste = stock;
    }

    public String wasteString() {
        StringBuilder stringBuilder = new StringBuilder();
        if (waste.isEmpty()){
            stringBuilder.append("[]");
        } else {
            stringBuilder.append("[");
            stringBuilder.append(waste.stream()
                    .filter(Card::getVisibility)
                    .map(Card::toString)
                    .collect(Collectors.joining(", ")));
            stringBuilder.append("]");
        }
        return stringBuilder.toString();

    }

    public String stockString() {
        return "[???] x" + stock.size();
    }
}

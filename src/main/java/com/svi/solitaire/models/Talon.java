package com.svi.solitaire.models;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.stream.Collectors;

public class Talon {
    int drawCount;
    Deque<Card> stock;
    Deque<Card> waste;

    public Talon(int drawCount, Deque<Card> stock){
        this.drawCount = drawCount;
        this.stock = stock;
        waste = new ArrayDeque<>();
    }

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

    public Card drawWaste() {
        return waste.removeLast();
    }

    public void returnToStock(){
        stock = new ArrayDeque<>(waste);
        waste.clear();
    }

    public String wasteString() {
        StringBuilder stringBuilder = new StringBuilder();
        if (waste.isEmpty()){
            stringBuilder.append("[]");
        } else {
            stringBuilder.append("[");
            stringBuilder.append(
                    waste.stream()
                            .skip(Math.max(0, waste.size() - drawCount))
                            .map(card -> {
                                card.setVisible();
                                return card.toString();
                            })
                            .collect(Collectors.joining(", ")));
            stringBuilder.append("]");
        }
        return stringBuilder.toString();
    }

    public String stockString() {
        return "[???] x" + stock.size();
    }
}

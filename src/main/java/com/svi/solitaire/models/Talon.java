package com.svi.solitaire.models;

import com.svi.solitaire.models.piles.Stock;
import com.svi.solitaire.models.piles.Waste;

import java.util.ArrayDeque;
import java.util.stream.Collectors;

// TODO: can further break this down
public class Talon {
    private int drawCount;
    private Stock stock;
    private Waste waste;

    public Talon(int drawCount, Stock stock){
        this.drawCount = drawCount;
        this.stock = stock;
        waste = new Waste(drawCount);
    }

    public Card peekWaste() {
        return waste.peekTop();
    }

    public void removeFromWaste() {
        waste.removeTop();
    }

    public boolean canDraw() {
        return !stock.isEmpty();
    }

    public boolean canRestack() {
        return stock.isEmpty() && waste.size() > 1;
    }

    public void drawCards(){
        for (int i = 0; i < drawCount; i++) {
            if (stock.isEmpty()) {
                return;
            }
            Card drawnCard = stock.removeTop(); // D-A,S-A,D-5,C-2 => D-A
            waste.add(drawnCard); // D-A
        }
    }

    public void restack(){
        while (!waste.isEmpty()) {
            Card c = waste.removeTop();
            stock.add(c);
        }
    }

    public String wasteString() {
        return waste.toString();
    }

    public String stockString() {
        return stock.toString();
    }
}

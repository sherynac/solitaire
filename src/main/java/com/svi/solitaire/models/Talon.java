package com.svi.solitaire.models;

import com.svi.solitaire.models.piles.Stock;
import com.svi.solitaire.models.piles.Waste;

import java.util.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.Set;

public class Talon {
    private int drawCount;
    private Stock stock;
    private Waste waste;

    public Talon(int drawCount, Stock stock) {
        this.drawCount = drawCount;
        this.stock = stock;
        waste = new Waste(drawCount);
    }

    private final Set<Card> seen = Collections.newSetFromMap(new IdentityHashMap<>());

    public int drawCards() {
        int drawn = 0;
        while (drawn < drawCount && !stock.isEmpty()) {
            Card card = stock.removeTop();
            seen.add(card);                              // remember it
            waste.add(card);
            drawn++;
        }
        return drawn;
    }

    /**
     * Cards the player has already seen that sit under the waste top or are still in the stock,
     * in draw order. Stock cards that were never flipped are not included.
     */
    public List<Card> knownCardsBelowTop() {
        List<Card> known = new ArrayList<>(waste.peekTop(waste.size()));   // bottom to top
        if (!known.isEmpty()) known.remove(known.size() - 1);              // drop the waste top itself
        for (Card card : stock.topFirst()) {                               // next card to draw first
            if (seen.contains(card)) known.add(card);
        }
        return known;
    }

    public Card peekWaste() {
        return waste.peekTop();
    }

    public Card removeFromWaste() {
        return waste.removeTop();
    }

    public boolean canDraw() {
        return !stock.isEmpty();
    }

    public boolean canRestack() {
        return stock.isEmpty() && waste.size() > 1;
    }

    public int size() {
        return stock.size() + waste.size();
    }

//    public void drawCards(){
//        for (int i = 0; i < drawCount; i++) {
//            if (stock.isEmpty()) {
//                return;
//            }
//            Card drawnCard = stock.removeTop(); // D-A,S-A,D-5,C-2 => D-A
//            waste.add(drawnCard); // D-A
//        }
//    }

    public void restack() {
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

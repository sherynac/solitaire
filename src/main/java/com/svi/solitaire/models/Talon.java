package com.svi.solitaire.models;

import com.svi.solitaire.models.piles.Stock;
import com.svi.solitaire.models.piles.Waste;

import java.util.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.Set;

public class Talon {
    private final int drawCount;
    private final Stock stock;
    private final Waste waste;

    public Talon(int drawCount, Stock stock) {
        this.drawCount = drawCount;
        this.stock = stock;
        waste = new Waste(drawCount);
    }

    private final Set<Card> seen = Collections.newSetFromMap(new IdentityHashMap<>());

    public void drawCards() {
        int drawn = 0;
        while (drawn < drawCount && !stock.isEmpty()) {
            Card card = stock.removeTop();
            seen.add(card);                              // remember it
            waste.add(card);
            drawn++;
        }
    }

    /**
     * Cards the player has already seen that sit under the waste top or are still in the stock,
     * in draw order. Stock cards that were never flipped are not included.
     */
    public List<Card> knownCardsBelowTop() {
        List<Card> known = new ArrayList<>(waste.peekTop(waste.size()));   // bottom to top
        if (!known.isEmpty()) known.removeLast();              // drop the waste top itself
        for (Card card : stock.topFirst()) {                               // next card to draw first
            if (seen.contains(card)) known.add(card);
        }
        return known;
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

    public int size() {
        return stock.size() + waste.size();
    }

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

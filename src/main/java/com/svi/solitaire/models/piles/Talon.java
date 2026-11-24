package com.svi.solitaire.models.piles;

import com.svi.solitaire.models.Card;

import java.util.*;

public class Talon {
    private final int drawCount;
    private final Stock stock;
    private final Waste waste;
    private final Set<Card> seen;

    public Talon(int drawCount, Stock stock) {
        this.drawCount = drawCount;
        this.stock = stock;
        waste = new Waste(drawCount);
        this.seen = Collections.newSetFromMap(new IdentityHashMap<>());
    }

    public Talon(int drawCount, Stock stock, Waste waste, Set<Card> seen) {
        this.drawCount = drawCount;
        this.stock = stock;
        this.waste = waste;
        this.seen = Collections.newSetFromMap(new IdentityHashMap<>());
        this.seen.addAll(seen);
    }

    public Talon copy() {
        return new Talon(drawCount, stock.copy(), waste.copy(), seen);
    }

    public int size() {
        return stock.size() + waste.size();
    }

    public Card peekWaste() {
        return waste.peekTop();
    }

    public boolean canDraw() {
        return !stock.isEmpty();
    }

    public boolean canRestack() {
        return stock.isEmpty() && waste.size() > 1;
    }

    public void drawCards() {
        int drawn = 0;
        while (drawn < drawCount && !stock.isEmpty()) {
            Card card = stock.removeTop();
            seen.add(card);                              // remember it
            waste.add(card);
            drawn++;
        }
    }

    public void removeFromWaste() {
        waste.removeTop();
    }

    public void restack() {
        while (!waste.isEmpty()) {
            Card c = waste.removeTop();
            stock.add(c);
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
            if (seen.contains(card)) {
                known.add(card);
            }
        }
        return known;
    }

    public String wasteString() {
        return waste.toString();
    }

    public String stockString() {
        return stock.toString();
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("STOCK: ").append(stockString());
        stringBuilder.append("\nWASTE: ").append(wasteString());

        return stringBuilder.toString();
    }
}
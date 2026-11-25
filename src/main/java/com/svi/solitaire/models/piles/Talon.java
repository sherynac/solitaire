package com.svi.solitaire.models.piles;

import com.svi.solitaire.models.Card;

import java.util.*;

/**
 * Represents the talon area of a Solitaire game, consisting of a stock and
 * a waste pile.
 *
 * @author Sheryn Cagulada
 * @version 1.0
 */
public class Talon {

    private final int drawCount;
    private final Stock stock;
    private final Waste waste;
    private final Set<Card> seen;

    /**
     * Creates a talon with the specified draw count and stock.
     *
     * @param drawCount the maximum number of cards drawn per turn
     * @param stock the stockpile
     */
    public Talon(int drawCount, Stock stock) {
        this.drawCount = drawCount;
        this.stock = stock;
        waste = new Waste(drawCount);
        this.seen = Collections.newSetFromMap(new IdentityHashMap<>());
    }

    /**
     * Creates a talon using the specified draw count, stock, waste, and
     * previously seen cards.
     *
     * @param drawCount the maximum number of cards drawn per turn
     * @param stock the stockpile
     * @param waste the waste pile
     * @param seen the cards that have previously been seen
     */
    public Talon(int drawCount, Stock stock, Waste waste, Set<Card> seen) {
        this.drawCount = drawCount;
        this.stock = stock;
        this.waste = waste;
        this.seen = Collections.newSetFromMap(new IdentityHashMap<>());
        this.seen.addAll(seen);
    }

    /**
     * Creates an independent copy of this talon, including its stock,
     * waste, and previously seen cards.
     *
     * @return a new talon containing copies of the current state
     */
    public Talon copy() {
        return new Talon(drawCount, stock.copy(), waste.copy(), seen);
    }

    /**
     * Returns the total number of cards in the stock and waste piles.
     *
     * @return the total number of cards
     */
    public int size() {
        return stock.size() + waste.size();
    }

    /**
     * Returns the topmost card in the waste pile without removing it.
     *
     * @return the topmost waste card, or null if the waste is empty
     */
    public Card peekWaste() {
        return waste.peekTop();
    }

    /**
     * Checks whether cards can be drawn from the stock.
     *
     * @return true if the stock contains cards; false otherwise
     */
    public boolean canDraw() {
        return !stock.isEmpty();
    }

    /**
     * Checks whether the waste can be restacked into the stock.
     *
     * @return true if the waste can be restacked; false otherwise
     */
    public boolean canRestack() {
        return stock.isEmpty() && waste.size() > 1;
    }

    /**
     * Draws up to the configured number of cards from the stock and adds
     * them to the waste pile.
     */
    public void drawCards() {
        int drawn = 0;

        while (drawn < drawCount && !stock.isEmpty()) {
            Card card = stock.removeTop();
            seen.add(card);
            waste.add(card);
            drawn++;
        }
    }

    /**
     * Removes the topmost card from the waste pile.
     *
     * @throws NoSuchElementException if the waste pile is empty
     */
    public void removeFromWaste() {
        waste.removeTop();
    }

    /**
     * Moves all cards from the waste pile back into the stockpile.
     */
    public void restack() {
        while (!waste.isEmpty()) {
            Card c = waste.removeTop();
            stock.add(c);
        }
    }

    /**
     * Returns previously seen cards located below the current topmost
     * waste card, preserving their draw order.
     *
     * @return a list of known cards below the current waste top
     */
    public List<Card> knownCardsBelowTop() {
        List<Card> known = waste.snapshot();

        if (!known.isEmpty()) {
            known.removeLast();
        }

        for (Card card : stock.topFirst()) {
            if (seen.contains(card)) {
                known.add(card);
            }
        }

        return known;
    }

    /**
     * Returns the string representation of the waste pile.
     *
     * @return the formatted waste pile
     */
    public String wasteString() {
        return waste.toString();
    }

    /**
     * Returns the string representation of the stockpile.
     *
     * @return the formatted stockpile
     */
    public String stockString() {
        return stock.toString();
    }

    /**
     * Returns a formatted representation of the talon, including
     * its stock and waste piles.
     *
     * @return the formatted representation of the talon
     */
    @Override
    public String toString() {

        return "STOCK: " + stockString() +
                "\nWASTE: " + wasteString();
    }
}
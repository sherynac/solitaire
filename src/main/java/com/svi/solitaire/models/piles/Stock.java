package com.svi.solitaire.models.piles;

import com.svi.solitaire.models.Card;

import java.util.*;

/**
 * Stock representation containing the copy method
 *
 * @author Sheryn Cagulada
 * @version 1.0
 */
public class Stock extends DequePile {

    public Stock() {
        super();
    }

    public Stock(Deque<Card> cardDeque) {
        super(cardDeque);
    }

    /**
     * Creates a copy of the Stock
     *
     * @return a copy of the Stock
     */
    public Stock copy() {
        Deque<Card> cardDeque = new ArrayDeque<>(snapshot());
        return new Stock(cardDeque);
    }

    /**
     * Converts the stock to bottom-top
     *
     * @return list of cards from stock to bottom-top
     */
    public List<Card> topFirst() {
        List<Card> list = snapshot();
        Collections.reverse(list);
        return list;
    }

    /**
     * Creates the String representation of the Stock
     *
     * @return stock string repesentatiion
     */
    @Override
    public String toString() {
        return "[???] x" + size();
    }

}

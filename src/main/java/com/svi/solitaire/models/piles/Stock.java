package com.svi.solitaire.models.piles;

import com.svi.solitaire.models.Card;

import java.util.ArrayDeque;
import java.util.Deque;

public class Stock extends DequePile {

    public Stock() {
        super();
    }

    public Stock(Deque<Card> cardDeque) {
        super(cardDeque);
    }

    @Override
    public String toString() {
        return "[???] x" + size();
    }

    public Stock copy() {
        Deque<Card> cardDeque = new ArrayDeque<>(snapshot());
        return new Stock(cardDeque);
    }

}

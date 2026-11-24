package com.svi.solitaire.models.piles;

import com.svi.solitaire.models.Card;

import java.util.*;

public class Stock extends DequePile {

    public Stock() {
        super();
    }

    public Stock(Deque<Card> cardDeque) {
        super(cardDeque);
    }

    public Stock copy() {
        Deque<Card> cardDeque = new ArrayDeque<>(snapshot());
        return new Stock(cardDeque);
    }

    public List<Card> topFirst() {
        List<Card> list = snapshot();
        Collections.reverse(list);
        return list;
    }

    @Override
    public String toString() {
        return "[???] x" + size();
    }

}

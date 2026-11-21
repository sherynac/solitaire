package com.svi.solitaire.models.piles;

import com.svi.solitaire.models.Card;

import java.util.List;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;

public class DequePile implements Pile {

    private Deque<Card> cards = new ArrayDeque<>();

    @Override
    public boolean isEmpty() {
        return cards.isEmpty();
    }

    @Override
    public int size() {
        return cards.size();
    }

    @Override
    public Card peekTop() {
        return cards.peekLast();
    }

    @Override
    public void add(Card card) {
        cards.addLast(card);
    }

    @Override
    public Card removeTop() {
        return cards.removeLast();
    }

    public void clear() {
        cards.clear();
    }

    // DequePile
    protected List<Card> snapshot() {              // bottom to top
        return new ArrayList<>(cards);
    }
}

package com.svi.solitaire.models.piles;

import com.svi.solitaire.models.Card;

public interface Pile {

    boolean isEmpty();
    int size();
    Card peekTop();
    void add(Card card);
    Card removeTop();
}

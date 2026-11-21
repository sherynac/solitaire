package com.svi.solitaire.models.piles;

import com.svi.solitaire.models.Card;

public interface Pile {

    public boolean isEmpty();
    public int size();
    public Card peekTop();
    public void add(Card card);
    public Card removeTop();

}

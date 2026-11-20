package com.svi.solitaire.models.piles;

import com.svi.solitaire.models.Card;

public interface Pile {

    public void push(Card card);
    public int size();
    public void reset();

}

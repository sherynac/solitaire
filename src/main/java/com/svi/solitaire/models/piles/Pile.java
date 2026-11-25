package com.svi.solitaire.models.piles;

import com.svi.solitaire.models.Card;

/**
 * Interface containing the pile methods
 *
 * @author Sheryn Cagulada
 * @version 1.0
 */
public interface Pile {

    boolean isEmpty();
    int size();
    Card peekTop();
    void add(Card card);
    Card removeTop();
}

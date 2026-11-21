package com.svi.solitaire.models.piles;

public class Stock extends DequePile {
    @Override
    public String toString() {
        return "[???] x" + size();
    }
}

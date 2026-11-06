package com.svi.solitaire.models;

import com.svi.solitaire.enums.Rank;
import com.svi.solitaire.enums.Suit;
import com.svi.solitaire.utils.ColorHelper;

public class Card {
    private Rank rank;
    private Suit suit;
    private boolean isVisible;

    public Card(Suit suit, Rank rank){
        this.rank = rank;
        this.suit = suit;
        isVisible = true;
    }

    public Rank getRank() {
        return rank;
    }

    public Suit getSuit() {
        return suit;
    }

    public boolean getVisibility() {
        return isVisible;
    }

    public void setVisible() {
        this.isVisible = true;
    }

    public void setInvisible() {
        this.isVisible = false;
    }


    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append(suit).append("-");
        stringBuilder.append(((rank.getValue() > 10) ? rank : rank.getValue()));

        return (isVisible
                ? stringBuilder.toString()
                : " ?");
    }
}

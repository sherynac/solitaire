package com.svi.solitaire.utils;

import com.svi.solitaire.Constants;
import com.svi.solitaire.models.Card;
import com.svi.solitaire.models.Deck;
import com.svi.solitaire.models.Maneuver;
import com.svi.solitaire.models.Talon;
import com.svi.solitaire.models.piles.Stock;

public class CardDealer {
    public static Maneuver dealManeuver(Deck deck) {
        Maneuver maneuver = new Maneuver();

        for (int i = 0; i < Constants.MANEUVER_COLUMNS;  i++){
            for (int j = i; j < Constants.MANEUVER_COLUMNS; j++){
                Card card = deck.removeFirst();

                if (j == i) {
                    maneuver.addVisibleCard(j, card);
                } else {
                    maneuver.addCard(j, card);
                }
            }
        }

        return maneuver;
    }

    public static Talon dealTalon(Deck deck, int drawCount) {
        Stock stock = new Stock();

        while (deck.hasNext()) {
            stock.add(deck.removeLast());
        }

        return new Talon(drawCount, stock);
    }
}
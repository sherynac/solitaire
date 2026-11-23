package com.svi.solitaire.game;

import com.svi.solitaire.enums.Constants;
import com.svi.solitaire.models.Card;
import com.svi.solitaire.models.Deck;
import com.svi.solitaire.models.piles.ManeuverPile;
import com.svi.solitaire.models.piles.Stock;

public class CardDealer {
    public static ManeuverPile.Maneuver dealManeuver(Deck deck) {
        ManeuverPile.Maneuver maneuver = new ManeuverPile.Maneuver();

        for (int i = 0; i < Constants.MANEUVER_COLUMNS; i++){
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

    public static ManeuverPile.Talon dealTalon(Deck deck, int drawCount) {
        Stock stock = new Stock();

        while (deck.hasNext()) {
            stock.add(deck.removeLast());
        }

        return new ManeuverPile.Talon(drawCount, stock);
    }
}
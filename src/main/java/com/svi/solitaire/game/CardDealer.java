package com.svi.solitaire.game;

import com.svi.solitaire.models.piles.Maneuver;
import com.svi.solitaire.models.piles.Talon;
import com.svi.solitaire.models.Card;
import com.svi.solitaire.models.Deck;
import com.svi.solitaire.models.piles.Stock;
import com.svi.solitaire.utils.Constants;

public class CardDealer {
    public static Maneuver dealManeuver(Deck deck) {
        Maneuver maneuver = new Maneuver();

        for (int col = 0; col < Constants.MANEUVER_COLUMNS; col++) {
            for (int row = col; row < Constants.MANEUVER_ROWS; row++) {
                Card card = deck.removeFirst();

                if (col == row) {
                    maneuver.addVisibleCard(row, card);
                } else {
                    maneuver.addCard(row, card);
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
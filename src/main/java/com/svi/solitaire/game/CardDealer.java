package com.svi.solitaire.game;

import com.svi.solitaire.models.piles.Maneuver;
import com.svi.solitaire.models.piles.Talon;
import com.svi.solitaire.models.Card;
import com.svi.solitaire.models.Deck;
import com.svi.solitaire.models.piles.Stock;
import com.svi.solitaire.utils.Constants;

/**
 * Deals the deck into the maneuver and talon for the game
 *
 * @author Sheryn Cagulada
 * @version 1.0
 */
public class CardDealer {

    /**
     * Deal Maneuver piles in increasing length
     *
     * @param deck to be distributed cards
     * @return maneuver with distributed cards
     */
    public static Maneuver dealManeuver(Deck deck) {
        Maneuver maneuver = new Maneuver();

        for (int row = 0; row < Constants.MANEUVER_ROWS; row++) {
            for (int col = row; col < Constants.MANEUVER_COLUMNS; col++) {
                Card card = deck.removeFirst();

                if (row == col) {
                    maneuver.addVisibleCard(col, card);
                } else {
                    maneuver.addCard(col, card);
                }
            }
        }

        return maneuver;
    }

    /**
     * Deal remaining cards to the talon
     *
     * @param deck to be distributed cards
     * @param drawCount number of draws
     * @return talon with distributed cards
     */
    public static Talon dealTalon(Deck deck, int drawCount) {
        Stock stock = new Stock();

        while (deck.hasNext()) {
            stock.add(deck.removeLast());
        }

        return new Talon(drawCount, stock);
    }
}
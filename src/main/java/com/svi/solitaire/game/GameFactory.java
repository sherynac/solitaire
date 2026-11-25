package com.svi.solitaire.game;

import com.svi.solitaire.enums.Suit;
import com.svi.solitaire.models.Deck;
import com.svi.solitaire.models.Game;
import com.svi.solitaire.models.piles.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Creates and initializes Game objects by setting up the foundations, maneuver piles and talon
 *
 * @author Sheryn Cagulada
 * @version 1.0
 */
public class GameFactory {

    /**
     * Creates the Game Object
     *
     * @param deck to be distributed cards
     * @param drawCount number of draws
     * @return a new Game object
     */
    public static Game create(Deck deck, int drawCount) {

        Map<Suit, Foundation> foundations = new HashMap<>();
        for (Suit suit : Suit.values()) {
            foundations.put(suit, new Foundation(suit));
        }

        Maneuver maneuver = CardDealer.dealManeuver(deck);
        Talon talon = CardDealer.dealTalon(deck, drawCount);

        return new Game(talon, foundations, maneuver);
    }
}
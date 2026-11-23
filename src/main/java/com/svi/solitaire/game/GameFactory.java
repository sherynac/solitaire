package com.svi.solitaire.game;

import com.svi.solitaire.enums.Suit;
import com.svi.solitaire.models.Deck;
import com.svi.solitaire.models.Game;
import com.svi.solitaire.models.piles.Foundation;
import com.svi.solitaire.models.piles.Maneuver;
import com.svi.solitaire.models.piles.ManeuverPile;
import com.svi.solitaire.models.piles.Talon;

import java.util.HashMap;
import java.util.Map;

public class GameFactory {

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
package com.svi.solitaire;

import com.svi.solitaire.enums.Suit;
import com.svi.solitaire.models.Deck;
import com.svi.solitaire.models.Game;
import com.svi.solitaire.models.Maneuver;
import com.svi.solitaire.models.piles.Foundation;
import com.svi.solitaire.models.Talon;
import com.svi.solitaire.utils.CardDealer;

import java.util.*;

public class GameFactory {

    public static Game create(Deck deck, int drawCount) {

        Map<Suit, Foundation> foundations = new HashMap<>();
        for (Suit suit: Suit.values()) {
            foundations.put(suit, new Foundation(suit));
        }

        Maneuver maneuver = CardDealer.dealManeuver(deck);
        Talon talon = CardDealer.dealTalon(deck, drawCount);

        return new Game (talon, foundations, maneuver);
    }
}

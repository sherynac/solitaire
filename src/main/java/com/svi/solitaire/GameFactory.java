package com.svi.solitaire;

import com.svi.solitaire.enums.Suit;
import com.svi.solitaire.models.Card;
import com.svi.solitaire.models.Deck;
import com.svi.solitaire.models.Game;
import com.svi.solitaire.models.Maneuver;
import com.svi.solitaire.models.piles.FoundationPile;
import com.svi.solitaire.models.piles.TalonPile;
import com.svi.solitaire.shuffle.ShuffleStrategy;
import com.svi.solitaire.utils.CardDealer;

import java.util.*;

public class GameFactory {

    public static Game create(Deck deck, int drawCount) {

        Map<Suit, FoundationPile> foundations = new HashMap<>();
        for (Suit suit: Suit.values()) {
            foundations.put(suit, new FoundationPile(suit));
        }

        Maneuver maneuver = CardDealer.dealManeuver(deck);
        TalonPile talon = CardDealer.dealTalon(deck, drawCount);

        return new Game (talon, foundations, maneuver);
    }
}

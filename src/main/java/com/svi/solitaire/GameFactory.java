package com.svi.solitaire;

import com.svi.solitaire.enums.Suit;
import com.svi.solitaire.models.Card;
import com.svi.solitaire.models.Deck;
import com.svi.solitaire.models.Game;
import com.svi.solitaire.models.Maneuver;
import com.svi.solitaire.models.piles.Foundation;
import com.svi.solitaire.models.piles.Talon;
import com.svi.solitaire.shuffle.ShuffleStrategy;
import com.svi.solitaire.utils.CardDealer;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

public class GameFactory {

    public static Game create(int drawCount, ShuffleStrategy shuffleStrategy) {

        List<Foundation> foundations = new ArrayList<>();
        for (Suit suit: Suit.values()) {
            Foundation foundation = new Foundation(suit);
            foundations.add(foundation);
        }

        Deck deck = new Deck(shuffleStrategy);
        deck.shuffleDeck();

        Maneuver maneuver = CardDealer.dealManeuver(deck);
        Deque<Card> talonCards = CardDealer.dealTalon(deck);
        Talon talon = new Talon(drawCount, talonCards);

        return new Game (talon, foundations, maneuver);
    }
}

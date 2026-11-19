package com.svi.solitaire.utils;

import com.svi.solitaire.models.Card;
import com.svi.solitaire.models.Deck;
import com.svi.solitaire.models.Maneuver;

import java.util.*;

public class CardDealer {
    public static Maneuver dealManeuver(Deck deck) {
        Maneuver maneuver = new Maneuver();

        for (int i = 0; i < 7;  i++){
            for (int j = i; j < 7; j++){
                Card card = deck.removeFirst();

                if (j == i) {
                    card.setVisible();}

                maneuver.addCard(j, card);
            }
        }

        return maneuver;
    }

    public static Deque<Card> dealTalon(Deck deck) {

        Deque<Card> cardDeque = new ArrayDeque<>();

        while (deck.hasNext()) {
            cardDeque.push(deck.removeFirst());
        }

        return cardDeque;
    }
}
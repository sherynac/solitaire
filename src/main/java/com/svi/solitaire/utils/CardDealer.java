package com.svi.solitaire.utils;

import com.svi.solitaire.models.Card;
import com.svi.solitaire.models.Deck;
import com.svi.solitaire.models.Maneuver;
import com.svi.solitaire.models.piles.ManeuverColumn;
import com.svi.solitaire.models.piles.Talon;

import java.util.*;

public class CardDealer {
    public static Maneuver dealManeuver(Deck deck) {
        Deque<Card> cardDeque = new ArrayDeque<>(deck.getGameDeck());
        Maneuver maneuver = new Maneuver();
        List<ManeuverColumn> maneuverColumns = maneuver.getColumnList();

        for (int i = 0; i < 7;  i++){
            for (int j = i; j < 7; j++){
                Card card = cardDeque.removeFirst();

                if (j == i) {
                    card.setVisible();}

                maneuverColumns.get(j).addCard(card);
            }
        }

        return maneuver;
    }

    public static Deque<Card> dealTalon(Deck deck) {
        return new ArrayDeque<>(deck.getGameDeck());
    }


}
package com.svi.solitaire.models;

import com.svi.solitaire.enums.Color;
import com.svi.solitaire.enums.ShuffleType;
import com.svi.solitaire.enums.Suit;
import com.svi.solitaire.models.piles.Foundation;
import com.svi.solitaire.models.piles.Talon;
import com.svi.solitaire.utils.ColorHelper;
import com.svi.solitaire.utils.InputHandler;
import com.svi.solitaire.shuffle.ShuffleStrategy;

import java.util.ArrayList;
import java.util.List;


public class Game {
    private Maneuver maneuver;
    private Talon talon;
    private List<Foundation> foundations;

    public Game() {
    }

    public Game(Talon talon, List<Foundation> foundations, Maneuver maneuver) {
        this.talon = talon;
        this.foundations = foundations;
        this.maneuver = maneuver;
    }

    public void displayCurrentState() {
        System.out.println(ColorHelper.color("\n====================== Move #: ==========================", Color.BLUE));

        System.out.printf("%n%-13s", "Stock");
        System.out.printf("%-20s", "Waste");
        System.out.print("Foundation");

        System.out.printf("%n%-13s", talon.stockString());
        System.out.printf("%-20s", talon.wasteString());

        for (Foundation foundation: foundations) {
            System.out.print(foundation + " ");
        }

        System.out.println("\n\n---------------------------------------------------------");
        System.out.println(maneuver);
        System.out.println(ColorHelper.color("=========================================================", Color.BLUE));
    }

    public boolean isGameOver() {
        return foundations.stream()
                .allMatch(foundation -> foundation.getCardCount() == 13);
    }

    public void addToFoundation(Card card) {
        for (Foundation foundation : foundations) {
            if (foundation.getSuit() == card.getSuit()) {
                foundation.add(card);
            }
        }
    }

    /**
     * TODO: MOVE CHECKERS FIX LATER ON
     */

//    public boolean canMoveToFoundation (Card card) {
//        if (card.getRank() == Rank.A) {
//            return true;
//        }
//
//        return false;
//    }
//
//    public void restartGame() {
//
//    }


}

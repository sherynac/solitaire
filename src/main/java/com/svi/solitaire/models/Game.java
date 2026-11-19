package com.svi.solitaire.models;

import com.svi.solitaire.enums.Color;
import com.svi.solitaire.enums.Suit;
import com.svi.solitaire.models.piles.FoundationPile;
import com.svi.solitaire.models.piles.TalonPile;
import com.svi.solitaire.utils.ColorHelper;

import java.util.List;
import java.util.Map;


public class Game {
    private Maneuver maneuver;
    private TalonPile talon;
    private Map<Suit, FoundationPile> foundations;

    public Game(TalonPile talon, Map<Suit, FoundationPile> foundations, Maneuver maneuver) {
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

        for (Map.Entry<Suit, FoundationPile> entry : foundations.entrySet()) {
            System.out.print(entry.getValue());
        }

        System.out.println("\n\n---------------------------------------------------------");
        System.out.println(maneuver);
        System.out.println(ColorHelper.color("=========================================================", Color.BLUE));
    }

//    public boolean isGameOver() {
//        return foundations.stream()
//                .allMatch(foundation -> foundation.getCardCount() == 13);
//    }
//
//    public void addToFoundation(Card card) {
//        for (FoundationPile foundation : foundations) {
//            if (foundation.getSuit() == card.getSuit()) {
//                foundation.add(card);
//            }
//        }
//    }

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

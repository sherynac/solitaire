package com.svi.solitaire.utils;

import com.svi.solitaire.enums.Rank;
import com.svi.solitaire.models.Card;

public class MoveChecker {
    //
    public static boolean canMoveToFoundation(Card card) {
        if (card.getRank() == Rank.A) {

            return true;
        }

    }
//
//    public canMoveToManeuver() {
//
//    }
//
//    public canMoveKing() {
//
//    }
}

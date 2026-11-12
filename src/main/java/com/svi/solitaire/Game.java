package com.svi.solitaire;

import com.svi.solitaire.enums.Rank;
import com.svi.solitaire.enums.ShuffleType;
import com.svi.solitaire.enums.Suit;
import com.svi.solitaire.models.*;
import com.svi.solitaire.utils.ColorHelper;
import com.svi.solitaire.utils.InputHandler;
import com.svi.solitaire.utils.Solver;

import java.util.ArrayList;
import java.util.List;


public class Game {
    private Maneuver maneuver;
    private Talon talon;
    private List<Foundation> foundations;
    private Dealer dealer;
    private Solver solver;

    public Game() {
        this.dealer = new Dealer();
        foundations = new ArrayList<>();
        this.solver = new Solver(this);

        for (Suit suit: Suit.values()) {
            Foundation foundation = new Foundation(suit);
            foundations.add(foundation);
        }
    }

    public void run() {
        setupGame();

//        while (!isGameOver()) {
            displayCurrentState();
            talon.drawCards();
            displayCurrentState();
            talon.drawCards();

            Card card = talon.drawWaste();
            System.out.println("DRAWN: " + card);


            addToFoundation(card);

            displayCurrentState();

            displayCurrentState();

//            talon.returnToStock();
//            displayCurrentState();

            addToFoundation(talon.drawWaste());
            displayCurrentState();

//        }
    }

    public void setupGame() {
        System.out.println("========== Welcome to Solitaire! ==========");
        System.out.println("\n!! Game Mode !! ");
        int drawCount = InputHandler.inputDrawCount();
        System.out.println(ColorHelper.color("Game is set to draw " + drawCount + " from talon.", "YELLOW"));

        ShuffleType shuffleChoice = InputHandler.inputShuffleChoice();

        System.out.println(ColorHelper.color("\nDealer is shuffling the deck...", "YELLOW"));
        dealer.shuffleDeck(shuffleChoice);
        System.out.println(dealer.showCards());

        this.maneuver = dealer.dealCards();
        this.talon = new Talon(drawCount, dealer.placeTalon());
    }

    public void displayCurrentState() {
        System.out.println(ColorHelper.color("\n====================== Move #: ==========================", "BLUE"));

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
        System.out.println(ColorHelper.color("=========================================================", "BLUE"));
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

    public boolean canMoveToFoundation (Card card) {
        if (card.getRank() == Rank.A) {
            return true;
        }

        return false;
    }


}

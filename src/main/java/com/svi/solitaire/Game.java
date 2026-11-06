package com.svi.solitaire;

import com.svi.solitaire.enums.Rank;
import com.svi.solitaire.enums.ShuffleType;
import com.svi.solitaire.enums.Suit;
import com.svi.solitaire.models.*;
import com.svi.solitaire.utils.ColorHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Game {
    private Tableau tableau;
    private Talon talon;
    private List<Foundation> foundations;
    private Dealer dealer;
    private Scanner scanner;

    public Game(Scanner scanner){
        this.scanner = scanner;
        this.dealer = new Dealer();
        foundations = new ArrayList<>();

        for (Suit suit: Suit.values()){
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
            foundations.get(0).addToFoundation(new Card(Suit.D, Rank.SIX));

            displayCurrentState();


//        }
    }

    public void setupGame(){
        System.out.println("========== Welcome to Solitaire! ==========");
        System.out.println("\n!! Game Mode !! ");
        int drawCount = inputDrawChoice(scanner);
        System.out.println(ColorHelper.color("Game is set to draw " + drawCount + " from talon.", "YELLOW"));

        ShuffleType shuffleChoice = inputShuffleChoice(scanner);

        System.out.println(ColorHelper.color("\nDealer is shuffling the deck...", "YELLOW"));
        dealer.shuffleDeck(shuffleChoice);
        System.out.println(dealer.showCards());

        this.tableau = dealer.dealCards();
        this.talon = new Talon(drawCount, dealer.placeTalon());
    }

    private int inputDrawChoice(Scanner scanner){
        int drawChoice;

        while(true) {
            try {
                System.out.print("\nEnter '1' for draw one and '3' for draw three (1,3): ");
                drawChoice = Integer.parseInt(scanner.nextLine());
                if (drawChoice == 1 || drawChoice == 3) {
                    return drawChoice;
                } else {
                    System.out.println("Make sure to enter either 1 or 3 only.");
                }
            } catch (NumberFormatException nfe){
                System.out.println("Make sure to enter a number.");
            }
        }
    }

    private ShuffleType inputShuffleChoice(Scanner scanner){
        int shuffleChoice;

        while(true) {
            try {
                System.out.print("\nEnter '1' for perfect shuffle and '2' for random shuffle (1,2): ");
                shuffleChoice = Integer.parseInt(scanner.nextLine());
                if (shuffleChoice == 1) {
                    System.out.println(ColorHelper.color("Shuffle type is set to perfect shuffle.", "YELLOW"));
                    return ShuffleType.PERFECT;
                } else if (shuffleChoice == 2) {
                    System.out.println(ColorHelper.color("Shuffle type is set to random shuffle.", "YELLOW"));
                    return ShuffleType.RANDOM;
                } else {
                    System.out.println("Make sure to enter either 1 or 2 only.");
                }
            } catch (NumberFormatException nfe){
                System.out.println("Make sure to enter a number.");
            }
        }
    }

    public void displayCurrentState(){
        System.out.println(ColorHelper.color("====================== Move #: ==========================", "BLUE"));

        System.out.printf("%n%-13s", "Stock");
        System.out.printf("%-20s", "Waste");
        System.out.print("Foundation");

        System.out.printf("%n%-13s", talon.stockString());
        System.out.printf("%-20s", talon.wasteString());

        for (Foundation foundation: foundations){
            System.out.print(foundation + " ");
        }

        System.out.println("\n\n---------------------------------------------------------");
        System.out.println(tableau);
        System.out.println(ColorHelper.color("=========================================================", "BLUE"));
    }

    public boolean isGameOver() {
        return foundations.stream()
                .allMatch(foundation -> foundation.getCardCount() == 13);
    }

    /**
     * TODO: FOLLOWING METHODS MOVE TO SOMEWHERE ELSE
     */


    public void addToColumn() {

    }

    public void moveSequence() {

    }

    public void canMoveSequence() {

    }




}

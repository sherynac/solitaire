package com.svi.solitaire;


import com.svi.solitaire.enums.Rank;
import com.svi.solitaire.enums.Suit;
import com.svi.solitaire.models.Card;
import com.svi.solitaire.models.TableauColumn;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Scanner;
import java.util.Stack;

public class Main {
    private static Deque<Card> testDeque;

    static void main() {
        Scanner scanner = new Scanner(System.in);
        boolean willPlayAgain = true;

        TableauColumn sample = new TableauColumn();

        testDeque = new ArrayDeque<>();

        testDeque.push(new Card(Suit.D, Rank.TEN));
        testDeque.push(new Card(Suit.C, Rank.NINE));
        testDeque.push(new Card(Suit.D, Rank.EIGHT));
        testDeque.push(new Card(Suit.C, Rank.SEVEN));
        testDeque.push(new Card(Suit.D, Rank.SIX));

        Deque<Card> stackToMove = new ArrayDeque<>();

        stackToMove.push(new Card(Suit.C, Rank.FIVE));
        stackToMove.push(new Card(Suit.D, Rank.FOUR));
        stackToMove.push(new Card(Suit.C, Rank.THREE));

        moveStack(stackToMove);

        System.out.println("CHECKER");
        System.out.println(testDeque);

        try {
            while (willPlayAgain) {
                Game game = new Game(scanner);
                game.run();
                willPlayAgain = playAgain(scanner);
            }
        } catch (Exception e) {
            System.out.println("Program has encountered an error: " + e.getMessage());
            System.out.println("Kindly rerun the program");
        }

    }

    public static void moveStack(Deque<Card> stackToMove) {
        for (Card card : stackToMove) {
            testDeque.push(card);
        }
    }

    private static boolean playAgain(Scanner scanner){

        while (true) {
            System.out.print("Do you want to play again? (y/n): ");
            String input = scanner.nextLine();
            if (input.equalsIgnoreCase("y")){
                return true;
            } else if (input.equalsIgnoreCase("n")) {
                return false;
            }
        }
    }
}

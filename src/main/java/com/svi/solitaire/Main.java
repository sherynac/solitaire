package com.svi.solitaire;


import com.svi.solitaire.enums.Rank;
import com.svi.solitaire.enums.Suit;
import com.svi.solitaire.models.Card;
import com.svi.solitaire.models.TableauColumn;

import java.util.Scanner;
import java.util.Stack;

public class Main {
    private static Stack<Card> testStack;

    static void main() {
        Scanner scanner = new Scanner(System.in);
        boolean willPlayAgain = true;

        TableauColumn sample = new TableauColumn();

        testStack = new Stack<>();

        testStack.push(new Card(Suit.D, Rank.TEN));
        testStack.push(new Card(Suit.C, Rank.NINE));
        testStack.push(new Card(Suit.D, Rank.EIGHT));
        testStack.push(new Card(Suit.C, Rank.SEVEN));
        testStack.push(new Card(Suit.D, Rank.SIX));

        Stack<Card> stackToMove = new Stack<>();

        stackToMove.push(new Card(Suit.C, Rank.FIVE));
        stackToMove.push(new Card(Suit.D, Rank.FOUR));
        stackToMove.push(new Card(Suit.C, Rank.THREE));

        moveStack(stackToMove);

        System.out.println("CHECKER");
        System.out.println(testStack);

        while (willPlayAgain) {
            Game game = new Game(scanner);
            game.run();
            willPlayAgain = playAgain(scanner);
        }
    }

    public static void moveStack(Stack<Card> stackToMove) {
        for (Card card : stackToMove) {
            testStack.add(card);
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

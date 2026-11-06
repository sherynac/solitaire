package com.svi.solitaire.models;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.Stack;

public class TableauColumn {
    Stack<Card> cardStack;

    public TableauColumn(){
        cardStack = new Stack<>();
    }

    public TableauColumn(Stack<Card> cards) {
        cardStack = cards;
    }

    public void addCard(Card card) {
        cardStack.push(card);
    }

    public int getCardCount(){
        return cardStack.size();
    }

    public void moveCards(Stack<Card> movingCard) {
        for (Card card : movingCard) {
            cardStack.push(card);
        }
    }

    public Card getCardAt(int index){
        return cardStack.get(index);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (Card card: cardStack){
            stringBuilder.append("\n").append(card);
        }
        return stringBuilder.toString();
    }
}

package com.svi.solitaire.models;

import java.util.*;

public class TableauColumn {
    Deque<Card> cardStack;

    public TableauColumn(){
        cardStack = new ArrayDeque<>();
    }

    public TableauColumn(Deque<Card> cards) {
        cardStack = cards;
    }

    public void addCard(Card card) {
        cardStack.push(card);
    }

    public int getCardCount(){
        return cardStack.size();
    }

    public void addCards(Deque<Card> movingCard) {
        for (Card card : movingCard) {
            cardStack.push(card);
        }
    }

    public Card getCardAt(int index) {
        if (index < 0 || index >= cardStack.size()) {
            throw new IndexOutOfBoundsException("Invalid card index: " + index);
        }

        Iterator<Card> iterator = cardStack.iterator();

        for (int i = 0; i < index; i++) {
            iterator.next();
        }

        return iterator.next();
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

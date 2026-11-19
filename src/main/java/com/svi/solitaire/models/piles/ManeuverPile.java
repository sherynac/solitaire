package com.svi.solitaire.models.piles;

import com.svi.solitaire.models.Card;

import java.util.*;

public class ManeuverPile implements Pile {
    Deque<Card> cardDeque;

    public ManeuverPile(){
        cardDeque = new ArrayDeque<>();
    }

    public ManeuverPile(Deque<Card> cards) {
        cardDeque = cards;
    }

    @Override
    public void push(Card card) {
        cardDeque.push(card);
    }

    public void addLast(Card card) {
        cardDeque.addLast(card);
    }

    @Override
    public int size() {
        return cardDeque.size();
    }

    @Override
    public void reset() {
        cardDeque.clear();
    }

    public void addCards(Deque<Card> movingCard) {
        for (Card card : movingCard) {
            cardDeque.push(card);
        }
    }

    public Card getCardAt(int index) {
        if (index < 0 || index >= cardDeque.size()) {
            throw new IndexOutOfBoundsException("Invalid card index: " + index);
        }

        Iterator<Card> iterator = cardDeque.iterator();

        for (int i = 0; i < index; i++) {
            iterator.next();
        }

        return iterator.next();
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (Card card: cardDeque){
            stringBuilder.append("\n").append(card);
        }
        return stringBuilder.toString();
    }

}

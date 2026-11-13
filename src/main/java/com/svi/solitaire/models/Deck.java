package com.svi.solitaire.models;

import com.svi.solitaire.enums.Color;
import com.svi.solitaire.enums.Rank;
import com.svi.solitaire.enums.Suit;
import com.svi.solitaire.shuffle.ShuffleStrategy;
import com.svi.solitaire.utils.ColorHelper;

import java.util.*;

public class Deck {
    private Deque<Card> originalDeck;
    private Deque<Card> gameDeck;
    private ShuffleStrategy shuffleStrategy;

    public Deck(ArrayDeque<Card> cards) {
        originalDeck = new ArrayDeque<>();

        for (Suit suit : Suit.values()) {
            for (Rank rank : Rank.values()) {
                originalDeck.add(new Card(suit, rank));
            }
        }

        gameDeck = new ArrayDeque<>(originalDeck);
    }

    public Deck(ShuffleStrategy shuffleStrategy) {
        this();
        this.shuffleStrategy = shuffleStrategy;
    }

    public void shuffleDeck() {
        Random random = new Random();
        System.out.println(ColorHelper.color("\nInitial Deck: ", Color.YELLOW));
        System.out.println(this);

        this.gameDeck = shuffleStrategy.shuffle(random, gameDeck);
        System.out.println(ColorHelper.color("\nShuffled Deck: ", Color.YELLOW));
        System.out.println(this);
    }

    public List<Card> getGameDeck() {
        return gameDeck;
    }

    public void setShuffleStrategy(ShuffleStrategy shuffleStrategy) {
        this.shuffleStrategy = shuffleStrategy;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < gameDeck.size(); i++) {
            stringBuilder.append(gameDeck.get(i));

            if ((i + 1) % 15 == 0) {
                stringBuilder.append("\n");
            } else if (i != gameDeck.size() - 1) {
                stringBuilder.append(", ");
            }
        }
        return stringBuilder.toString();
    }

}

package com.svi.solitaire.io;

import com.svi.solitaire.Constants;
import com.svi.solitaire.enums.Rank;
import com.svi.solitaire.enums.Suit;
import com.svi.solitaire.models.Card;
import com.svi.solitaire.models.Deck;

import java.io.*;
import java.util.*;

public class FileHandler {

    public static Deck loadDeck(String filename) throws IOException {
        InputStream input = FileHandler.class.getClassLoader()
                .getResourceAsStream(filename);

        if (input == null) {
            throw new FileNotFoundException("Card text file was not found.");
        }

        Deque<Card> cards = readDeck(input);
        validateDeck(cards);
        return new Deck(cards);
    }

    private static Deque<Card> readDeck(InputStream input) throws IOException{
        Deque<Card> cardsDeque = new ArrayDeque<>();

        try (BufferedReader br = new BufferedReader(new InputStreamReader(input))) {
            String line;

            while ((line = br.readLine()) != null) {
                String[] cards = line.split(",");

                for (String cardString : cards) {
                    Card card = parseCard(cardString.trim());
                    cardsDeque.add(card);
                }
            }
        }

        return cardsDeque;
    }

    private static Card parseCard(String cardString) {

        String[] parts = cardString.split("-");

        if (parts.length != Constants.NUM_CARD_PARTS) {
            throw new IllegalArgumentException("Incorrect Card Format: " + cardString);
        }

        return new Card(Suit.fromText(parts[0]), Rank.fromText(parts[1]));
    }

    private static void validateDeck(Deque<Card> cards) {
        if (cards.size() != Constants.DECK_SIZE) {
            throw new IllegalArgumentException("Expected deck size is 52 but found " + cards.size() + " cards.");
        }

        Set<Card> seen = new HashSet<>(cards);

        if (seen.size() != cards.size()) {
            throw new IllegalArgumentException("Deck contains duplicate cards.");
        }
    }
}

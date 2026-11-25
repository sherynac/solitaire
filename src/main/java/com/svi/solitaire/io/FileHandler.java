package com.svi.solitaire.io;

import com.svi.solitaire.utils.Constants;
import com.svi.solitaire.enums.Rank;
import com.svi.solitaire.enums.Suit;
import com.svi.solitaire.models.Card;
import com.svi.solitaire.models.Deck;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * Handles the loading, reading, parsing and validation of the deck from a file
 *
 * @author Sheryn Cagulada
 * @version 1.0
 */
public class FileHandler {

    /**
     * Loads a deck of cards from the specified resource file and validates its contents.
     *
     * @param filename the name of the card text file to load
     * @return a deck containing the loaded and validated cards
     * @throws IOException if an I/O error occurs while reading the file or if the file is not found
     */
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

    /**
     * Reads the contents of an input stream and parses each card entry
     * into a collection of Card objects.
     *
     * @param input the input stream containing the card entries
     * @return a deque containing the parsed cards
     * @throws IOException if an I/O error occurs while reading the input stream
     */
    private static Deque<Card> readDeck(InputStream input) throws IOException {
        Deque<Card> cardsDeque = new ArrayDeque<>();

        try (BufferedReader br = new BufferedReader(new InputStreamReader(input, StandardCharsets.UTF_8))) {
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

    /**
     * Parses a String into a Card object
     *
     * @param cardString the String representation of card
     * @return the created Card object
     */
    private static Card parseCard(String cardString) {

        String[] parts = cardString.split("-");

        if (parts.length != Constants.CARD_PARTS) {
            throw new IllegalArgumentException("Incorrect Card Format: " + cardString);
        }

        return new Card(Suit.fromText(parts[0]), Rank.fromText(parts[1]));
    }

    /**
     * Checks that there are enough cards and no duplicated cards
     *
     * @param cards the read list of cards
     */
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

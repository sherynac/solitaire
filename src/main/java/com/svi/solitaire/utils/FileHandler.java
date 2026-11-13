package com.svi.solitaire.utils;

import com.svi.solitaire.models.Card;
import com.svi.solitaire.models.Deck;

import java.io.*;

public class FileHandler {
    public static Deck loadDeck() throws IOException {
        InputStream input = FileHandler.class.getClassLoader()
                .getResourceAsStream("input.txt");

        if (input == null) {
            throw new FileNotFoundException("Card text file was not found.");
        }

        Deck deck = readDeck(input);




    }

    private static Deck readDeck(InputStream input) throws IOException{
        try (BufferedReader br = new BufferedReader(new InputStreamReader(input))) {
            String line;

            while ((line = br.readLine()) != null) {
                String[] cards = line.split(",");

//                while (cards)
            }
        }

    }

    private static Card parseDeck(String cardString) {

        String[] parts = cardString.split("-");

        return new Card()

    }
}

package com.svi.solitaire.models;

import com.svi.solitaire.enums.ShuffleType;
import com.svi.solitaire.enums.Suit;
import com.svi.solitaire.enums.Rank;

import java.util.*;

public class Dealer {

    private List<Card> cards = new ArrayList<>();

    public Dealer() {
        for (Suit suit : Suit.values()) {
            for (Rank rank : Rank.values()) {
                cards.add(new Card(suit, rank));
            }
        }
    }

    /**
     * TODO: method is too long
     */
    public void shuffleDeck(ShuffleType shuffleType) {
        Random random = new Random();
        int numShuffles = random.nextInt(100);

        if (shuffleType == ShuffleType.RANDOM){
            for (int i = 0; i < numShuffles; i++){
                Collections.shuffle(cards);
            }
        } else {
            for (int k = 0; k < numShuffles; k++) {
                List<Card> list1 = cards.subList(0,26);
                List<Card> list2 = cards.subList(26,52);
                List<Card> shuffledDeck = new ArrayList<>();

                for (int i = 0; i < list1.size(); i++) {
                    shuffledDeck.add(list1.get(i));
                    shuffledDeck.add(list2.get(i));
                }
                cards = shuffledDeck;
            }
        }
    }

    public Tableau dealCards() {
        Tableau tableau = new Tableau();
        List<TableauColumn> tableauColumns = tableau.getColumnList();
        for (int i = 0; i < 7;  i++){
            for (int j = i; j < 7; j++){
                Card card = cards.removeFirst();

                if (j == i) {
                    card.setVisible();}

                tableauColumns.get(j).addCard(card);
            }
        }

        return tableau;
    }

    public List<Card> placeTalon() {
        return cards;
    }

    public void clearCards() {

    }


    public String showCards() {
        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < cards.size(); i++) {
            stringBuilder.append(cards.get(i));

            if ((i + 1) % 15 == 0) {
                stringBuilder.append("\n");
            } else if (i != cards.size() - 1) {
                stringBuilder.append(", ");
            }
        }
        return stringBuilder.toString();
    }
}
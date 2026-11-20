package com.svi.solitaire.models;

import com.svi.solitaire.Constants;
import com.svi.solitaire.enums.Color;
import com.svi.solitaire.enums.Suit;
import com.svi.solitaire.models.piles.FoundationPile;
import com.svi.solitaire.models.piles.ManeuverPile;
import com.svi.solitaire.models.piles.TalonPile;
import com.svi.solitaire.utils.ColorHelper;

import java.util.Map;


public class Game {
    private Maneuver maneuver;
    private TalonPile talon;
    private Map<Suit, FoundationPile> foundations;

    public Game(TalonPile talon, Map<Suit, FoundationPile> foundations, Maneuver maneuver) {
        this.talon = talon;
        this.foundations = foundations;
        this.maneuver = maneuver;
    }

    public void displayCurrentState() {
        System.out.println(ColorHelper.color("\n====================== Move #: ==========================", Color.BLUE));

        System.out.printf("%n%-13s", "Stock");
        System.out.printf("%-20s", "Waste");
        System.out.print("Foundation");

        System.out.printf("%n%-13s", talon.stockString());
        System.out.printf("%-20s", talon.wasteString());

        for (Map.Entry<Suit, FoundationPile> entry : foundations.entrySet()) {
            System.out.print(entry.getValue());
        }

        System.out.println("\n\n---------------------------------------------------------");
        System.out.println(maneuver);
        System.out.println(ColorHelper.color("=========================================================", Color.BLUE));
    }

    public Maneuver getManeuver() {
        return maneuver;
    }

    public TalonPile getTalon() {
        return talon;
    }

    public FoundationPile getFoundation(Suit suit) {
        return foundations.get(suit);
    }

    public void moveWasteToFoundation() {
        Card card = talon.peekWaste();
        if (card == null) throw new IllegalArgumentException("The waste is empty. ");

        foundations.get(card.getSuit()).push(card);
        talon.removeFromWaste();
    }

    public void moveWasteToColumn(int column) {
        Card card = talon.peekWaste();
        if (card == null) throw new IllegalStateException("The waste is empty.");

        maneuver.getColumn(column).addFlippedCard(card);
        talon.removeFromWaste();
    }

    public void moveManeuverToFoundation(int column) {
        ManeuverPile source = maneuver.getColumn(column);
        Card card = source.peekLast();
        if (card == null) throw new IllegalStateException("Column " + column + " is empty.");

        foundations.get(card.getSuit()).push(card);
        source.removeLast();

        if (source.hasHiddenCards()) {
            source.flipTop();
        }
    }

    public void moveManeuverToManeuver(int from, int to, int count) {
        maneuver.moveCards(from, to, count);
    }

    public void drawTalon()    { talon.drawCards(); }
    public void restackTalon() { talon.restack(); }

    public boolean isGameOver() {
        for (Map.Entry<Suit, FoundationPile> foundation : foundations.entrySet()) {
            if (foundation.getValue().size() != Constants.CARD_PER_SUIT) {
                return false;
            }
        }
        return true;
    }

}

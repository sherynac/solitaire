package com.svi.solitaire.models;

import com.svi.solitaire.models.piles.Maneuver;
import com.svi.solitaire.models.piles.Talon;
import com.svi.solitaire.utils.Constants;
import com.svi.solitaire.enums.Suit;
import com.svi.solitaire.models.piles.Foundation;
import com.svi.solitaire.models.piles.ManeuverPile;

import java.util.Map;


public class Game {
    private Maneuver maneuver;
    private Talon talon;
    private Map<Suit, Foundation> foundations;

    public Game(Talon talon, Map<Suit, Foundation> foundations, Maneuver maneuver) {
        this.talon = talon;
        this.foundations = foundations;
        this.maneuver = maneuver;
    }

    public Maneuver getManeuver() {
        return maneuver;
    }

    public Talon getTalon() {
        return talon;
    }

    public Foundation getFoundation(Suit suit) {
        return foundations.get(suit);
    }

    public void moveWasteToFoundation() {
        Card card = talon.peekWaste();
        if (card == null) throw new IllegalArgumentException("The waste is empty. ");

        foundations.get(card.suit()).add(card);
        talon.removeFromWaste();
    }

    public void moveWasteToColumn(int column) {
        Card card = talon.peekWaste();
        if (card == null) throw new IllegalStateException("The waste is empty.");

        maneuver.getColumn(column).add(card);
        talon.removeFromWaste();
    }

    public void moveManeuverToFoundation(int column) {
        ManeuverPile source = maneuver.getColumn(column);
        Card card = source.peekTop();
        if (card == null) throw new IllegalStateException("Column " + column + " is empty.");

        foundations.get(card.suit()).add(card);
        source.removeTop();

        if (source.hasHiddenCards() && source.hasNoVisibleCards()) {
            source.flipTop();
        }
    }

    public void moveManeuverToManeuver(int from, int to, int count) {
        maneuver.moveCards(from, to, count);
    }

    public void drawTalon()    { talon.drawCards(); }
    public void restackTalon() { talon.restack(); }

    public boolean isGameOver() {
        for (Map.Entry<Suit, Foundation> foundation : foundations.entrySet()) {
            if (foundation.getValue().size() != Constants.CARD_PER_SUIT) {
                return false;
            }
        }
        return true;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append(String.format("%n%-13s", "Stock"));
        stringBuilder.append(String.format("%-20s", "Waste"));
        stringBuilder.append("Foundation");

        stringBuilder.append(String.format("%n%-13s", talon.stockString()));
        stringBuilder.append(String.format("%-20s", talon.wasteString()));

        for (Map.Entry<Suit, Foundation> entry : foundations.entrySet()) {
            stringBuilder.append(entry.getValue());
        }

        stringBuilder.append("\n\n---------------------------------------------------------\n");
        stringBuilder.append(maneuver);

        return stringBuilder.toString();
    }
}

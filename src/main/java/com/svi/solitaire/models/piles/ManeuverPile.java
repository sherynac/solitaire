package com.svi.solitaire.models.piles;

import com.svi.solitaire.enums.Rank;
import com.svi.solitaire.models.Card;

import java.util.*;

public class ManeuverPile implements Pile {
    private final Deque<Card> hiddenCards;
    private final Deque<Card> visibleCards;

    public ManeuverPile() {
        hiddenCards = new ArrayDeque<>();
        visibleCards = new ArrayDeque<>();
    }

    public void addHidden(Card card) {
        hiddenCards.addLast(card);
    }

    public boolean hasNoVisibleCards() {
        return visibleCards.isEmpty();
    }

    public boolean hasHiddenCards() {
        return !hiddenCards.isEmpty();
    }

    public void flipTop() {
        Card card = hiddenCards.peekLast();
        System.out.println("FLIPPED " + card);

        hiddenCards.removeLast();
        visibleCards.addFirst(card);
    }

    @Override
    public int size() {
        return hiddenCards.size() + visibleCards.size();
    }

    @Override
    public Card peekTop() {
        return visibleCards.peekLast();
    }

    @Override
    public void add(Card card) {
        visibleCards.add(card);
    }

    public void add(List<Card> cards) {
        visibleCards.addAll(cards);
    }

    @Override
    public Card removeTop() {
        return visibleCards.removeLast();
    }

    public int visibleCount() {
        return visibleCards.size();
    }

    public int hiddenCount() {
        return hiddenCards.size();
    }

    public boolean isEmpty() {
        return (hiddenCards.isEmpty() && visibleCards.isEmpty());
    }

    /**
     * n-th visible card from the top (0 = top card), or null if there is none.
     */
    public Card cardFromTop(int n) {
        if (n < 0 || n >= visibleCards.size()) {
            return null;
        }

        List<Card> visibleList = new ArrayList<>(visibleCards);

        return visibleList.get(visibleCount() - 1 - n);
    }

    public String getDisplayCardAt(int index) {
        if (index < 0 || index >= size()) {
            throw new IndexOutOfBoundsException("Invalid card index: " + index);
        }

        if (index < hiddenCards.size()) {
            return " ?";
        }

        int visibleIndex = index - hiddenCards.size();

        Iterator<Card> iterator = visibleCards.iterator();

        for (int i = 0; i < visibleIndex; i++) {
            iterator.next();
        }

        return iterator.next().toString();
    }

    public boolean canAccept(Card card) {
        if (card == null) {
            return false;
        }

        if (visibleCards.isEmpty()) {
            return card.rank() == Rank.KING;
        }

        Card exposedCard = visibleCards.peekLast();

        return card.isOneLowerThan(exposedCard);
    }

    public static class Maneuver {

        private List<ManeuverPile> columnList;

        public Maneuver(){
            columnList = new ArrayList<>();

            for (int i = 0 ; i < 7; i++){
                ManeuverPile tableauColumn = new ManeuverPile();
                columnList.add(tableauColumn);
            }
        }

        public Maneuver(List<ManeuverPile> columns){
            this.columnList = columns;
        }

        public ManeuverPile getColumn(int index) {
            return columnList.get(index);
        }

        public void addCard(int columnIndex, Card card) {
            columnList.get(columnIndex).addHidden(card);
        }

        public void addVisibleCard(int columnIndex, Card card) {
            columnList.get(columnIndex).add(card);
        }

        public void moveCards(int from, int to, int cardCount) {
            ManeuverPile fromPile = getColumn(from);
            ManeuverPile toPile = getColumn(to);

            List<Card> cardsToMove = new ArrayList<>();

            for (int counter = 0; counter < cardCount; counter++) {
                cardsToMove.add(fromPile.removeTop());   // top first
            }

            Collections.reverse(cardsToMove);            // now bottom to top: C-9, D-8, S-7

            toPile.add(cardsToMove);

            if (fromPile.hasNoVisibleCards() && !fromPile.isEmpty()) {
                fromPile.flipTop();
            }
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < columnList.size(); i++) {
                sb.append(String.format("%-8s", "[" + (i + 1) + "]"));
            }

            sb.append("\n\n");

            int maxHeight = 0;

            for (ManeuverPile col : columnList) {
                maxHeight = Math.max(maxHeight, col.size());
            }

            for (int row = 0; row < maxHeight; row++) {
                for (ManeuverPile col : columnList) {
                    if (row < col.size()) {
                        sb.append(String.format("%-8s", col.getDisplayCardAt(row)));
                    } else {
                        sb.append(String.format("%-8s", ""));
                    }
                }

                sb.append("\n");
            }

            return sb.toString();
        }
    }

    public static class Talon {
        private final int drawCount;
        private final Stock stock;
        private final Waste waste;

        public Talon(int drawCount, Stock stock) {
            this.drawCount = drawCount;
            this.stock = stock;
            waste = new Waste(drawCount);
        }

        private final Set<Card> seen = Collections.newSetFromMap(new IdentityHashMap<>());

        public void drawCards() {
            int drawn = 0;
            while (drawn < drawCount && !stock.isEmpty()) {
                Card card = stock.removeTop();
                seen.add(card);                              // remember it
                waste.add(card);
                drawn++;
            }
        }

        /**
         * Cards the player has already seen that sit under the waste top or are still in the stock,
         * in draw order. Stock cards that were never flipped are not included.
         */
        public List<Card> knownCardsBelowTop() {
            List<Card> known = new ArrayList<>(waste.peekTop(waste.size()));   // bottom to top
            if (!known.isEmpty()) known.removeLast();              // drop the waste top itself
            for (Card card : stock.topFirst()) {                               // next card to draw first
                if (seen.contains(card)) known.add(card);
            }
            return known;
        }

        public Card peekWaste() {
            return waste.peekTop();
        }

        public void removeFromWaste() {
            waste.removeTop();
        }

        public boolean canDraw() {
            return !stock.isEmpty();
        }

        public boolean canRestack() {
            return stock.isEmpty() && waste.size() > 1;
        }

        public int size() {
            return stock.size() + waste.size();
        }

        public void restack() {
            while (!waste.isEmpty()) {
                Card c = waste.removeTop();
                stock.add(c);
            }
        }

        public String wasteString() {
            return waste.toString();
        }

        public String stockString() {
            return stock.toString();
        }
    }
}

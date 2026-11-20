package com.svi.solitaire.models.piles;

import com.svi.solitaire.models.Card;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.stream.Collectors;

// TODO: can further break this down
public class TalonPile implements Pile {
    private int drawCount;
    private Deque<Card> stock;
    private Deque<Card> waste;

    public TalonPile(int drawCount, Deque<Card> stock){
        this.drawCount = drawCount;
        this.stock = stock;
        waste = new ArrayDeque<>();
    }

    @Override
    public void push(Card card) {

    }


    @Override
    public int size() {
        return 0;
    }

    @Override
    public void reset() {
        stock.clear();
        waste.clear();
    }

    public Card peekWaste() {
        return waste.peekLast();
    }

    public void removeFromWaste() {
        waste.removeLast();
    }

    public boolean canDraw() {
        return !stock.isEmpty();
    }

    public boolean canRestack() {
        return stock.isEmpty() && waste.size() > 1;
    }

    public void drawCards(){
        System.out.println("CARDS IN STOCK: ");
        for (Card card: stock) {
            System.out.print(card + ",");
        }

        for (int i = 0; i < drawCount; i++) {
            if (stock.isEmpty()) {
                return;
            }
            Card drawnCard = stock.removeFirst(); // D-A,S-A,D-5,C-2 => D-A
            waste.addLast(drawnCard); // D-A
        }
    }


    public void restack(){
        stock = new ArrayDeque<>(waste);
        waste.clear();
    }

    public String wasteString() {
        StringBuilder stringBuilder = new StringBuilder();
        if (waste.isEmpty()){
            stringBuilder.append("[]");
        } else {
            stringBuilder.append("[");
            stringBuilder.append(
                    waste.stream()
                            .skip(Math.max(0, waste.size() - drawCount))
                            .map(card -> {
                                return card.toString();
                            })
                            .collect(Collectors.joining(", ")));
            stringBuilder.append("]");
        }
        return stringBuilder.toString();
    }

    public String stockString() {
        return "[???] x" + stock.size();
    }

}

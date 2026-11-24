package com.svi.solitaire.models.piles;

import com.svi.solitaire.models.Card;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.stream.Collectors;
import java.util.List;

public class Waste extends DequePile {
    private final int visibleCount;

    public Waste(int visibleCount) {
        this.visibleCount = visibleCount;
    }

    public Waste(int visibleCount, Deque<Card> cardDeque) {
        super(cardDeque);
        this.visibleCount = visibleCount;
    }

    public Waste copy() {
        Deque<Card> cardDeque = new ArrayDeque<>(snapshot());
        return new Waste(visibleCount, cardDeque);
    }

    public List<Card> getTopCards(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("n must not be negative");
        }

        List<Card> all = snapshot();
        return List.copyOf(all.subList(Math.max(0, all.size() - n), all.size()));
    }

    @Override
    public String toString() {
        return getTopCards(visibleCount).stream()
                .map(Object::toString)
                .collect(Collectors.joining(", ", "[", "] x" + size()));
    }
}

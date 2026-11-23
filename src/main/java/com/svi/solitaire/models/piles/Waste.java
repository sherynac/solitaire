package com.svi.solitaire.models.piles;

import com.svi.solitaire.models.Card;

import java.util.stream.Collectors;
import java.util.List;

public class Waste extends DequePile {
    private final int visibleCount;

    public Waste(int visibleCount) {
        this.visibleCount = visibleCount;
    }

    public List<Card> peekTop(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("n must not be negative");
        }
        List<Card> all = snapshot();
        return List.copyOf(all.subList(Math.max(0, all.size() - n), all.size()));
    }

    @Override
    public String toString() {
        return peekTop(visibleCount).stream()
                .map(Object::toString)
                .collect(Collectors.joining(", ", "[", "] x" + size()));
    }
}

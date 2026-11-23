package com.svi.solitaire.shuffle;

public enum ShuffleType {
    RANDOM("Random Shuffle"),
    IN_FARO("Perfect Shuffle (In Faro)"),
    OUT_FARO("Perfect Shuffle (Out Faro)"),
    NO_SHUFFLE("No Shuffle");

    private final String label;

    ShuffleType(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
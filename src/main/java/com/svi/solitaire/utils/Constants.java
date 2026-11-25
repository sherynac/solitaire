package com.svi.solitaire.utils;

/**
 * Contains the constant values used in the Solitaire game
 */
public final class Constants {

    // Deck Constants
    public static final int DECK_START = 0;
    public static final int DECK_SIZE = 52;
    public static final int DECK_HALF = 26;
    public static final int CARD_PARTS = 2;
    public static final int CARD_PER_SUIT = 13;

    // Game Constants
    public static final int MANEUVER_COLUMNS = 7;
    public static final int MANEUVER_ROWS = 7;
    public static final int MAX_MOVES = 5000;
    public static final int MIN_GAMES = 1;
    public static final int MAX_GAMES = 1000;
    public static final int NONE = -1;

    // Shuffle Constants
    public static final int IN_FARO_CYCLE = 52;
    public static final int OUT_FARO_CYCLE = 8;
    public static final int MAX_RANDOM_SHUFFLES = 100;

    // Score Constants
    public static final int SAFE_FOUNDATION = 1000;
    public static final int REVEAL = 800;
    public static final int EMPTY_COLUMN = 600;
    public static final int TALON_TO_COLUMN = 200;
    public static final int TALON_TO_FOUNDATION = 100;
    public static final int USELESS_FOUNDATION = 20;
    public static final int DRAW_SCORE = 10;
    public static final int RESTACK_SCORE = 5;
    public static final int FOLLOW_UP_BONUS = 15;
}

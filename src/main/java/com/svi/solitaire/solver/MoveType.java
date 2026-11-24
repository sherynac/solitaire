package com.svi.solitaire.solver;

public enum MoveType {
    TALON_TO_FOUNDATION,
    TALON_TO_MANEUVER,
    MANEUVER_TO_FOUNDATION,
    MANEUVER_TO_MANEUVER,
    DRAW,
    RESTACK;

    public boolean isTalonCycle() {
        return this == DRAW || this == RESTACK;
    }

    public boolean isFromTalon() {
        return this == TALON_TO_FOUNDATION || this == TALON_TO_MANEUVER;
    }

    public boolean isToManeuver() {
        return this == TALON_TO_MANEUVER || this == MANEUVER_TO_MANEUVER;
    }
}

package com.svi.solitaire.enums;

/**
 * Utility for adding color to console display
 *
 * @author Sheryn Ann Cagulada
 * @version October 30, 2026
 */
public enum Color {
    RED("\u001B[31m"),
    GREEN("\u001B[32m"),
    YELLOW("\u001B[33m"),
    BLUE("\u001B[34m"),
    BLACK("\u001B[30m"),
    PURPLE("\u001B[35m"),
    YELLOW_BG("\u001B[43m"),
    GREEN_BG("\u001B[42m"),
    RESET("\u001B[0m");

    private String code;

    Color(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}

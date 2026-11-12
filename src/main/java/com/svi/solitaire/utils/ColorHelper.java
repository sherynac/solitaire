package com.svi.solitaire.utils;

import com.svi.solitaire.enums.Color;

public class ColorHelper {

    /**
     * Builds a string with the specified font and background colors.
     *
     * @param text the text to be colored
     * @param fontColor the font color to apply
     * @param bgColor the background color to apply
     * @return the formatted string with the specified colors
     */
    public static String color(String text, Color fontColor, Color bgColor) {
        StringBuilder result = new StringBuilder();

        if (bgColor != null) {
            result.append(bgColor.getCode());
        }

        if (fontColor != null) {
            result.append(fontColor.getCode());
        }

        return result.append(text)
                .append(Color.RESET.getCode())
                .toString();
    }

    /**
     * Builds a string with the specified font color.
     *
     * @param text the text to be colored
     * @param fontColor the font color to apply
     * @return the formatted string with the specified color
     */
    public static String color(String text, Color fontColor) {
        return color(text, fontColor, null);
    }
}

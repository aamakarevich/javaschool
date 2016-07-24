package com.tsystems.ecare.app.utils;

/**
 * Testing utility methods.
 */
public class TestUtils {

    private TestUtils() {
        // do not instantiate utility class
    }

    /**
     * Returns string of passed length filled with zero characters.
     *
     * @param length lengs of string
     * @return generated string
     */
    public static String getStringOfLength(int length) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append("0");
        }
        return sb.toString();
    }
}

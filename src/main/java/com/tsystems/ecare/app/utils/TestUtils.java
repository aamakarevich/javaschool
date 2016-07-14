package com.tsystems.ecare.app.utils;

import org.apache.commons.lang3.NotImplementedException;

/**
 * Testing utility methods.
 */
public class TestUtils {

    private TestUtils() {
        throw new NotImplementedException("Utility classes cannot be instantiated");
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

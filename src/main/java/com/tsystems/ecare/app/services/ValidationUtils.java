package com.tsystems.ecare.app.services;

import org.apache.commons.lang3.NotImplementedException;
import org.apache.commons.lang3.StringUtils;

import java.util.regex.Pattern;

/**
 * Validation utility methods.
 */
public final class ValidationUtils {

    private ValidationUtils() {
        throw new NotImplementedException("Utility classes cannot be instantiated");
    }

    /**
     * Asserts that value is not blank.
     *
     * @param value value to check
     * @param message message to show if assertion fails
     */
    public static void assertNotBlank(String value, String message) {
        if (StringUtils.isBlank(value)) {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * Asserts that value's length is not more than limit.
     *
     * @param value value to check
     * @param length limit
     * @param message message to show if assertion fails
     */
    public static void assertMaximumLength(String value, int length, String message) {
        if (value.length() > length) {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * Asserts that value's length is not less than limit.
     *
     * @param value value to check
     * @param length limit
     * @param message message to show if assertion fails
     */
    public static void assertMinimumLength(String value, int length, String message) {
        if (value.length() < length) {
            throw new IllegalArgumentException(message);
        }
    }

    /**
     * Asserts that value matches regular expression.
     *
     * @param value value to check
     * @param regex regular expression
     * @param message message to show if assertion fails
     */
    public static void assertMatches(String value, Pattern regex, String message) {
        if (!regex.matcher(value).matches()) {
            throw new IllegalArgumentException(message);
        }
    }
}

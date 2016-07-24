package com.tsystems.ecare.app.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

/**
 * Hashing utility methods.
 */
public class HashUtils {

    private HashUtils() {
        // do not instantiate utility class
    }

    /**
     * Creates SHA-256 hash of provided string.
     *
     * @param input string to get hash from
     * @return hash value
     */
    public static String sha256(String input) {
        MessageDigest mDigest = null;
        try {
            mDigest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException ex) {
            throw new IllegalArgumentException("unsupported encryption type", ex);
        }
        byte[] result = mDigest.digest(input.getBytes());
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < result.length; i++) {
            sb.append(Integer.toString((result[i] & 0xff) + 0x100, 16).substring(1));
        }
        return sb.toString();
    }

    /**
     * Generates fresh password.
     *
     * @return password consisting of 8 characters
     */
    public static String generatePassword() {
        return UUID.randomUUID().toString().substring(0, 8);
    }
}

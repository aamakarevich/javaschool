package com.tsystems.ecare.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author Andrei Makarevich
 */
public class HashUtil {

    private HashUtil() { /** Utility class */ }

    /**
     * Returns SHA-256 hash of string value.
     *
     * @param stringToHash string value to get hash from
     * @return string representation of SHA-256 hash
     */
    public static String getSHA256(String stringToHash) {

        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA-256");
            md.update(stringToHash.getBytes("UTF-8"));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        byte[] hashBytes = md.digest();

        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < hashBytes.length; i++) {
            sb.append(Integer.toString((hashBytes[i] & 0xff) + 0x100, 16).substring(1));
        }

        return sb.toString();
    }
}

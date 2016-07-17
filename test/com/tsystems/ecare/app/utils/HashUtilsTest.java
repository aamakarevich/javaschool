package com.tsystems.ecare.app.utils;

import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Test class for HashUtils.
 */
public class HashUtilsTest {

    /**
     * Tests if sha256 hashes are being generated properly.
     */
    @Test
    public void sha256() {
        String value1 = "abcdef";
        String value2 = "ghijkl";
        String value3 = "mnopqr";

        String realHashOfValue1 = "bef57ec7f53a6d40beb640a780a639c83bc29ac8a9816f1fc6c5c6dcd93c4721";
        String realHashOfValue2 = "54f6ee81b58accbc57adbceb0f50264897626060071dc9e92f897e7b373deb93";
        String realHashOfValue3 = "08aeae929315709506a7e5c09c8c8aefb491290b2552044daff777810070e340";

        String gotHashOfValue1 = HashUtils.sha256(value1);
        String gotHashOfValue2 = HashUtils.sha256(value2);
        String gotHashOfValue3 = HashUtils.sha256(value3);

        assertEquals(gotHashOfValue1, realHashOfValue1);
        assertEquals(gotHashOfValue2, realHashOfValue2);
        assertEquals(gotHashOfValue3, realHashOfValue3);
    }

    /**
     * Tests if all passwords in bench are different and have length of 8 characters.
     */
    @Test
    public void generatePassword() {
        Set<String> passwords = new HashSet<>();
        for (int i = 0; i < 10; i++) {
            String password = HashUtils.generatePassword();
            assertTrue(password.length() == 8);
            passwords.add(password);
        }
        assertTrue(passwords.size() == 10);
    }
}
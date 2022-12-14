package io.github.kylinhunter.commons.codec;

import javax.crypto.SecretKey;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class AESIncludeManagerTest {

    @Test
    void test() {
        AESKeyManager aesKeyManager = new AESKeyManager();

        String key = aesKeyManager.newKeyStr();
        SecretKey secretKey = aesKeyManager.toKey(key);
        String keyToString = aesKeyManager.keyToString(secretKey);
        Assertions.assertEquals(key, keyToString);

        SecretKey secretKey1 = aesKeyManager.newKey();
        String keyToString1 = aesKeyManager.keyToString(secretKey1);
        SecretKey secretKey2 = aesKeyManager.toKey(keyToString1);
        Assertions.assertArrayEquals(secretKey1.getEncoded(), secretKey2.getEncoded());

    }

}
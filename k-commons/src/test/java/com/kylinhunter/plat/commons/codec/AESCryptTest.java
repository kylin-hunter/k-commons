package com.kylinhunter.plat.commons.codec;

import static org.junit.jupiter.api.Assertions.assertEquals;

import javax.crypto.SecretKey;

import org.junit.jupiter.api.Test;

class AESCryptTest {

    AESCrypt aesCrypt = AESCrypt.getInstance();

    @Test
    void testKey() {
        SecretKey key = aesCrypt.generateKey();
        String keyStr = aesCrypt.stringKey(key);
        System.out.println("generateKey:" + keyStr);
        SecretKey keyRestore = aesCrypt.restoreKey(keyStr);
        String keyRestoreStr = aesCrypt.stringKey(keyRestore);
        System.out.println("keyRestoreStr:" + keyRestoreStr);
        assertEquals(keyStr, keyRestoreStr);
    }

    @Test
    void testEnDe() {
        SecretKey key = aesCrypt.generateKey("123");
        String keyStr = aesCrypt.stringKey(key);
        System.out.println("测试秘钥:" + keyStr);
        String oriText = "你好";
        System.out.println("oriText:" + oriText);

        String encrptText1 = aesCrypt.encrypt(oriText, keyStr);
        System.out.println("encrptText:" + encrptText1);

        String encrptText2 = aesCrypt.encrypt(oriText, keyStr);
        System.out.println("encrptText:" + encrptText2);

        String decryptText1 = aesCrypt.decrypt(encrptText1, keyStr);
        System.out.println("decryptText:" + decryptText1);

        String decryptText2 = aesCrypt.decrypt(encrptText2, keyStr);
        System.out.println("decryptText:" + decryptText2);

        assertEquals(oriText, decryptText1);
        assertEquals(oriText, decryptText2);

    }

    @Test
    void testEnDeDefault() {

        System.out.println("默认秘钥:" + aesCrypt.stringKey(aesCrypt.getDefaultKey()));
        String oriText = "你好啊测试默认秘钥";
        System.out.println("oriText:" + oriText);

        String encrptText1 = aesCrypt.encrypt(oriText);
        System.out.println("encrptText:" + encrptText1);

        String encrptText2 = aesCrypt.encrypt(oriText);
        System.out.println("encrptText:" + encrptText2);

        String decryptText1 = aesCrypt.decrypt(encrptText1);
        System.out.println("decryptText:" + decryptText1);

        String decryptText2 = aesCrypt.decrypt(encrptText2);
        System.out.println("decryptText:" + decryptText2);

        assertEquals(oriText, decryptText1);
        assertEquals(oriText, decryptText2);

    }

}

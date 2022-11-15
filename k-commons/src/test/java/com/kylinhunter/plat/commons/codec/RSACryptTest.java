package com.kylinhunter.plat.commons.codec;

import java.security.KeyPair;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class RSACryptTest {
    private final RSACrypt rsaCrypt = RSACrypt.getInstance();
    String oriText =
            "referrer:[http://www.example.com/start.html](http://www.example.com/start.htmluser_agent: Mozilla/4"
                    + ".08 [en] (Win98; I ;Nav)";

    @Test
    public void testDefault() throws Exception {
        // 使用公钥、私钥对象加解密

        String encryptText = rsaCrypt.encrypt(oriText);
        System.out.println("encryptText:" + encryptText);
        String decryptText = rsaCrypt.decrypt(encryptText);
        Assertions.assertEquals(decryptText, oriText);
    }

    @Test
    public void testKey() throws Exception {
        // 使用公钥、私钥对象加解密
        KeyPair keyPair = rsaCrypt.generateKeyPair();

        String encryptText = rsaCrypt.encryptPub(oriText, (RSAPublicKey) keyPair.getPublic());
        System.out.println("encryptText:" + encryptText);
        String decryptText = rsaCrypt.decryptPrivate(encryptText, (RSAPrivateKey) keyPair.getPrivate());
        Assertions.assertEquals(decryptText, oriText);
    }

    @Test
    public void testKeyStr() {
        // 使用公钥、私钥对象加解密
        RSACrypt.RSAKeyPair rsaKeyPair = rsaCrypt.generateRSAKeyPair();
        String publicKey = rsaKeyPair.getPublicKey();
        String privateKey = rsaKeyPair.getPrivateKey();

        String encryptText = rsaCrypt.encryptPub(oriText, publicKey);
        System.out.println("第1次公钥加密:encryptText:" + encryptText);

        encryptText = rsaCrypt.encryptPrivate(encryptText, privateKey);
        System.out.println("第2次私钥加密: encryptText:" + encryptText);

        String decryptText = rsaCrypt.decryptPub(encryptText, publicKey);
        System.out.println("第2次公钥解密: decryptText:" + decryptText);

        decryptText = rsaCrypt.decryptPrivate(decryptText, privateKey);
        System.out.println("第1次私钥解密: decryptText:" + decryptText);

        Assertions.assertEquals(decryptText, oriText);
    }

    @Test
    public void testDefault1() throws Exception {
        // 使用公钥、私钥对象加解密
        final KeyPair keyPair = rsaCrypt.generateKeyPair();
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
        String message =
                "referrer:[http://www.example.com/start.html](http://www.example.com/start.htmluser_agent: Mozilla/4"
                        + ".08 [en] (Win98; I ;Nav)";
        String encryptedMsg = rsaCrypt.encryptPub(message, publicKey);
        String decryptedMsg = rsaCrypt.decryptPrivate(encryptedMsg, privateKey);
        System.out.println("object key ! oriText ==  decryptedMsg ? " + message.equals(decryptedMsg));

        // 使用字符串生成公钥、私钥完成加解密
        final RSACrypt.RSAKeyPair rsaKeyPair = rsaCrypt.generateRSAKeyPair();

        System.out.println("公钥:" + rsaKeyPair.getPublicKey());
        System.out.println("私钥:" + rsaKeyPair.getPrivateKey());
        // 生成公钥、私钥
        publicKey = rsaCrypt.restorePublicKey(rsaKeyPair.getPublicKey());
        privateKey = rsaCrypt.restorePrivateKey(rsaKeyPair.getPrivateKey());
        encryptedMsg = rsaCrypt.encryptPub(message, publicKey);
        decryptedMsg = rsaCrypt.decryptPrivate(encryptedMsg, privateKey);
        System.out.println("string key ! oriText ==  decryptedMsg ? " + message.equals(decryptedMsg));

    }

}
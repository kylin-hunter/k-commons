package io.github.kylinhunter.commons.codec;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class RSAUtilsTest {
    String oriText =
            "referrer:[http://www.example.com/start.html](http://www.example.com/start.htmluser_agent: Mozilla/4"
                    + ".08 [en] (Win98; I ;Nav)";

    @Test
    public void testDefault() {
        String encryptText = RSAUtils.encrypt(oriText);
        System.out.println("encryptText:" + encryptText);
        String decryptText = RSAUtils.decrypt(encryptText);
        Assertions.assertEquals(decryptText, oriText);
    }

    @Test
    public void test1() {
        // 使用公钥、私钥对象加解密
        RSAKeyManager rsaKeyManager = RSAUtils.getRsaKeyManager();

        RSAKeyManager.RSAKeyPair rsaKeyPair = rsaKeyManager.newKeyPair();
        String publicKey = rsaKeyPair.getPublicKey();
        String privateKey = rsaKeyPair.getPrivateKey();

        String encryptText = RSAUtils.encryptPub(oriText, publicKey);
        System.out.println("第1次公钥加密:encryptText:" + encryptText);

        encryptText = RSAUtils.encryptPrivate(encryptText, privateKey);
        System.out.println("第2次私钥加密: encryptText:" + encryptText);

        String decryptText = RSAUtils.decryptPub(encryptText, publicKey);
        System.out.println("第2次公钥解密: decryptText:" + decryptText);

        decryptText = RSAUtils.decryptPrivate(decryptText, privateKey);
        System.out.println("第1次私钥解密: decryptText:" + decryptText);

        Assertions.assertEquals(decryptText, oriText);
    }

    @Test
    public void test2() {
        RSAKeyManager rsaKeyManager = RSAUtils.getRsaKeyManager();

        RSAKeyManager.RSAKeyPair rsaKeyPair = rsaKeyManager.newKeyPair();

        System.out.println("公钥:" + rsaKeyPair.getPublicKey());
        System.out.println("私钥:" + rsaKeyPair.getPrivateKey());
        RSAPublicKey publicKey = rsaKeyManager.toPublicKey(rsaKeyPair.getPublicKey());
        RSAPrivateKey privateKey = rsaKeyManager.toPrivateKey(rsaKeyPair.getPrivateKey());
        String encryptedMsg = RSAUtils.encryptPub(oriText, publicKey);
        String decryptedMsg = RSAUtils.decryptPrivate(encryptedMsg, privateKey);
        Assertions.assertEquals(oriText, decryptedMsg);

    }

}
package com.kylinhunter.plat.commons.codec;

import static org.junit.jupiter.api.Assertions.assertEquals;

import javax.crypto.SecretKey;

class TestEncryptionAES {

    public static void main(String[] args) {

        AESCrypt aesCrypt = AESCrypt.getInstance();

        SecretKey key = aesCrypt.generateKey();
        String keyStr = aesCrypt.stringKey(key);
        System.out.println("generateKey:" + keyStr);

        System.out.println("默认秘钥:" + aesCrypt.stringKey(aesCrypt.getDefaultKey()));
        String oriText = "你好";
        System.out.println("oriText:" + oriText);

        String encrptText = aesCrypt.encrypt(oriText);

        System.out.println("期待密文:" + "hiRgGVHrVSRCGHXFVXlyzw==");
        System.out.println("期待密文:" + "LgMIIOXgBRLWpZo2CdQmZQ==");

        System.out.println("实际密文:" + encrptText);
//        assertEquals("LgMIIOXgBRLWpZo2CdQmZQ==", encrptText);

        String decryptText = aesCrypt.decrypt(encrptText);
        System.out.println("decryptText:" + decryptText);

        assertEquals(oriText, decryptText.trim());

    }

}

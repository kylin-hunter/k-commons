package io.github.kylinhunter.commons.codec;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class RSAKeyManagerTest {
    @Test
    void test() {
        RSAKeyManager rsaKeyManager = new RSAKeyManager();
        RSAKeyManager.RSAKeyPair rsaKeyPair = rsaKeyManager.newKeyPair();
        String publicKey = rsaKeyPair.getPublicKey();
        String privateKey = rsaKeyPair.getPrivateKey();

        RSAPrivateKey rsaPrivateKey = rsaKeyManager.toPrivateKey(privateKey);
        RSAPublicKey rsaPublicKey = rsaKeyManager.toPublicKey(publicKey);
        Assertions.assertArrayEquals(rsaKeyPair.getKeyPair().getPublic().getEncoded(), rsaPublicKey.getEncoded());
        Assertions.assertArrayEquals(rsaKeyPair.getKeyPair().getPrivate().getEncoded(), rsaPrivateKey.getEncoded());

    }

}
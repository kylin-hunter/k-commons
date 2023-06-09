package o.github.kylinhunter.commons.utils.codec;

import javax.crypto.SecretKey;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class AESKeyManagerTest {

  @Test
  void testECB() {
    AESKeyManager aesKeyManager = new AESKeyManager(CipherManager.CodecType.AES_ECB_NOPADDING);

    String key = aesKeyManager.newKeyStr();
    SecretKey secretKey = aesKeyManager.toKey(key);
    String keyToString = aesKeyManager.keyToString(secretKey);
    Assertions.assertEquals(key, keyToString);

    SecretKey secretKey1 = aesKeyManager.newKey();
    String keyToString1 = aesKeyManager.keyToString(secretKey1);
    SecretKey secretKey2 = aesKeyManager.toKey(keyToString1);
    Assertions.assertArrayEquals(secretKey1.getEncoded(), secretKey2.getEncoded());
  }

  @Test
  void testGCM() {
    AESKeyManager aesKeyManager = new AESKeyManager(CipherManager.CodecType.AES_GCM_NOPADDING);

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

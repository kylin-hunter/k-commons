package io.github.kylinhunter.commons.codec;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

class AESUtilsTest {

  @Test
  @Order(1)
  void testNew() {
    String newKey = AESUtils.getKeyManager().newKeyStr();

    System.out.println("测试秘钥:" + newKey);
    String oriText = "你好";
    System.out.println("oriText:" + oriText);

    String encrptText1 = AESUtils.encrypt(oriText, newKey);
    System.out.println("encrptText:" + encrptText1);

    String encrptText2 = AESUtils.encrypt(oriText, newKey);
    System.out.println("encrptText:" + encrptText2);

    String decryptText1 = AESUtils.decrypt(encrptText1, newKey);
    System.out.println("decryptText:" + decryptText1);

    String decryptText2 = AESUtils.decrypt(encrptText2, newKey);
    System.out.println("decryptText:" + decryptText2);

    assertEquals(oriText, decryptText1);
    assertEquals(oriText, decryptText2);
  }

  @Test
  @Order(2)
  void testNewGCM() {
    AESUtils.reInit(CipherManager.CodecType.AES_GCM_NOPADDING);
    String newKey = AESUtils.getKeyManager().newKeyStr();

    System.out.println("测试秘钥:" + newKey);
    String oriText = "你好";
    System.out.println("oriText:" + oriText);

    String encrptText1 = AESUtils.encrypt(oriText, newKey);
    System.out.println("encrptText:" + encrptText1);

    String encrptText2 = AESUtils.encrypt(oriText, newKey);
    System.out.println("encrptText:" + encrptText2);

    String decryptText1 = AESUtils.decrypt(encrptText1, newKey);
    System.out.println("decryptText:" + decryptText1);

    String decryptText2 = AESUtils.decrypt(encrptText2, newKey);
    System.out.println("decryptText:" + decryptText2);

    assertEquals(oriText, decryptText1);
    assertEquals(oriText, decryptText2);
  }

  @Test
  @Order(3)
  void testDefault() {
    AESUtils.reInit(CipherManager.CodecType.AES_ECB_NOPADDING);

    System.out.println("默认秘钥:" + AESUtils.getKeyManager().getDefaultKeyStr());
    String oriText = "你好啊测试默认秘钥";
    System.out.println("oriText:" + oriText);

    String encrptText1 = AESUtils.encrypt(oriText);
    System.out.println("encrptText:" + encrptText1);

    String encrptText2 = AESUtils.encrypt(oriText);
    System.out.println("encrptText:" + encrptText2);

    String decryptText1 = AESUtils.decrypt(encrptText1);
    System.out.println("decryptText:" + decryptText1);

    String decryptText2 = AESUtils.decrypt(encrptText2);
    System.out.println("decryptText:" + decryptText2);

    assertEquals(oriText, decryptText1);
    assertEquals(oriText, decryptText2);
  }

  @Test
  @Order(4)
  void testDefaultGCM() {
    AESUtils.reInit(CipherManager.CodecType.AES_GCM_NOPADDING);
    System.out.println("默认秘钥:" + AESUtils.getKeyManager().getDefaultKeyStr());
    String oriText = "你好啊测试默认秘钥";
    System.out.println("oriText:" + oriText);

    String encrptText1 = AESUtils.encrypt(oriText);
    System.out.println("encrptText:" + encrptText1);

    String encrptText2 = AESUtils.encrypt(oriText);
    System.out.println("encrptText:" + encrptText2);

    String decryptText1 = AESUtils.decrypt(encrptText1);
    System.out.println("decryptText:" + decryptText1);

    String decryptText2 = AESUtils.decrypt(encrptText2);
    System.out.println("decryptText:" + decryptText2);

    assertEquals(oriText, decryptText1);
    assertEquals(oriText, decryptText2);
  }
}

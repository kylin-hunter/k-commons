/*
 * Copyright (C) 2023 The k-commons Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.github.kylinhunter.commons.utils.codec;

import io.github.kylinhunter.commons.exception.embed.CryptException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.SecureRandom;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.spec.GCMParameterSpec;
import lombok.Getter;

/**
 * @author BiJi'an
 * @description
 * @date 2022/11/20
 */
public class AESUtils {

  private static CipherManager.CodecType CODEC_TYPE = CipherManager.CodecType.AES_ECB_NOPADDING;

  private static CipherManager cipherManager = new CipherManager(CODEC_TYPE);
  @Getter
  private static AESKeyManager keyManager = new AESKeyManager(CODEC_TYPE);

  private static final SecureRandom secureRandom = new SecureRandom();
  private static final int IV_LENGTH_BYTE = 12;
  private static final int TAG_LENGTH_BIT = 128;

  /**
   * @param codecType codecType
   * @return void
   * @title resetCodecType
   * @description
   * @author BiJi'an
   * @date 2023-04-22 00:20
   */
  public static void reInit(CipherManager.CodecType codecType) {
    CODEC_TYPE = codecType;
    cipherManager = new CipherManager(codecType);
    keyManager = new AESKeyManager(codecType);
  }

  /**
   * @param text text
   * @return java.lang.String
   * @title encrypt
   * @description
   * @author BiJi'an
   * @date 2022-11-20 17:15
   */
  public static String encrypt(String text) {
    return encrypt(text, cipherManager.getEnCipher(), keyManager.getDefaultKey());
  }

  /**
   * @param text   text
   * @param keyStr keyStr
   * @return java.lang.String
   * @title encrypt
   * @description
   * @author BiJi'an
   * @date 2022-11-20 17:15
   */
  public static String encrypt(String text, String keyStr) {
    return encrypt(text, cipherManager.getEnCipher(), keyManager.toKey(keyStr));
  }

  /**
   * @param text   text
   * @param cipher cipher
   * @return java.lang.String
   * @title encrypt
   * @description
   * @author BiJi'an
   * @date 2022-11-20 17:16
   */
  private static String encrypt(String text, Cipher cipher, Key key) {

    try {
      byte[] textBytes = text.getBytes(StandardCharsets.UTF_8);
      byte[] decrptTextBytes;
      if (CODEC_TYPE == CipherManager.CodecType.AES_GCM_NOPADDING) {
        byte[] iv = new byte[IV_LENGTH_BYTE];
        secureRandom.nextBytes(iv);
        cipher.init(Cipher.ENCRYPT_MODE, key, new GCMParameterSpec(TAG_LENGTH_BIT, iv));

        byte[] encryptBytes = cipher.doFinal(textBytes);

        ByteBuffer byteBuffer = ByteBuffer.allocate(Short.BYTES + iv.length + encryptBytes.length);
        byteBuffer.putShort((short) iv.length);
        byteBuffer.put(iv);
        byteBuffer.put(encryptBytes);

        decrptTextBytes = byteBuffer.array();

      } else {
        cipher.init(Cipher.ENCRYPT_MODE, key);

        int length = textBytes.length;
        int blockSize = cipher.getBlockSize();
        if (length % blockSize != 0) {
          length = length + (blockSize - (length % blockSize));
        }
        byte[] plaintext = new byte[length];
        System.arraycopy(textBytes, 0, plaintext, 0, textBytes.length);
        decrptTextBytes = cipher.doFinal(plaintext);
      }
      byte[] base64Bytes = Base64.getEncoder().encode(decrptTextBytes);
      return new String(base64Bytes, StandardCharsets.UTF_8);

    } catch (Exception e) {
      throw new CryptException("encrypt error", e);
    }
  }

  /**
   * @param text text
   * @return java.lang.String
   * @title decrypt
   * @description
   * @author BiJi'an
   * @date 2022-11-20 17:16
   */
  public static String decrypt(String text) {
    return decrypt(text, cipherManager.getDeCipher(), keyManager.getDefaultKey());
  }

  /**
   * @param text   text
   * @param keyStr keyStr
   * @return java.lang.String
   * @title decrypt
   * @description
   * @author BiJi'an
   * @date 2022-11-20 17:16
   */
  public static String decrypt(String text, String keyStr) {
    return decrypt(text, cipherManager.getDeCipher(), keyManager.toKey(keyStr));
  }

  /**
   * @param decryptText decryptText
   * @param cipher      cipher
   * @return java.lang.String
   * @title decrypt
   * @description
   * @author BiJi'an
   * @date 2022-11-20 17:16
   */
  private static String decrypt(String decryptText, Cipher cipher, Key key) {

    try {
      byte[] decryptTextBytes = decryptText.getBytes(StandardCharsets.UTF_8);
      byte[] decryptTextRaw = Base64.getDecoder().decode(decryptTextBytes);

      if (CODEC_TYPE == CipherManager.CodecType.AES_GCM_NOPADDING) {

        ByteBuffer byteBuffer = ByteBuffer.wrap(decryptTextRaw);
        int ivLen = byteBuffer.getShort();
        byte[] iv = new byte[ivLen];
        byteBuffer.get(iv);
        byte[] encrypted = new byte[byteBuffer.remaining()];
        byteBuffer.get(encrypted);

        cipher.init(Cipher.DECRYPT_MODE, key, new GCMParameterSpec(TAG_LENGTH_BIT, iv));
        byte[] encryptTextBytes = cipher.doFinal(encrypted);
        return new String(encryptTextBytes, StandardCharsets.UTF_8);

      } else {
        cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] encryptTextBytes = cipher.doFinal(decryptTextRaw);
        return new String(encryptTextBytes, StandardCharsets.UTF_8).trim();
      }

    } catch (Exception e) {
      throw new CryptException("encrypt error:" + e.getMessage(), e);
    }
  }
}

package io.github.kylinhunter.commons.codec;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

import javax.crypto.Cipher;

import io.github.kylinhunter.commons.exception.builtin.CryptException;
import lombok.Getter;

/**
 * @author BiJi'an
 * @description
 * @date 2022/11/20
 **/

public class AESUtils {
    private static final CipherManager.CodecType CODE_TYPE = CipherManager.CodecType.AES;

    private static final CipherManager cipherManager = new CipherManager(CODE_TYPE);
    @Getter
    private static final AESKeyManager keyManager = new AESKeyManager();

    /**
     * @param text text
     * @return java.lang.String
     * @title encrypt
     * @description
     * @author BiJi'an
     * @date 2022-11-20 17:15
     */
    public static String encrypt(String text) {
        return encrypt(text, cipherManager.getEnCipher(keyManager.getDefaultKey()));
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
        return encrypt(text, cipherManager.getEnCipher(keyManager.toKey(keyStr)));
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
    private static String encrypt(String text, Cipher cipher) {

        try {
            byte[] textBytes = text.getBytes(StandardCharsets.UTF_8);
            int length = textBytes.length;
            int blockSize = cipher.getBlockSize();

            if (length % blockSize != 0) {
                length = length + (blockSize - (length % blockSize));
            }
            byte[] plaintext = new byte[length];

            System.arraycopy(textBytes, 0, plaintext, 0, textBytes.length);

            byte[] decrptText = cipher.doFinal(plaintext);
            byte[] base64Bytes = Base64.getEncoder().encode(decrptText);

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
        return decrypt(text, cipherManager.getDeCipher(keyManager.getDefaultKey()));
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
        return decrypt(text, cipherManager.getDeCipher(keyManager.toKey(keyStr)));
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
    private static String decrypt(String decryptText, Cipher cipher) {

        try {

            byte[] decryptTextBytes = decryptText.getBytes(StandardCharsets.UTF_8);

            byte[] decryptTextRaw = Base64.getDecoder().decode(decryptTextBytes);

            byte[] encryptTextBytes = cipher.doFinal(decryptTextRaw);

            String result = new String(encryptTextBytes, StandardCharsets.UTF_8);

            return result.trim();
        } catch (Exception e) {
            throw new CryptException("encrypt error:" + e.getMessage(), e);

        }

    }

}

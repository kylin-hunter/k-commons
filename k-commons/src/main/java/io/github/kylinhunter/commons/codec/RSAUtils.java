package io.github.kylinhunter.commons.codec;

import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.security.interfaces.RSAKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Base64;

import javax.crypto.Cipher;

import io.github.kylinhunter.commons.exception.inner.CryptException;
import lombok.Getter;

/**
 * @author BiJi'an
 * @description
 * @date 2022/11/20
 **/
public class RSAUtils {
    @Getter
    private static final RSAKeyManager rsaKeyManager = new RSAKeyManager();

    private static final CipherManager cipherManager = new CipherManager(CipherManager.CodecType.RSA);

    public static String encrypt(String data) {
        RSAPrivateKey defaultPrivateKey = rsaKeyManager.getDefaultPrivateKey();
        Cipher cipher = cipherManager.getEnCipher(defaultPrivateKey);
        return encrypt(data, cipher, defaultPrivateKey);

    }

    public static String encryptPub(String data, String publicKey) {
        return encryptPub(data, rsaKeyManager.toPublicKey(publicKey));
    }

    public static String encryptPub(String data, RSAPublicKey publicKey) {
        Cipher cipher = cipherManager.getEnCipher(publicKey);
        return encrypt(data, cipher, publicKey);
    }

    public static String encryptPrivate(String data, String privateKey) {
        return encryptPrivate(data, rsaKeyManager.toPrivateKey(privateKey));
    }

    public static String encryptPrivate(String data, RSAPrivateKey rasPriateKey) {
        Cipher cipher = cipherManager.getEnCipher(rasPriateKey);
        return encrypt(data, cipher, rasPriateKey);
    }

    /**
     * @param data   data
     * @param cipher cipher
     * @param rsaKey rsaKey
     * @return java.lang.String
     * @title encrypt
     * @description
     * @author BiJi'an
     * @date 2022-11-20 21:47
     */
    private static String encrypt(String data, Cipher cipher, RSAKey rsaKey) {

        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            int modulusSize = rsaKey.getModulus().bitLength() / 8;
            int maxSingleSize = modulusSize - 11;
            byte[][] dataArray = splitArray(data.getBytes(), maxSingleSize);

            for (byte[] s : dataArray) {
                out.write(cipher.doFinal(s));
            }
            return Base64.getEncoder().encodeToString(out.toByteArray());
        } catch (Exception e) {
            throw new CryptException("encrypt error", e);
        }
    }

    /**
     * @param data data
     * @return java.lang.String
     * @title decrypt
     * @description
     * @author BiJi'an
     * @date 2022-11-20 23:37
     */
    public static String decrypt(String data) {
        RSAPublicKey defaultPubKey = rsaKeyManager.getDefaultPubKey();
        Cipher cipher = cipherManager.getDeCipher(defaultPubKey);
        return decrypt(data, cipher, defaultPubKey);
    }

    /**
     * @param data      data
     * @param publicKey publicKey
     * @return java.lang.String
     * @title decryptPub
     * @description
     * @author BiJi'an
     * @date 2022-11-20 23:37
     */
    public static String decryptPub(String data, String publicKey) {
        return decryptPub(data, rsaKeyManager.toPublicKey(publicKey));
    }

    /**
     * @param data         data
     * @param rsaPublicKey rsaPublicKey
     * @return java.lang.String
     * @title decryptPub
     * @description
     * @author BiJi'an
     * @date 2022-11-20 23:37
     */
    public static String decryptPub(String data, RSAPublicKey rsaPublicKey) {
        Cipher cipher = cipherManager.getDeCipher(rsaPublicKey);
        return decrypt(data, cipher, rsaPublicKey);
    }

    /**
     * @param data       data
     * @param privateKey privateKey
     * @return java.lang.String
     * @title decryptPrivate
     * @description
     * @author BiJi'an
     * @date 2022-11-20 23:39
     */
    public static String decryptPrivate(String data, String privateKey) {
        return decryptPrivate(data, rsaKeyManager.toPrivateKey(privateKey));
    }

    /**
     * @param data         data
     * @param rsaPrivateKe rsaPrivateKe
     * @return java.lang.String
     * @title decryptPrivate
     * @description
     * @author BiJi'an
     * @date 2022-11-20 23:39
     */
    public static String decryptPrivate(String data, RSAPrivateKey rsaPrivateKe) {
        Cipher cipher = cipherManager.getDeCipher(rsaPrivateKe);
        return decrypt(data, cipher, rsaPrivateKe);
    }

    /**
     * @param data   data
     * @param cipher cipher
     * @param rsaKey rsaKey
     * @return java.lang.String
     * @title decrypt
     * @description
     * @author BiJi'an
     * @date 2022-11-20 23:39
     */
    private static String decrypt(String data, Cipher cipher, RSAKey rsaKey) {

        try {
            int modulusSize = rsaKey.getModulus().bitLength() / 8;
            byte[] dataBytes = data.getBytes();
            byte[] decodeData = Base64.getDecoder().decode(dataBytes);
            byte[][] splitArrays = splitArray(decodeData, modulusSize);
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            for (byte[] arr : splitArrays) {
                out.write(cipher.doFinal(arr));
            }
            return new String(out.toByteArray(), StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new CryptException("encrypt error", e);
        }
    }

    /**
     * @param data data
     * @param len  len
     * @return byte[][]
     * @title splitArray
     * @description
     * @author BiJi'an
     * @date 2022-11-20 23:40
     */
    private static byte[][] splitArray(byte[] data, int len) {

        int dataLen = data.length;
        if (dataLen <= len) {
            return new byte[][] {data};
        }
        byte[][] result = new byte[(dataLen - 1) / len + 1][];
        int resultLen = result.length;
        for (int i = 0; i < resultLen; i++) {
            if (i == resultLen - 1) {
                int slen = dataLen - len * i;
                byte[] single = new byte[slen];
                System.arraycopy(data, len * i, single, 0, slen);
                result[i] = single;
                break;
            }
            byte[] single = new byte[len];
            System.arraycopy(data, len * i, single, 0, len);
            result[i] = single;
        }
        return result;
    }

}

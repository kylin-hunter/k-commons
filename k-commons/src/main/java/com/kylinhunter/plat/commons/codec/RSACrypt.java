package com.kylinhunter.plat.commons.codec;

import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

import javax.crypto.Cipher;

import com.kylinhunter.plat.commons.exception.inner.CryptException;
import com.kylinhunter.plat.commons.exception.inner.InitException;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * RSA 算法
 *
 * @author bijian
 */
// @Slf4j
public class RSACrypt {
    private static final CodecType CODE_TYPE = CodecType.RSA;
    private final static String ALGORITHM_RSA = "RSA";
    private final static RSACrypt singleton = new RSACrypt();
    private static final int DEFAULT_KEY_SIZE = 1024;

    private final RSAPrivateKey defaultEncryptKey;
    private final RSAPublicKey defaultDecryptKey;
    private final CipherManager cipherManager;

    public static RSACrypt getInstance() {
        return singleton;
    }

    private RSACrypt() {
        KeyPair keyPair = this.generateKeyPair();
        this.defaultEncryptKey = (RSAPrivateKey) keyPair.getPrivate();
        this.defaultDecryptKey = (RSAPublicKey) keyPair.getPublic();
        this.cipherManager = new CipherManager(CODE_TYPE, this.defaultEncryptKey, this.defaultDecryptKey);
    }

    public RSACrypt(String privateKey, String publicKey) {
        this.defaultEncryptKey = this.restorePrivateKey(privateKey);
        this.defaultDecryptKey = this.restorePublicKey(publicKey);
        this.cipherManager = new CipherManager(CODE_TYPE, this.defaultEncryptKey, this.defaultDecryptKey);

    }

    /**
     * @param modulus modulus
     * @return java.security.KeyPair
     * @title generateKeyPair
     * @description
     * @author BiJi'an
     * @date 2022-06-22 01:07
     */
    public KeyPair generateKeyPair(int modulus) {
        try {
            KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(ALGORITHM_RSA);
            keyPairGen.initialize(modulus);
            return keyPairGen.generateKeyPair();
        } catch (NoSuchAlgorithmException e) {
            throw new InitException("no ras algorithm ", e);
        }

    }

    public KeyPair generateKeyPair() {
        return this.generateKeyPair(DEFAULT_KEY_SIZE);
    }

    /**
     * @param modulus modulus
     * @return com.kylinhunter.plat.commons.codec.RSACrypt.RSAKeyPair
     * @title generateRSAKeyPair
     * @description
     * @author BiJi'an
     * @date 2022-06-22 01:27
     */
    public RSAKeyPair generateRSAKeyPair(int modulus) {

        KeyPair keyPair = this.generateKeyPair(modulus);

        return new RSAKeyPair(keyPair);
    }

    public RSAKeyPair generateRSAKeyPair() {
        return this.generateRSAKeyPair(DEFAULT_KEY_SIZE);
    }

    /**
     * @param publicKey publicKey
     * @return java.security.interfaces.RSAPublicKey
     * @title restorePublicKey
     * @description
     * @author BiJi'an
     * @date 2022-06-22 01:28
     */

    public RSAPublicKey restorePublicKey(String publicKey) {

        try {
            KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM_RSA);
            X509EncodedKeySpec spec = new X509EncodedKeySpec(Base64Util.toBytes(publicKey));
            return (RSAPublicKey) keyFactory.generatePublic(spec);
        } catch (Exception e) {
            throw new CryptException("restorePublicKey error", e);
        }
    }

    /**
     * @param privateKey privateKey
     * @return java.security.interfaces.RSAPrivateKey
     * @title restorePrivateKey
     * @description
     * @author BiJi'an
     * @date 2022-06-22 01:28
     */
    public RSAPrivateKey restorePrivateKey(String privateKey) {

        try {
            KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM_RSA);
            PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(Base64Util.toBytes(privateKey));
            return (RSAPrivateKey) keyFactory.generatePrivate(spec);
        } catch (Exception e) {
            throw new CryptException("restorePrivateKey error", e);
        }
    }

    public String encrypt(String data) {
        Cipher cipher = this.cipherManager.getDefaultEnCipher();
        return this.encrypt(data, cipher, this.defaultDecryptKey);

    }

    public String encryptPub(String data, String publicKey) {
        RSAPublicKey rsaPublicKey = restorePublicKey(publicKey);
        return this.encryptPub(data, rsaPublicKey);
    }

    public String encryptPub(String data, RSAPublicKey publicKey) {
        Cipher cipher = this.cipherManager.getEnCipher(publicKey);
        return this.encrypt(data, cipher, publicKey);
    }

    public String encryptPrivate(String data, String privateKey) {
        return this.encryptPrivate(data, restorePrivateKey(privateKey));
    }

    public String encryptPrivate(String data, RSAPrivateKey rasPriateKey) {
        Cipher cipher = this.cipherManager.getEnCipher(rasPriateKey);
        return this.encrypt(data, cipher, rasPriateKey);
    }

    /**
     * 公钥加密
     *
     * @param data   data
     * @param cipher cipher
     * @return String
     */
    private String encrypt(String data, Cipher cipher, RSAKey rsaKey) {

        try {
            // 模长n转换成字节数
            int modulusSize = rsaKey.getModulus().bitLength() / 8;
            // PKCS Padding长度为11字节，所以实际要加密的数据不能要 - 11byte
            int maxSingleSize = modulusSize - 11;
            // 切分字节数组，每段不大于maxSingleSize
            byte[][] dataArray = splitArray(data.getBytes(), maxSingleSize);
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            // 分组加密，并将加密后的内容写入输出字节流
            for (byte[] s : dataArray) {
                out.write(cipher.doFinal(s));
            }
            // 使用Base64将字节数组转换String类型
            return Base64.getEncoder().encodeToString(out.toByteArray());
        } catch (Exception e) {
            throw new CryptException("encrypt error", e);
        }
    }

    public String decrypt(String data) {
        Cipher cipher = this.cipherManager.getDefaultDeCipher();
        return this.decrypt(data, cipher, this.defaultEncryptKey);
    }

    public String decryptPub(String data, String publicKey) {
        return this.decryptPub(data, restorePublicKey(publicKey));
    }

    public String decryptPub(String data, RSAPublicKey rsaPublicKey) {
        Cipher cipher = this.cipherManager.getDeCipher(rsaPublicKey);
        return this.decrypt(data, cipher, rsaPublicKey);
    }

    public String decryptPrivate(String data, String privateKey) {
        return this.decryptPrivate(data, restorePrivateKey(privateKey));
    }

    public String decryptPrivate(String data, RSAPrivateKey rsaPrivateKe) {
        Cipher cipher = this.cipherManager.getDeCipher(rsaPrivateKe);
        return this.decrypt(data, cipher, rsaPrivateKe);
    }

    /**
     * 私钥解密
     *
     * @param data   data
     * @param cipher cipher
     * @return String
     */
    private String decrypt(String data, Cipher cipher, RSAKey rsaKey) {

        try {
            // RSA加密算法的模长 n
            int modulusSize = rsaKey.getModulus().bitLength() / 8;
            byte[] dataBytes = data.getBytes();
            // 之前加密的时候做了转码，此处需要使用Base64进行解码
            byte[] decodeData = Base64.getDecoder().decode(dataBytes);
            // 切分字节数组，每段不大于modulusSize
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
     * 按指定长度切分数组
     *
     * @param data data
     * @param len  data
     * @return byte[][]
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

    @Getter
    @RequiredArgsConstructor
    public static class RSAKeyPair {
        private final KeyPair keyPair;
        private final String publicKey;
        private final String privateKey;

        public RSAKeyPair(KeyPair keyPair) {
            this.keyPair = keyPair;
            this.publicKey = Base64Util.toString(keyPair.getPublic().getEncoded());
            this.privateKey = Base64Util.toString(keyPair.getPrivate().getEncoded());

        }
    }

}

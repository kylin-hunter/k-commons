package com.kylinhunter.plat.commons.codec;

import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import com.kylinhunter.plat.commons.exception.inner.GeneralException;

/**
 * AES算法
 *
 * @author bijian
 */
// @Slf4j
public class AESCrypt {
    private final SecretKey defaultKey;
    private static final CodecType CODE_TYPE = CodecType.AES;

    private static final String DEFAULT_SEED = "kplat";

    private static final AESCrypt singletin = new AESCrypt();
    private final CipherManager cipherManager;

    public static AESCrypt getInstance() {
        return singletin;
    }

    private AESCrypt() {
        this.defaultKey = this.generateKey();
        this.cipherManager = new CipherManager(CODE_TYPE, this.defaultKey);
    }

    public AESCrypt(String defaultKey) {
        this.defaultKey = this.restoreKey(defaultKey);
        this.cipherManager = new CipherManager(CODE_TYPE, this.defaultKey);
    }

    public SecretKey getDefaultKey() {
        return defaultKey;
    }

    public SecretKey generateKey() {
        return generateKey(DEFAULT_SEED);
    }

    public SecretKey generateKey(String seed) {
        // TODO Auto-generated method stub

        try {
            // 1.构造密钥生成器，指定为AES算法,不区分大小写
            KeyGenerator keygen = KeyGenerator.getInstance("AES");
            // 2.根据ecnodeRules规则初始化密钥生成器
            // 生成一个128位的随机源,根据传入的字节数组
            // keygen.init(128, new SecureRandom(encodeRules.getBytes()));
            //            SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
            SecureRandom random = new SecureRandom(seed.getBytes());
            //            random.setSeed(seed.getBytes());
            keygen.init(128, random);// 利用用户密码作为随机数初始化出

            // 3.产生原始对称密钥
            SecretKey original_key = keygen.generateKey();
            // 4.获得原始对称密钥的字节数组
            byte[] keyRaw = original_key.getEncoded();
            return new SecretKeySpec(keyRaw, "AES");// 转换为AES专用密钥

        } catch (Exception e) {
            // TODO Auto-generated catch block
            throw new GeneralException("generateKey error", e);
        }
    }

    public SecretKey restoreKey(String key) {
        return new SecretKeySpec(Base64Util.toBytes(key), "AES");
    }

    public String stringKey(SecretKey key) {
        return Base64Util.toString(key.getEncoded());
    }

    public String encrypt(String text) {
        return encrypt(text, cipherManager.getDefaultEnCipher());
    }

    public String encrypt(String text, String keyStr) {
        return encrypt(text, restoreKey(keyStr));
    }

    public String encrypt(String text, SecretKey key) {
        // TODO Auto-generated method stub

        return encrypt(text, cipherManager.getEnCipher(key));

    }

    private String encrypt(String text, Cipher cipher) {
        // TODO Auto-generated method stub

        try {
            // log.info("明文：" + text);
            byte[] textBytes = text.getBytes(StandardCharsets.UTF_8);
            // log.info("明文的原始字节（utf-8）：" + Arrays.toString(textBytes));
            int length = textBytes.length;
            // 计算需填充长度
            int blockSize = cipher.getBlockSize();

            if (length % blockSize != 0) {
                length = length + (blockSize - (length % blockSize));
            }
            byte[] plaintext = new byte[length];
            // 填充
            System.arraycopy(textBytes, 0, plaintext, 0, textBytes.length);

            byte[] decrptText = cipher.doFinal(plaintext);// 加密
            // log.info("密文字节：" + Arrays.toString(decrptText));
            byte[] base64Bytes = Base64.getEncoder().encode(decrptText);
            // log.info("密字节(base64之后）：" + Arrays.toString(base64Bytes));
            // log.info("加密的最终结果：" + result);

            return new String(base64Bytes, StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new GeneralException("encrypt error", e);

        }

    }

    public String decrypt(String text) {
        return decrypt(text, cipherManager.getDefaultDeCipher());
    }

    public String decrypt(String text, String keyStr) {
        return decrypt(text, restoreKey(keyStr));
    }

    public String decrypt(String decryptText, SecretKey key) {
        // TODO Auto-generated method stub
        // log.info("密文：" + decryptText);
        return decrypt(decryptText, cipherManager.getDeCipher(key));
    }

    private String decrypt(String decryptText, Cipher cipher) {
        // TODO Auto-generated method stub

        try {

            byte[] decryptTextBytes = decryptText.getBytes(StandardCharsets.UTF_8);
            // log.info("密文用UTF8进行还原（utf-8）：" + Arrays.toString(decryptTextBytes));

            byte[] decryptTextRaw = Base64.getDecoder().decode(decryptTextBytes);
            // log.info("密文用base64还原：" + Arrays.toString(decryptTextRaw));

            byte[] encryptTextBytes = cipher.doFinal(decryptTextRaw);// 加密

            // log.info("明文字节：" + Arrays.toString(encryptTextBytes));

            String result = new String(encryptTextBytes, StandardCharsets.UTF_8);
            // log.info("解密的最终明文（UTF-8编码)：" + result);

            return result.trim();
        } catch (Exception e) {
            throw new GeneralException("encrypt error:" + e.getMessage(), e);

        }

    }

}

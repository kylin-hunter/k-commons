package com.kylinhunter.plat.commons.codec;

import java.security.InvalidKeyException;
import java.security.Key;

import javax.crypto.Cipher;

import com.kylinhunter.plat.commons.exception.inner.InitException;

/**
 * @author BiJi'an
 * @description
 * @date 2022-06-22 01:55
 **/

public class CipherManager {
    private final CodecType codecType;

    private final ThreadLocal<Cipher> defaultEnCipher;
    private final ThreadLocal<Cipher> defaultDeCipher;
    private final ThreadLocal<Cipher> enCipher;
    private final ThreadLocal<Cipher> deCipher;

    public CipherManager(CodecType codecType, Key key) {
        this.codecType = codecType;

        this.defaultEnCipher = this.initCipher(Cipher.ENCRYPT_MODE, key);
        this.defaultDeCipher = this.initCipher(Cipher.DECRYPT_MODE, key);
        this.enCipher = this.initCipher(Cipher.ENCRYPT_MODE, null);
        this.deCipher = this.initCipher(Cipher.DECRYPT_MODE, null);
    }

    public CipherManager(CodecType codecType, Key encryptKey, Key decryptKey) {
        this.codecType = codecType;

        this.defaultEnCipher = this.initCipher(Cipher.ENCRYPT_MODE, encryptKey);
        this.defaultDeCipher = this.initCipher(Cipher.DECRYPT_MODE, decryptKey);
        this.enCipher = this.initCipher(Cipher.ENCRYPT_MODE, null);
        this.deCipher = this.initCipher(Cipher.DECRYPT_MODE, null);
    }

    private ThreadLocal<Cipher> initCipher(int mode, Key key) {
        return ThreadLocal.withInitial(() -> {

            try {
                Cipher cipher;

                if (codecType == CodecType.AES) {
                    cipher = Cipher.getInstance("AES/ECB/NoPadding"); // 创建密码器
                } else if (codecType == CodecType.RSA) {
                    cipher = Cipher.getInstance("RSA"); // 创建密码器
                } else {
                    throw new InitException("invalid codecType:" + codecType);
                }
                if (key != null) {
                    cipher.init(mode, key);// 初始化为加密模式的密码器
                }
                return cipher;
            } catch (Exception e) {
                throw new InitException("initCipher error", e);
            }
        });
    }

    public Cipher getDefaultEnCipher() {

        return this.defaultEnCipher.get();

    }

    public Cipher getDefaultDeCipher() {

        return this.defaultDeCipher.get();

    }

    public Cipher getEnCipher(Key key) {

        try {
            Cipher cipher = this.enCipher.get();
            cipher.init(Cipher.ENCRYPT_MODE, key);// 初始化为加密模式的密码器
            return cipher;
        } catch (InvalidKeyException e) {
            throw new InitException("getEnCipher error", e);
        }

    }

    public Cipher getDeCipher(Key key) {
        try {
            Cipher cipher = this.deCipher.get();
            cipher.init(Cipher.DECRYPT_MODE, key);// 初始化为解密模式的密码器
            return cipher;
        } catch (InvalidKeyException e) {
            throw new InitException("getDeCipher error", e);
        }
    }

}

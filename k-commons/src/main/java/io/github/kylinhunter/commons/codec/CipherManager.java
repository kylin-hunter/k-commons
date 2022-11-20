package io.github.kylinhunter.commons.codec;

import java.security.InvalidKeyException;
import java.security.Key;

import javax.crypto.Cipher;

import io.github.kylinhunter.commons.exception.inner.InitException;

/**
 * @author BiJi'an
 * @description
 * @date 2022-06-22 01:55
 **/

public class CipherManager {
    private final CodecType codecType;

    private final ThreadLocal<Cipher> enCipher;
    private final ThreadLocal<Cipher> deCipher;

    public CipherManager(CodecType codecType) {
        this.codecType = codecType;
        this.enCipher = this.initCipher();
        this.deCipher = this.initCipher();
    }

    /**
     * @return java.lang.ThreadLocal<javax.crypto.Cipher>
     * @title initCipher
     * @description
     * @author BiJi'an
     * @date 2022-11-20 18:53
     */
    private ThreadLocal<Cipher> initCipher() {
        return ThreadLocal.withInitial(() -> {

            try {
                Cipher cipher;

                if (codecType == CodecType.AES) {
                    cipher = Cipher.getInstance("AES/ECB/NoPadding");
                } else if (codecType == CodecType.RSA) {
                    cipher = Cipher.getInstance("RSA");
                } else {
                    throw new InitException("invalid codecType:" + codecType);
                }
                return cipher;
            } catch (Exception e) {
                throw new InitException("initCipher error", e);
            }
        });
    }

    /**
     * @param key key
     * @return javax.crypto.Cipher
     * @title getEnCipher
     * @description
     * @author BiJi'an
     * @date 2022-11-20 18:53
     */
    public Cipher getEnCipher(Key key) {

        try {
            Cipher cipher = this.enCipher.get();
            cipher.init(Cipher.ENCRYPT_MODE, key);
            return cipher;
        } catch (InvalidKeyException e) {
            throw new InitException("getEnCipher error", e);
        }

    }

    /**
     * @param key key
     * @return javax.crypto.Cipher
     * @title getDeCipher
     * @description
     * @author BiJi'an
     * @date 2022-11-20 18:53
     */

    public Cipher getDeCipher(Key key) {
        try {
            Cipher cipher = this.deCipher.get();
            cipher.init(Cipher.DECRYPT_MODE, key);
            return cipher;
        } catch (InvalidKeyException e) {
            throw new InitException("getDeCipher error", e);
        }
    }

    /**
     * @author BiJi'an
     * @description
     * @date 2022-06-22 02:17
     **/
    public enum CodecType {
        AES, RSA
    }
}

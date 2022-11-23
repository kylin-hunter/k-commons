package io.github.kylinhunter.commons.codec;

import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

import io.github.kylinhunter.commons.exception.builtin.CryptException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author BiJi'an
 * @description
 * @date 2022-11-20 21:40
 **/
public class RSAKeyManager {
    private final static String ALGORITHM_RSA = "RSA";
    private static final int DEFAULT_KEY_SIZE = 1024;
    @Getter
    private final RSAPrivateKey defaultPrivateKey;
    @Getter
    private final RSAPublicKey defaultPubKey;

    private final LoadingCache<String, RSAPublicKey> rsaPublicKeyCache;

    private final LoadingCache<String, RSAPrivateKey> rsaPrivateKeyCache;

    public RSAKeyManager() {
        RSAKeyPair rsaKeyPair = this.newKeyPair();
        this.defaultPubKey = (RSAPublicKey) rsaKeyPair.getKeyPair().getPublic();
        this.defaultPrivateKey = (RSAPrivateKey) rsaKeyPair.getKeyPair().getPrivate();

        this.rsaPublicKeyCache = CacheBuilder.newBuilder()
                .expireAfterWrite(10000, TimeUnit.DAYS)
                .build(new CacheLoader<String, RSAPublicKey>() {
                    @SuppressWarnings("NullableProblems")
                    @Override
                    public RSAPublicKey load(String key) {
                        return RSAKeyManager.this.restorePublicKey(key);

                    }
                });

        this.rsaPrivateKeyCache = CacheBuilder.newBuilder()
                .expireAfterWrite(10000, TimeUnit.DAYS)
                .build(new CacheLoader<String, RSAPrivateKey>() {
                    @SuppressWarnings("NullableProblems")
                    @Override
                    public RSAPrivateKey load(String key) {
                        return RSAKeyManager.this.restorePrivateKey(key);
                    }
                });
    }

    /**
     * @param keysize keysize
     * @return io.github.kylinhunter.commons.codec.RSAUtils.RSAKeyPair
     * @title generateRSAKeyPair
     * @description
     * @author BiJi'an
     * @date 2022-06-22 01:27
     */
    public RSAKeyPair newKeyPair(int keysize) {
        try {

            KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(ALGORITHM_RSA);
            keyPairGen.initialize(keysize);
            KeyPair keyPair = keyPairGen.generateKeyPair();

            return new RSAKeyPair(keyPair);
        } catch (NoSuchAlgorithmException e) {
            throw new CryptException("generateRSAKeyPair error", e);

        }
    }

    public RSAKeyPair newKeyPair() {
        return this.newKeyPair(DEFAULT_KEY_SIZE);
    }

    /**
     * @param publicKey publicKey
     * @return java.security.interfaces.RSAPublicKey
     * @title restorePublicKey
     * @description
     * @author BiJi'an
     * @date 2022-06-22 01:28
     */

    private RSAPublicKey restorePublicKey(String publicKey) {

        try {
            KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM_RSA);
            X509EncodedKeySpec spec = new X509EncodedKeySpec(Base64Utils.decode(publicKey));
            return (RSAPublicKey) keyFactory.generatePublic(spec);
        } catch (Exception e) {
            throw new CryptException("restorePublicKey error", e);
        }
    }

    public RSAPublicKey toPublicKey(String publicKey) {
        try {
            return this.rsaPublicKeyCache.get(publicKey);
        } catch (ExecutionException e) {
            throw new CryptException("toPublicKey error", e);
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
    private RSAPrivateKey restorePrivateKey(String privateKey) {

        try {
            KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM_RSA);
            PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(Base64Utils.decode(privateKey));
            return (RSAPrivateKey) keyFactory.generatePrivate(spec);
        } catch (Exception e) {
            throw new CryptException("restorePrivateKey error", e);
        }
    }

    /**
     * @param publicKey publicKey
     * @return java.security.interfaces.RSAPrivateKey
     * @title toPrivateKey
     * @description
     * @author BiJi'an
     * @date 2022-11-20 23:29
     */
    public RSAPrivateKey toPrivateKey(String publicKey) {
        try {
            return this.rsaPrivateKeyCache.get(publicKey);
        } catch (ExecutionException e) {
            throw new CryptException("toPrivateKey error", e);
        }
    }

    @Getter
    @RequiredArgsConstructor
    public static class RSAKeyPair {
        private final KeyPair keyPair;
        private final String publicKey;
        private final String privateKey;

        public RSAKeyPair(KeyPair keyPair) {
            this.keyPair = keyPair;
            this.publicKey = Base64Utils.encodeToString(keyPair.getPublic().getEncoded());
            this.privateKey = Base64Utils.encodeToString(keyPair.getPrivate().getEncoded());

        }
    }

}

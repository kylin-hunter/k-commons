package io.github.kylinhunter.commons.codec;

import java.security.KeyFactory;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.X509EncodedKeySpec;

import io.github.kylinhunter.commons.cache.guava.AbstractCache;
import io.github.kylinhunter.commons.cache.guava.CacheConfig;
import io.github.kylinhunter.commons.cache.guava.CacheKey;
import io.github.kylinhunter.commons.exception.embed.CryptException;

/**
 * @author BiJi'an
 * @description
 * @date 2022-11-27 02:22
 **/
public class RSAPublicKeyCache extends AbstractCache<RSAPublicKey> {

    @Override
    public RSAPublicKey load(CacheKey cacheKey) {
        try {
            String key = cacheKey.getString(0);
            KeyFactory keyFactory = KeyFactory.getInstance(RSAKeyManager.ALGORITHM_RSA);
            X509EncodedKeySpec spec = new X509EncodedKeySpec(Base64Utils.decode(key));
            return (RSAPublicKey) keyFactory.generatePublic(spec);
        } catch (Exception e) {
            throw new CryptException("restorePublicKey error", e);
        }
    }

    @Override
    protected void custom(CacheConfig cacheConfig) {

    }
}

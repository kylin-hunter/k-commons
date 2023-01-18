package io.github.kylinhunter.commons.codec;

import java.security.KeyFactory;
import java.security.interfaces.RSAPrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;

import io.github.kylinhunter.commons.cache.guava.CacheConfig;
import io.github.kylinhunter.commons.cache.guava.CacheKey;
import io.github.kylinhunter.commons.cache.guava.AbstractCache;
import io.github.kylinhunter.commons.exception.embed.CryptException;

/**
 * @author BiJi'an
 * @description
 * @date 2022-11-27 02:22
 **/
public class RSAPrivateKeyCache extends AbstractCache< RSAPrivateKey> {

    @Override
    public RSAPrivateKey load(CacheKey cacheKey) {
        try {
            String key= cacheKey.getString(0);
            KeyFactory keyFactory = KeyFactory.getInstance(RSAKeyManager.ALGORITHM_RSA);
            PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(Base64Utils.decode(key));
            return (RSAPrivateKey) keyFactory.generatePrivate(spec);
        } catch (Exception e) {
            throw new CryptException("restorePrivateKey error", e);
        }
    }

    @Override
    protected void custom(CacheConfig cacheConfig) {

    }
}

package io.github.kylinhunter.commons.codec;

import java.security.KeyFactory;
import java.security.interfaces.RSAPrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.concurrent.TimeUnit;

import io.github.kylinhunter.commons.cache.GuavaCache;
import io.github.kylinhunter.commons.cache.GuavaCacheConfig;
import io.github.kylinhunter.commons.exception.embed.CryptException;

/**
 * @author BiJi'an
 * @description
 * @date 2022-11-27 02:22
 **/
public class RSAPrivateKeyCache extends GuavaCache<String, RSAPrivateKey> {

    @Override
    public RSAPrivateKey load(String key) {
        try {
            KeyFactory keyFactory = KeyFactory.getInstance(RSAKeyManager.ALGORITHM_RSA);
            PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(Base64Utils.decode(key));
            return (RSAPrivateKey) keyFactory.generatePrivate(spec);
        } catch (Exception e) {
            throw new CryptException("restorePrivateKey error", e);
        }
    }

}

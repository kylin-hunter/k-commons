package o.github.kylinhunter.commons.utils.codec;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import io.github.kylinhunter.commons.cache.guava.AbstractCache;
import io.github.kylinhunter.commons.cache.guava.CacheConfig;
import io.github.kylinhunter.commons.cache.guava.CacheKey;

/**
 * @author BiJi'an
 * @description
 * @date 2022-11-27 02:22
 */
public class AESKeyCache extends AbstractCache<SecretKey> {

    @Override
    public SecretKey load(CacheKey key) {
        return new SecretKeySpec(Base64Utils.decode(key.getString(0)), "AES");
    }

    @Override
    protected void custom(CacheConfig cacheConfig) {
    }
}

package io.github.kylinhunter.commons.codec;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import io.github.kylinhunter.commons.cache.guava.CacheKey;
import io.github.kylinhunter.commons.cache.guava.AbstractGuavaCache;

/**
 * @author BiJi'an
 * @description
 * @date 2022-11-27 02:22
 **/
public class AESKeyCache extends AbstractGuavaCache<SecretKey> {

    @Override
    public SecretKey load(CacheKey key) {
        return new SecretKeySpec(Base64Utils.decode((String) key.getParams()[0]), "AES");
    }


}

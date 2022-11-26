package io.github.kylinhunter.commons.codec;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import io.github.kylinhunter.commons.cache.GuavaCache;

/**
 * @author BiJi'an
 * @description
 * @date 2022-11-27 02:22
 **/
public class AESKeyCache extends GuavaCache<String, SecretKey> {

    @Override
    public SecretKey load(String key) {
        return new SecretKeySpec(Base64Utils.decode(key), "AES");
    }

}

/*
 * Copyright (C) 2023 The k-commons Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package o.github.kylinhunter.commons.utils.codec;

import io.github.kylinhunter.commons.cache.guava.AbstractCache;
import io.github.kylinhunter.commons.cache.guava.CacheConfig;
import io.github.kylinhunter.commons.cache.guava.CacheKey;
import io.github.kylinhunter.commons.exception.embed.CryptException;
import java.security.KeyFactory;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.X509EncodedKeySpec;

/**
 * @author BiJi'an
 * @description
 * @date 2022-11-27 02:22
 */
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
  protected void custom(CacheConfig cacheConfig) {}
}

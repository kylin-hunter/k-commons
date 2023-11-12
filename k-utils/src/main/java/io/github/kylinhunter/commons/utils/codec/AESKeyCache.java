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
package io.github.kylinhunter.commons.utils.codec;

import io.github.kylinhunter.commons.utils.cache.guava.AbstractCache;
import io.github.kylinhunter.commons.utils.cache.guava.CacheConfig;
import io.github.kylinhunter.commons.utils.cache.guava.CacheKey;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

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

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

import io.github.kylinhunter.commons.exception.embed.CryptException;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import lombok.Getter;

/**
 * @author BiJi'an
 * @description
 * @date 2022-11-20 17:34
 */
public class AESKeyManager {
  @Getter private final SecretKey defaultKey;
  @Getter private final String defaultKeyStr;
  private static final String DEFAULT_SEED = "kplat";
  private final AESKeyCache keyCache = new AESKeyCache();

  public AESKeyManager(CipherManager.CodecType codecType) {
    this.defaultKey = this.newKey();
    this.defaultKeyStr = this.keyToString(this.defaultKey);
  }

  /**
   * @return javax.crypto.SecretKey
   * @title generateKey
   * @description
   * @author BiJi'an
   * @date 2022-11-20 17:01
   */
  public SecretKey newKey() {
    return newKey(DEFAULT_SEED);
  }

  /**
   * @param seed seed
   * @return javax.crypto.SecretKey
   * @title generateKey
   * @description SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
   * @author BiJi'an
   * @date 2022-11-20 17:14
   */
  public SecretKey newKey(String seed) {

    try {
      KeyGenerator keygen = KeyGenerator.getInstance("AES");
      SecureRandom random = new SecureRandom(seed.getBytes(StandardCharsets.UTF_8));
      keygen.init(128, random);
      SecretKey secretKey = keygen.generateKey();
      byte[] keyRaw = secretKey.getEncoded();

      return new SecretKeySpec(keyRaw, "AES"); // 转换为AES专用密钥

    } catch (Exception e) {
      throw new CryptException("generateKey error", e);
    }
  }

  /**
   * @return java.lang.String
   * @title generateKeyStr
   * @description
   * @author BiJi'an
   * @date 2022-11-20 23:11
   */
  public String newKeyStr() {
    return keyToString(newKey(DEFAULT_SEED));
  }

  /**
   * @param key key
   * @return java.lang.String
   * @title stringKey
   * @description
   * @author BiJi'an
   * @date 2022-11-20 17:15
   */
  public String keyToString(SecretKey key) {
    return Base64Utils.encodeToString(key.getEncoded());
  }

  /**
   * @param key key
   * @return javax.crypto.SecretKey
   * @title restoreKey
   * @description
   * @author BiJi'an
   * @date 2022-11-20 17:15
   */
  public SecretKey toKey(String key) {
    return keyCache.get(key);
  }
}

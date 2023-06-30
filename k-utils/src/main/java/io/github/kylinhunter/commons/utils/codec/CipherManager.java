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

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import io.github.kylinhunter.commons.exception.embed.InitException;
import javax.crypto.Cipher;

/**
 * @author BiJi'an
 * @description
 * @date 2022-06-22 01:55
 */
@SuppressFBWarnings({"ECB_MODE", "CIPHER_INTEGRITY"})
public class CipherManager {

  private final CodecType codecType;

  private final ThreadLocal<Cipher> enCipher;
  private final ThreadLocal<Cipher> deCipher;

  public CipherManager(CodecType codecType) {
    this.codecType = codecType;
    this.enCipher = this.initCipher();
    this.deCipher = this.initCipher();
  }

  /**
   * @return java.lang.ThreadLocal<javax.crypto.Cipher>
   * @title initCipher
   * @description
   * @author BiJi'an
   * @date 2022-11-20 18:53
   */
  private ThreadLocal<Cipher> initCipher() {
    return ThreadLocal.withInitial(
        () -> {
          try {
            Cipher cipher;
            if (codecType == CodecType.AES_ECB_NOPADDING) {
              cipher = Cipher.getInstance("AES/ECB/NoPadding");
            } else if (codecType == CodecType.AES_GCM_NOPADDING) {
              cipher = Cipher.getInstance("AES/GCM/NoPadding");
            } else if (codecType == CodecType.RSA) {
              cipher = Cipher.getInstance("RSA");
            } else {
              throw new InitException("invalid codecType:" + codecType);
            }
            return cipher;
          } catch (Exception e) {
            throw new InitException("initCipher error", e);
          }
        });
  }

  /**
   * @return javax.crypto.Cipher
   * @title getEnCipher
   * @description
   * @author BiJi'an
   * @date 2022-11-20 18:53
   */
  public Cipher getEnCipher() {

    return this.enCipher.get();
  }

  /**
   * @return javax.crypto.Cipher
   * @title getDeCipher
   * @description
   * @author BiJi'an
   * @date 2022-11-20 18:53
   */
  public Cipher getDeCipher() {
    return this.deCipher.get();
  }

  /**
   * @author BiJi'an
   * @description
   * @date 2022-06-22 02:17
   */
  public enum CodecType {
    AES_ECB_NOPADDING,
    AES_GCM_NOPADDING,
    RSA
  }
}

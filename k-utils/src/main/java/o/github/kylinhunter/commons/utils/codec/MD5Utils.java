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

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import io.github.kylinhunter.commons.exception.embed.CryptException;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.codec.digest.Md5Crypt;

/**
 * @author BiJi'an
 * @description
 * @date 2022-06-07 00:47
 */
public class MD5Utils {

  /**
   * @param text text
   * @return java.lang.String
   * @title md5
   * @description
   * @author BiJi'an
   * @date 2022-11-20 16:55
   */
  public static String md5(String text) {
    return md5(text.getBytes(StandardCharsets.UTF_8));
  }

  /**
   * @param text text
   * @param salt salt
   * @return java.lang.String
   * @title md5
   * @description
   * @author BiJi'an
   * @date 2022-11-20 16:53
   */
  public static String md5(String text, String salt) {
    return Md5Crypt.md5Crypt(text.getBytes(StandardCharsets.UTF_8), salt);
  }

  /**
   * @param bytes bytes
   * @return java.lang.String
   * @title md5
   * @description
   * @author BiJi'an
   * @date 2022-11-20 16:53
   */
  @SuppressFBWarnings("WEAK_MESSAGE_DIGEST_MD5")
  public static String md5(byte[] bytes) {
    return DigestUtils.md5Hex(bytes);
  }

  /**
   * @param inputStream inputStream
   * @return java.lang.String
   * @title md5
   * @description
   * @author BiJi'an
   * @date 2022-11-20 16:53
   */
  @SuppressFBWarnings("WEAK_MESSAGE_DIGEST_MD5")
  public static String md5(InputStream inputStream) {
    try {
      return DigestUtils.md5Hex(inputStream);

    } catch (Exception e) {
      throw new CryptException("md5 error ", e);
    }
  }

  /**
   * @param file file
   * @return java.lang.String
   * @title md5
   * @description
   * @author BiJi'an
   * @date 2022-11-20 16:53
   */
  public static String md5(File file) {
    if (!file.isFile()) {
      throw new CryptException("not file " + file.getAbsolutePath());
    }
    try (FileInputStream in = new FileInputStream(file)) {
      return md5(in);
    } catch (Exception e) {
      throw new CryptException("md5 error ", e);
    }
  }
}

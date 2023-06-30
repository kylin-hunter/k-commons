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

import io.github.kylinhunter.commons.exception.embed.CryptException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * @author BiJi'an
 * @description
 * @date 2022-06-22 03:19
 */
public class Base64Utils {

  /**
   * @param text text
   * @param charsetName charsetName
   * @return java.lang.String
   * @title encodeToString
   * @description
   * @author BiJi'an
   * @date 2022-11-20 16:49
   */
  public static String encodeToString(String text, String charsetName) {
    try {
      return encodeToString(text.getBytes(charsetName));
    } catch (UnsupportedEncodingException e) {
      throw new CryptException("encode error", e);
    }
  }

  /**
   * @param text text
   * @param charset charset
   * @return java.lang.String
   * @title encodeToString
   * @description
   * @author BiJi'an
   * @date 2022-11-20 16:49
   */
  public static String encodeToString(String text, Charset charset) {
    return encodeToString(text.getBytes(charset));
  }

  /**
   * @param text text
   * @return java.lang.String
   * @title encodeToString
   * @description
   * @author BiJi'an
   * @date 2022-11-20 16:46
   */
  public static String encodeToString(String text) {
    return encodeToString(text.getBytes(StandardCharsets.UTF_8));
  }

  /**
   * @param bytes bytes
   * @return java.lang.String
   * @title encodeToString
   * @description
   * @author BiJi'an
   * @date 2022-11-20 16:46
   */
  public static String encodeToString(byte[] bytes) {
    return new String(encode(bytes), StandardCharsets.UTF_8);
  }

  /**
   * @param bytes bytes
   * @return byte[]
   * @title encode
   * @description
   * @author BiJi'an
   * @date 2022-11-20 16:46
   */
  public static byte[] encode(byte[] bytes) {
    return Base64.getEncoder().encode(bytes);
  }

  /**
   * @param text text
   * @param charsetName charsetName
   * @return java.lang.String
   * @title decodeToString
   * @description
   * @author BiJi'an
   * @date 2022-11-20 16:44
   */
  public static String decodeToString(String text, String charsetName) {
    try {

      return new String(decode(text), charsetName);
    } catch (UnsupportedEncodingException e) {
      throw new CryptException("decode error", e);
    }
  }

  /**
   * @param text text
   * @param charset charset
   * @return java.lang.String
   * @title decodeToString
   * @description
   * @author BiJi'an
   * @date 2022-11-20 16:44
   */
  public static String decodeToString(String text, Charset charset) {

    return new String(decode(text), charset);
  }

  /**
   * @param text text
   * @return java.lang.String
   * @title decodeToString
   * @description
   * @author BiJi'an
   * @date 2022-11-20 16:44
   */
  public static String decodeToString(String text) {
    return new String(decode(text), StandardCharsets.UTF_8);
  }

  /**
   * @param text text
   * @return byte[]
   * @title decode
   * @description
   * @author BiJi'an
   * @date 2022-11-20 16:44
   */
  public static byte[] decode(String text) {
    return decode(text.getBytes(StandardCharsets.UTF_8));
  }

  /**
   * @param bytes bytes
   * @return byte[]
   * @title decode
   * @description
   * @author BiJi'an
   * @date 2022-11-20 16:44
   */
  public static byte[] decode(byte[] bytes) {
    return Base64.getDecoder().decode(bytes);
  }
}

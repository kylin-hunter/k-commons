package io.github.kylinhunter.commons.io;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * @author BiJi'an
 * @description
 * @date 2023-03-19 00:59
 */
public class Charsets {

  public static final Charset UTF_8 = StandardCharsets.UTF_8;
  public static final String UTF_8_VALUE = StandardCharsets.UTF_8.name();

  /**
   * @param charsetName charsetName
   * @return java.nio.charset.Charset
   * @title toCharset
   * @description
   * @author BiJi'an
   * @date 2023-03-19 00:59
   */
  public static Charset toCharset(final String charsetName) {
    return (charsetName == null || charsetName.length() == 0)
        ? Charset.defaultCharset()
        : Charset.forName(charsetName);
  }

  /**
   * @param charset charset
   * @return java.nio.charset.Charset
   * @title toCharset
   * @description toCharset
   * @author BiJi'an
   * @date 2023-06-11 10:50
   */
  public static Charset toCharset(final Charset charset) {
    return charset == null ? Charset.defaultCharset() : charset;
  }
}

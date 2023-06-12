package io.github.kylinhunter.commons.io;

import io.github.kylinhunter.commons.exception.embed.KIOException;
import java.net.URI;

/**
 * @author BiJi'an
 * @description
 * @date 2023-01-24 22:17
 */
public class IOHelper {

  /**
   * @param uri uri
   * @return java.net.URI
   * @title toURI
   * @description
   * @author BiJi'an
   * @date 2023-01-24 22:19
   */
  public static URI toURI(String uri) {
    try {
      return new URI(uri);
    } catch (Exception e) {
      throw new KIOException("invalid uri:" + uri);
    }
  }


}

package io.github.kylinhunter.commons.os;

/**
 * @author BiJi'an
 * @description
 * @date 2024-01-09 23:59
 */
public class OSUtils {

  private static volatile OS os;

  /**
   * @return io.github.kylinhunter.commons.os.OS
   * @title os
   * @description os
   * @author BiJi'an
   * @date 2024-01-01 00:12
   */
  public static OS os() {
    if (os == null) {
      synchronized (OS.class) {
        if (os == null) {
          String osName = System.getProperty("os.name").toLowerCase();
          if (osName.contains("win")) {
            os = OS.WINDOWS;
          } else if (osName.contains("mac")) {
            os = OS.MAC;
          } else if (osName.contains("nix") || osName.contains("nux") || osName.contains("aix")) {
            os = OS.LINUX;
          } else {
            os = OS.OTHERS;
          }
        }

      }

    }
    return os;

  }

}
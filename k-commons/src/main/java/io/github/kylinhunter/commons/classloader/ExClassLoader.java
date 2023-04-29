package io.github.kylinhunter.commons.classloader;

import java.net.URL;
import java.net.URLClassLoader;
import java.security.AccessController;
import java.security.PrivilegedAction;

/**
 * @author BiJi'an
 * @description
 * @date 2022-06-13 20:10
 */
class ExClassLoader extends URLClassLoader {

  private static ExClassLoader singletion;

  public static ExClassLoader getInstance() {
    return singletion;
  }

  private ExClassLoader() {
    super(new URL[] {}, ExClassLoader.class.getClassLoader());
  }

  public ExClassLoader(URL[] urls, ClassLoader parent) {
    super(urls, parent);
  }

  public ExClassLoader(URL[] urls) {
    super(urls);
  }

  static {
    singletion =
        AccessController.doPrivileged((PrivilegedAction<ExClassLoader>) () -> new ExClassLoader());
  }
}

package io.github.kylinhunter.commons.util;

import io.github.kylinhunter.commons.collections.SetUtils;
import java.util.Set;

/**
 * @author BiJi'an
 * @description
 * @date 2023-03-11 14:18
 */
public class OnceRunner {

  private static final Set<String> runNames = SetUtils.newHashSet();

  public static void run(Class<?> clazz, Runner r) {
    run(clazz.getName(), r);
  }

  /**
   * @param name name
   * @param r r
   * @return void
   * @title run
   * @description
   * @author BiJi'an
   * @date 2023-03-11 23:07
   */
  public static void run(String name, Runner r) {
    if (r != null) {
      if (!runNames.contains(name)) {
        synchronized (OnceRunner.class) {
          if (!runNames.contains(name)) {
            r.run();
            runNames.add(name);
          }
        }
      }
    }
  }

  @FunctionalInterface
  public static interface Runner {
    void run();
  }
}

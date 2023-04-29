package io.github.kylinhunter.commons.component;

import java.util.List;

/**
 * @author BiJi'an
 * @description
 * @date 2022-10-22 01:48
 */
public class CF {
  private static final CompManager COMP_MANAGER = new CompManager();

  static {
    init();
  }

  /**
   * @return void
   * @title init
   * @description
   * @author BiJi'an
   * @date 2022-11-08 21:38
   */
  public static void init(String... pkgs) {
    COMP_MANAGER.init(pkgs);
  }

  /**
   * @param ct ct
   * @return T
   * @title get
   * @description
   * @author BiJi'an
   * @date 2022-12-03 20:08
   */
  public static <T> T get(CT<T> ct) {
    return get(ct.getClazz(), true);
  }

  /**
   * @param clazz clazz
   * @return T
   * @title get
   * @description
   * @author BiJi'an
   * @date 2022-11-08 20:06
   */
  public static <T, S extends T> T get(Class<S> clazz) {
    return get(clazz, true);
  }

  /**
   * @param compClazz clazz
   * @param required required
   * @return T
   * @title get
   * @description
   * @author BiJi'an
   * @date 2022-11-08 20:06
   */
  public static <T, S extends T> T get(Class<S> compClazz, boolean required) {
    return COMP_MANAGER.get(compClazz, required);
  }

  /**
   * @param clazz clazz
   * @return T
   * @title get
   * @description
   * @author BiJi'an
   * @date 2022-11-08 20:06
   */
  public static <T> List<T> getAll(Class<T> clazz) {
    return COMP_MANAGER.getAll(clazz, true);
  }

  /**
   * @param compClazz clazz
   * @param required required
   * @return T
   * @title get
   * @description
   * @author BiJi'an
   * @date 2022-11-08 20:06
   */
  public static <T> List<T> getAll(Class<T> compClazz, boolean required) {
    return COMP_MANAGER.getAll(compClazz, required);
  }
}

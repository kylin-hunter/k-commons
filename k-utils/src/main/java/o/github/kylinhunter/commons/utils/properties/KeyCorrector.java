package o.github.kylinhunter.commons.utils.properties;

import io.github.kylinhunter.commons.name.NameRule;

/**
 * @author BiJi'an
 * @description
 * @date 2023-04-01 19:32
 */
@FunctionalInterface
public interface KeyCorrector {
  /**
   * @param key key
   * @param nameRule nameRule
   * @return java.lang.String
   * @title correct
   * @description
   * @author BiJi'an
   * @date 2023-02-04 00:36
   */
  Object correct(Object key, NameRule nameRule);
}

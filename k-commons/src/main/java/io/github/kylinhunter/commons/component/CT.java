package io.github.kylinhunter.commons.component;

/**
 * @author BiJi'an
 * @description
 * @date 2022-12-03 19:58
 */
public interface CT<T> {
  Class<? extends T> getClazz();
}

package io.github.kylinhunter.commons.init;

import java.lang.annotation.Annotation;
import java.util.Set;
import org.reflections.Reflections;
import org.reflections.scanners.Scanners;

/**
 * @author BiJi'an
 * @description
 * @date 2023-06-20 15:45
 */
public class ClassScanner {

  private final Reflections reflections;

  public ClassScanner(Set<String> allPackages) {
    reflections = new Reflections(allPackages, Scanners.SubTypes, Scanners.TypesAnnotated);
  }

  /**
   * @param type type
   * @return java.util.Set<java.lang.Class < ? extends T>>
   * @title getSubTypesOf
   * @description getSubTypesOf
   * @author BiJi'an
   * @date 2023-06-20 20:39
   */
  public <T> Set<Class<? extends T>> getSubTypesOf(Class<T> type) {
    return reflections.getSubTypesOf(type);
  }

  /**
   * @param annotation annotation
   * @return java.util.Set<java.lang.Class < ?>>
   * @title getTypesAnnotatedWith
   * @description getTypesAnnotatedWith
   * @author BiJi'an
   * @date 2023-06-20 21:06
   */
  public Set<Class<?>> getTypesAnnotatedWith(Class<? extends Annotation> annotation) {
    return reflections.getTypesAnnotatedWith(annotation);
  }
}
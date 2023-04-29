package io.github.kylinhunter.commons.component;

import io.github.kylinhunter.commons.collections.SetUtils;
import java.lang.annotation.Annotation;
import java.util.Set;
import lombok.Data;
import org.reflections.ReflectionUtils;
import org.reflections.Reflections;
import org.reflections.scanners.Scanners;

/**
 * @author BiJi'an
 * @description
 * @date 2023-01-19 02:19
 */
@Data
public class CompTools {
  private String[] pkgs;

  public CompTools(String... pkgs) {
    this.pkgs = pkgs;
  }

  /**
   * @param clazz clazz
   * @return java.util.Set<java.lang.Class < ?>>
   * @title getAllInterface
   * @description
   * @author BiJi'an
   * @date 2023-01-19 02:43
   */
  @SuppressWarnings("unchecked")
  public Set<Class<?>> getAllInterface(Class<?> clazz) {
    return ReflectionUtils.getAllSuperTypes(clazz, c -> c.isInterface() && isValid(c));
  }

  /**
   * @param clazz clazz
   * @return boolean
   * @title isValid
   * @description
   * @author BiJi'an
   * @date 2023-01-19 22:07
   */
  public boolean isValid(Class<?> clazz) {
    for (String pkg : pkgs) {
      if (clazz.getPackage().getName().contains(pkg)) {
        return true;
      }
    }
    return false;
  }

  /**
   * @param annotation annotation
   * @return java.util.Set<java.lang.Class < ?>>
   * @title getTypesAnnotatedWith
   * @description
   * @author BiJi'an
   * @date 2023-02-09 23:26
   */
  public Set<Class<?>> getClassesByAnnotatedWith(Class<? extends Annotation> annotation) {
    Set<Class<?>> allCompClazzes = SetUtils.newHashSet();
    for (String pkg : pkgs) {
      Reflections reflections = new Reflections(pkg, Scanners.TypesAnnotated);
      allCompClazzes = reflections.getTypesAnnotatedWith(annotation);
    }
    return allCompClazzes;
  }
}

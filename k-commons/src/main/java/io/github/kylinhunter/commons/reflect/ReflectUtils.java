package io.github.kylinhunter.commons.reflect;

import io.github.kylinhunter.commons.exception.embed.InitException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Set;
import java.util.function.Predicate;

/**
 * @author BiJi'an
 * @description
 * @date 2023-02-11 16:37
 */
public class ReflectUtils {

  /**
   * @param method method
   * @param obj obj
   * @param args args
   * @return java.lang.Object
   * @title invoke
   * @description
   * @author BiJi'an
   * @date 2023-02-10 10:38
   */
  @SuppressWarnings("unchecked")
  public static <T> T invoke(Object obj, Method method, Object... args) {
    try {
      method.setAccessible(true);
      return (T) method.invoke(obj, args);
    } catch (Exception e) {
      throw new InitException("invoke error: " + method, e);
    }
  }

  /**
   * @param obj obj
   * @param field field
   * @param value value
   * @return T
   * @title set
   * @description
   * @author BiJi'an
   * @date 2023-02-11 01:07
   */
  public static void set(Object obj, Field field, Object value) {
    try {
      field.setAccessible(true);
      field.set(obj, value);
    } catch (Exception e) {
      throw new InitException("invoke error", e);
    }
  }

  /**
   * @param clazz clazz
   * @return java.lang.Class<?>
   * @title get
   * @description
   * @author BiJi'an
   * @date 2023-05-12 16:57
   */
  public static Class<?> getSuperClass(Class<?> clazz) {
    return SuperClazzes.get(clazz);
  }

  /**
   * @param clazz clazz
   * @return java.util.Collection<java.lang.Class < ?>>
   * @title getAll
   * @description
   * @author BiJi'an
   * @date 2023-05-11 00:14
   */
  @SafeVarargs
  public static Set<Class<?>> getAllSuperClasses(
      Class<?> clazz, Predicate<Class<?>>... predicates) {
    return SuperClazzes.getAll(clazz, predicates);
  }

  /**
   * @param clazz clazz
   * @param predicates predicates
   * @return java.util.Set<java.lang.Class < ?>>
   * @title getInterfaces
   * @description
   * @author BiJi'an
   * @date 2023-05-12 17:33
   */
  @SafeVarargs
  public static Set<Class<?>> getInterfaces(Class<?> clazz, Predicate<Class<?>>... predicates) {
    return Interfaces.get(clazz, predicates);
  }

  /**
   * @param clazz clazz
   * @param predicates predicates
   * @return java.util.Set<java.lang.Class < ?>>
   * @title getAllInterfaces
   * @description
   * @author BiJi'an
   * @date 2023-05-12 17:33
   */
  @SafeVarargs
  public static Set<Class<?>> getAllInterfaces(Class<?> clazz, Predicate<Class<?>>... predicates) {
    return Interfaces.getAll(clazz, predicates);
  }

  /**
   * @param clazz clazz
   * @param predicates predicates
   * @return java.util.Set<java.lang.reflect.Method>
   * @title get
   * @description
   * @author BiJi'an
   * @date 2023-05-13 00:29
   */
  @SafeVarargs
  public static Set<Method> getMethods(Class<?> clazz, Predicate<Method>... predicates) {
    return Methods.get(clazz, predicates);
  }

  /**
   * @param clazz clazz
   * @param predicates predicates
   * @return java.util.Set<java.lang.reflect.Method>
   * @title getAll
   * @description
   * @author BiJi'an
   * @date 2023-05-13 00:29
   */
  @SafeVarargs
  public static Set<Method> getAllMethods(Class<?> clazz, Predicate<Method>... predicates) {
    return Methods.getAll(clazz, predicates);
  }
}

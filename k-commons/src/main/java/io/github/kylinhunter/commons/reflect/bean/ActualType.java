package io.github.kylinhunter.commons.reflect.bean;

import lombok.Data;

/**
 * @author BiJi'an
 * @description
 * @date 2023-02-11 20:32
 */
@Data
public class ActualType {
  private final Class<?> rawType;
  private final Class<?>[] types;

  /**
   * @param index index
   * @return java.lang.Class<T>
   * @title getType
   * @description
   * @author BiJi'an
   * @date 2023-02-11 20:50
   */
  public <T> Class<T> getType(int index) {
    if (types != null && index >= 0 && index < types.length) {
      return (Class<T>) types[index];
    }
    return null;
  }
}

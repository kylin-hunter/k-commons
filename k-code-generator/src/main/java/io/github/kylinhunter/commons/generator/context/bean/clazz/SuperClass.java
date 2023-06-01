package io.github.kylinhunter.commons.generator.context.bean.clazz;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.ClassUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * @author BiJi'an
 * @description
 * @date 2023-02-19 17:17
 */
@Getter
@Setter
public class SuperClass {
  private String className;

  @Override
  public String toString() {
    return toString("extends ");
  }

  /**
   * @param prefix prefix
   * @return java.lang.String
   * @title prefix
   * @description
   * @author BiJi'an
   * @date 2023-02-19 23:43
   */
  public String toString(String prefix) {
    if (!StringUtils.isEmpty(className)) {
      return prefix + className;

    } else {
      return "";
    }
  }

  public void setClassName(String fullClassName) {
    if (!StringUtils.isEmpty(fullClassName)) {
      this.className = ClassUtils.getShortClassName(fullClassName);
    }
  }
}

package io.github.kylinhunter.commons.generator.context.bean.clazz;

import io.github.kylinhunter.commons.collections.CollectionUtils;
import io.github.kylinhunter.commons.collections.ListUtils;
import io.github.kylinhunter.commons.lang.strings.StringUtil;
import io.github.kylinhunter.commons.reflect.ClassUtil;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author BiJi'an
 * @description
 * @date 2023-02-19 17:17
 */
public class Interfaces {
  private final List<String> shortClassNames = ListUtils.newArrayList();
  private final List<String> fullClassNames = ListUtils.newArrayList();

  @Override
  public String toString() {
    return "implements " + shortClassNames.stream().collect(Collectors.joining(","));
  }

  /**
   * @param prefix prefix
   * @return java.lang.String
   * @title toString
   * @description
   * @author BiJi'an
   * @date 2023-02-19 17:20
   */
  public String toString(String prefix) {
    if (!CollectionUtils.isEmpty(shortClassNames)) {
      return prefix + String.join(",", shortClassNames);
    }
    return StringUtil.EMPTY;
  }

  /**
   * @param fullClassName className
   * @title add
   * @description
   * @author BiJi'an
   * @date 2023-02-19 17:24
   */
  public void add(String fullClassName) {
    if (!StringUtil.isEmpty(fullClassName)) {
      this.fullClassNames.add(fullClassName);
      this.shortClassNames.add(ClassUtil.getShortClassName(fullClassName));
    }
  }
}

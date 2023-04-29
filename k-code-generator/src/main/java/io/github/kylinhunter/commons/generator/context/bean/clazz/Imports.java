package io.github.kylinhunter.commons.generator.context.bean.clazz;

import io.github.kylinhunter.commons.collections.ListUtils;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

/**
 * @author BiJi'an
 * @description
 * @date 2023-02-19 17:17
 */
@Data
public class Imports {
  private final List<String> imports = ListUtils.newArrayList();

  @Override
  public String toString() {

    return toString("import ", ";", System.lineSeparator());
  }

  /**
   * @param prefix prefix
   * @param postfix postfix
   * @return java.lang.String
   * @title toString
   * @description
   * @author BiJi'an
   * @date 2023-02-19 17:32
   */
  public String toString(String prefix, String postfix) {
    return toString(prefix, postfix, System.lineSeparator());
  }

  /**
   * @param prefix prefix
   * @return java.lang.String
   * @title toString
   * @description
   * @author BiJi'an
   * @date 2023-02-19 17:20
   */
  public String toString(String prefix, String postfix, String separator) {
    return imports.stream().map(e -> prefix + e + postfix).collect(Collectors.joining(separator));
  }

  /**
   * @param fullClassName className
   * @return void
   * @title add
   * @description
   * @author BiJi'an
   * @date 2023-02-19 17:24
   */
  public void add(String fullClassName) {
    if (!StringUtils.isEmpty(fullClassName)) {
      if (!fullClassName.startsWith("java.lang")) {
        if (!imports.contains(fullClassName)) {
          this.imports.add(fullClassName);
        }
      }
    }
  }

  public void add(Class<?> clazz) {
    if (clazz != null) {
      this.add(clazz.getName());
    }
  }
}

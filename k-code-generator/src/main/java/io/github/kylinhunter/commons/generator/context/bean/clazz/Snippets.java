package io.github.kylinhunter.commons.generator.context.bean.clazz;

import io.github.kylinhunter.commons.collections.CollectionUtils;
import io.github.kylinhunter.commons.collections.ListUtils;
import io.github.kylinhunter.commons.collections.MapUtils;
import java.util.List;
import java.util.Map;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

/**
 * @author BiJi'an
 * @description
 * @date 2023-02-19 17:17
 */
@Getter
@Setter
public class Snippets {
  private Map<String, List<String>> snippets = MapUtils.newHashMap();

  /**
   * @param location location
   * @return java.lang.String
   * @title toString
   * @description
   * @author BiJi'an
   * @date 2023-02-19 00:42
   */
  public String toString(String location) {
    return toString(location, System.lineSeparator());
  }

  /**
   * @param separator separator
   * @return java.lang.String
   * @title toString
   * @description
   * @author BiJi'an
   * @date 2023-02-19 17:20
   */
  public String toString(String location, String separator) {
    List<String> tmpSnippets = this.snippets.get(location);
    if (!CollectionUtils.isEmpty(tmpSnippets)) {
      return String.join(separator, tmpSnippets);
    }
    return StringUtils.EMPTY;
  }

  /**
   * @param snippet snippet
   * @return void
   * @title add
   * @description
   * @author BiJi'an
   * @date 2023-02-19 17:24
   */
  public void add(String name, String snippet) {

    if (!StringUtils.isBlank(name) && !StringUtils.isBlank(snippet)) {
      this.snippets.compute(
          name,
          (k, v) -> {
            if (v == null) {
              v = ListUtils.newArrayList();
            }
            v.add(snippet);
            return v;
          });
    }
  }
}

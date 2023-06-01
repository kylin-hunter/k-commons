package io.github.kylinhunter.commons.generator.config.bean;

import io.github.kylinhunter.commons.collections.MapUtils;
import java.util.Map;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * @author BiJi'an
 * @description
 * @date 2023-02-26 23:39
 */
@Getter
@Setter
public class Database {
  protected String name;
  private Map<String, String> sqlTypes = MapUtils.newHashMap();

  /**
   * @param type type
   * @return java.lang.String
   * @title getSqlType
   * @description
   * @author BiJi'an
   * @date 2023-02-26 23:50
   */
  public String getSqlType(String type) {
    return sqlTypes.get(type);
  }
}

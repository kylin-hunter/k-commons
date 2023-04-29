package io.github.kylinhunter.commons.generator.config.bean;

import io.github.kylinhunter.commons.collections.ListUtils;
import io.github.kylinhunter.commons.collections.MapUtils;
import java.util.List;
import java.util.Map;
import lombok.Data;

@Data
public class Table {
  private String name;
  private List<String> skipColumns = ListUtils.newArrayList();
  private Map<String, String> columnTypes = MapUtils.newHashMap();

  /**
   * @param column column
   * @return java.lang.String
   * @title getColumnType
   * @description
   * @author BiJi'an
   * @date 2023-02-26 19:16
   */
  public String getColumnType(String column) {

    return columnTypes.get(column);
  }
}

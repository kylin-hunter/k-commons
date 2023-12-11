/*
 * Copyright (C) 2023 The k-commons Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.github.kylinhunter.commons.jdbc.meta.bean;

import io.github.kylinhunter.commons.collections.MapUtils;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import lombok.Data;

/**
 * @author BiJi'an
 * @description
 * @date 2023-01-10 11:11
 */
@Data
public class ColumnMetas {

  private List<ColumnMeta> columns;

  private Map<String, ColumnMeta> nameColumns = MapUtils.newHashMap();

  public ColumnMetas(List<ColumnMeta> columns) {
    Objects.requireNonNull(columns);
    this.columns = columns;
    this.columns.forEach(
        columnMeta -> nameColumns.put(columnMeta.getColumnName(), columnMeta));
  }

  /**
   * @param index index
   * @return io.github.kylinhunter.commons.jdbc.meta.bean.ColumnMeta
   * @title get
   * @description get
   * @author BiJi'an
   * @date 2023-12-12 00:01
   */
  public ColumnMeta getByIndex(int index) {
    if (index >= 0 && index < columns.size()) {
      return columns.get(index);
    }
    return null;
  }

  /**
   * @param columnName columnName
   * @return io.github.kylinhunter.commons.jdbc.meta.bean.ColumnMeta
   * @title getByName
   * @description getByName
   * @author BiJi'an
   * @date 2023-12-12 00:02
   */
  public ColumnMeta getByName(String columnName) {
    return nameColumns.get(columnName);
  }

  /**
   * @return int
   * @title size
   * @description size
   * @author BiJi'an
   * @date 2023-12-12 00:09
   */
  public int size() {
    return columns.size();
  }
}

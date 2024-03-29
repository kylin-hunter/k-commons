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
import java.sql.JDBCType;
import java.util.Map;
import lombok.Data;
import lombok.ToString;

/**
 * @author BiJi'an
 * @description
 * @date 2023-01-10 11:11
 */
@Data
@ToString(exclude = "rawMetadatas")
public class ColumnMeta {

  private int pos;

  /*
   * the raw metadata of column
   */
  private Map<String, Object> rawMetadatas = MapUtils.newHashMap();
  private String tableName; // table name
  private String columnName; // column name

  private int dataType;
  private String typeName;
  private int columnSize;
  private int decimalDigits;

  private boolean autoIncrement;
  private boolean nullable;
  private String remarks;

  private JDBCType jdbcType;
  private Class<?> javaClass;
}

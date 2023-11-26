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
package io.github.kylinhunter.commons.jdbc.meta;

import io.github.kylinhunter.commons.component.CF;
import io.github.kylinhunter.commons.jdbc.constant.DbType;
import io.github.kylinhunter.commons.jdbc.meta.column.ColumnReader;
import io.github.kylinhunter.commons.jdbc.meta.table.TableReader;

/**
 * @author BiJi'an
 * @description
 * @date 2023-11-26 23:38
 */
public class MetaReaderFactory {

  /**
   * @param dbType dbType
   * @return io.github.kylinhunter.commons.jdbc.meta.table.TableReader
   * @title getTableMetaReader
   * @description getTableMetaReader
   * @author BiJi'an
   * @date 2023-11-27 01:05
   */
  public static TableReader getTableMetaReader(DbType dbType) {
    return CF.get(dbType.getPrefix() + "TableReader");
  }

  /**
   * @param dbType dbType
   * @return io.github.kylinhunter.commons.jdbc.meta.column.ColumnReader
   * @title getColumnMetaReader
   * @description getColumnMetaReader
   * @author BiJi'an
   * @date 2023-11-27 01:06
   */
  public static ColumnReader getColumnMetaReader(DbType dbType) {
    return CF.get(dbType.getPrefix() + "ColumnReader");
  }
}

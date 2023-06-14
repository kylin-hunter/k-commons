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
package io.github.kylinhunter.commons.generator.context;

import io.github.kylinhunter.commons.collections.CollectionUtils;
import io.github.kylinhunter.commons.component.C;
import io.github.kylinhunter.commons.component.CSet;
import io.github.kylinhunter.commons.exception.check.ExceptionChecker;
import io.github.kylinhunter.commons.generator.config.bean.Database;
import io.github.kylinhunter.commons.generator.config.bean.Module;
import io.github.kylinhunter.commons.generator.config.bean.Table;
import io.github.kylinhunter.commons.generator.context.bean.module.ModuleInfo;
import io.github.kylinhunter.commons.generator.context.bean.module.TableInfo;
import io.github.kylinhunter.commons.generator.exception.CodeException;
import io.github.kylinhunter.commons.jdbc.meta.ColumnMetaReader;
import io.github.kylinhunter.commons.jdbc.meta.TableMetaReader;
import io.github.kylinhunter.commons.jdbc.meta.bean.ColumnMeta;
import io.github.kylinhunter.commons.jdbc.meta.bean.TableMeta;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;

/**
 * @author BiJi'an
 * @description
 * @date 2023-02-12 10:24
 */
@C
@RequiredArgsConstructor
public class ModuleInfoReader {

  @CSet private ColumnMetaReader columnMetaReader;

  @CSet private TableMetaReader tableMetaReader;

  /**
   * @return io.github.kylinhunter.commons.generator.context.bean.ModuleInfos
   * @title read
   * @description
   * @author BiJi'an
   * @date 2023-02-19 23:26
   */
  public ModuleInfo read(Database database, Module module) {
    return new ModuleInfo(database, module, toTable(module));
  }

  /**
   * @param module module
   * @return io.github.kylinhunter.commons.generator.context.bean.Table
   * @title toTable
   * @description
   * @author BiJi'an
   * @date 2023-03-19 22:36
   */
  private TableInfo toTable(Module module) {
    Table table = module.getTable();

    String databaseName = module.getDatabase().getName();
    TableInfo tableInfo = new TableInfo(table);

    TableMeta tableMetaData = tableMetaReader.getTableMetaData(databaseName, table.getName());
    ExceptionChecker.checkNotNull(tableMetaData, "tableMetaData can't be null");
    tableInfo.setTableMeta(tableMetaData);

    List<String> skipColumns = table.getSkipColumns();
    List<ColumnMeta> columnMetas =
        columnMetaReader.getColumnMetaData(databaseName, table.getName());
    if (CollectionUtils.isEmpty(columnMetas)) {
      throw new CodeException("no column from table=>" + table);
    } else {
      columnMetas =
          columnMetas.stream()
              .filter(
                  c -> {
                    if (skipColumns != null) {
                      return !skipColumns.contains(c.getColumnName());
                    } else {
                      return true;
                    }
                  })
              .collect(Collectors.toList());
      tableInfo.setColumnMetas(columnMetas);
    }
    return tableInfo;
  }
}

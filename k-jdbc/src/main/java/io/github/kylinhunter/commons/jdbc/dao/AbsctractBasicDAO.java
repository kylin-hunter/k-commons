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
package io.github.kylinhunter.commons.jdbc.dao;

import io.github.kylinhunter.commons.jdbc.exception.JdbcException;
import io.github.kylinhunter.commons.jdbc.meta.DatabaseMetaReader;
import io.github.kylinhunter.commons.jdbc.meta.table.TableReader;
import io.github.kylinhunter.commons.lang.strings.StringUtil;
import javax.sql.DataSource;

/**
 * @author BiJi'an
 * @description
 * @date 2023-12-10 00:17
 */
public class AbsctractBasicDAO extends AbstractDatabaseVisitor implements BaseDAO {

  private static final String DROP_TABLE_SQL = "DROP TABLE IF EXISTS %s ";

  protected DatabaseMetaReader databaseMetaReader;

  public AbsctractBasicDAO(DataSource dataSource, boolean dbConfigEnabled) {
    super(dataSource, dbConfigEnabled);
    databaseMetaReader = new DatabaseMetaReader(dataSource, dbConfigEnabled);
  }

  /**
   * @param tableName tableName
   * @param creatTableSql creatTableSql
   * @title ensureTableExists
   * @description ensureTableExists
   * @author BiJi'an
   * @date 2023-12-10 16:40
   */
  public void ensureTableExists(String tableName, String creatTableSql) {

    TableReader tableReader = this.databaseMetaReader.getTableReader();
    boolean exist = tableReader.exist(this.getDataSource(), "", tableName);
    if (!exist) {
      if (StringUtil.isBlank(creatTableSql)) {
        throw new JdbcException("creatTableSql can't be empty");
      }
      this.getSqlExecutor().execute(creatTableSql);
    }
  }

  /**
   * @param tableName tableName
   * @title dropTable
   * @description dropTable
   * @author BiJi'an
   * @date 2023-12-12 23:49
   */
  public void dropTable(String tableName) {
    String sql = String.format(DROP_TABLE_SQL, tableName);
    this.getSqlExecutor().execute(sql);
  }
}

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

import io.github.kylinhunter.commons.exception.check.ThrowChecker;
import io.github.kylinhunter.commons.jdbc.exception.JdbcException;
import io.github.kylinhunter.commons.jdbc.meta.bean.DatabaseMeta;
import io.github.kylinhunter.commons.jdbc.meta.column.ColumnReader;
import io.github.kylinhunter.commons.jdbc.meta.table.TableReader;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import javax.sql.DataSource;
import lombok.extern.slf4j.Slf4j;

/**
 * @author BiJi'an
 * @description
 * @date 2023-01-10 11:11
 */
@Slf4j
public class DatabaseMetaReader extends AbstractDatabaseManager {

  public DatabaseMetaReader(boolean dbConfigEnabled) {
    this(null, dbConfigEnabled);
  }

  public DatabaseMetaReader(DataSource dataSource, boolean dbConfigEnabled) {
    super(null, dataSource, dbConfigEnabled);
    DatabaseMeta metaData = this.getMetaData();
    this.dbType = metaData.getDbType();
  }

  /**
   * @return io.github.kylinhunter.commons.jdbc.meta.bean.DatabaseMeta
   * @title getMetaData
   * @description
   * @author BiJi'an
   * @date 2023-01-18 12:41
   */
  public DatabaseMeta getMetaData() {
    return this.getMetaData(this.getDataSource());
  }

  /**
   * @param dataSource dataSource
   * @return io.github.kylinhunter.commons.jdbc.meta.bean.DatabaseMeta
   * @title getMetaData
   * @description
   * @author BiJi'an
   * @date 2023-01-18 12:41
   */
  public DatabaseMeta getMetaData(DataSource dataSource) {
    ThrowChecker.checkNotNull(dataSource, "datasource can't be null");

    try (Connection connection = dataSource.getConnection()) {
      return getMetaData(connection);
    } catch (Exception e) {
      throw new JdbcException("getDatabaseMetaData error", e);
    }
  }

  /**
   * @param connection connection
   * @return io.github.kylinhunter.commons.jdbc.meta.bean.DatabaseMeta
   * @title getMetaData
   * @description
   * @author BiJi'an
   * @date 2023-01-18 12:41
   */
  public DatabaseMeta getMetaData(Connection connection) {
    try {

      DatabaseMetaData metaData = connection.getMetaData();
      DatabaseMeta databaseMeta = new DatabaseMeta();
      databaseMeta.setUrl(metaData.getURL());
      databaseMeta.setProductName(metaData.getDatabaseProductName());
      databaseMeta.setVersion(metaData.getDatabaseProductVersion());
      databaseMeta.setDriverName(metaData.getDriverName());

      return databaseMeta;
    } catch (Exception e) {
      throw new JdbcException("getDatabaseMetaData error", e);
    }
  }

  /**
   * @return io.github.kylinhunter.commons.jdbc.meta.table.TableReader
   * @title getTableMetaReader
   * @description getTableMetaReader
   * @author BiJi'an
   * @date 2023-12-11 00:44
   */
  public TableReader getTableMetaReader() {
    return MetaReaderFactory.getTableMetaReader(this.dbType, true);
  }

  /**
   * @return io.github.kylinhunter.commons.jdbc.meta.column.ColumnReader
   * @title getColumnMetaReader
   * @description getColumnMetaReader
   * @author BiJi'an
   * @date 2023-12-11 00:45
   */
  public ColumnReader getColumnMetaReader() {
    return MetaReaderFactory.getColumnMetaReader(this.dbType, true);
  }

}
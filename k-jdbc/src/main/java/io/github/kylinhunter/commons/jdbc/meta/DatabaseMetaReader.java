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

import io.github.kylinhunter.commons.collections.MapUtils;
import io.github.kylinhunter.commons.exception.embed.UnsupportedException;
import io.github.kylinhunter.commons.jdbc.constant.DbType;
import io.github.kylinhunter.commons.jdbc.meta.bean.DatabaseMeta;
import io.github.kylinhunter.commons.jdbc.meta.column.ColumnReader;
import io.github.kylinhunter.commons.jdbc.meta.column.imp.MysqlColumnReader;
import io.github.kylinhunter.commons.jdbc.meta.column.imp.OracleColumnReader;
import io.github.kylinhunter.commons.jdbc.meta.column.imp.SqlServerColumnReader;
import io.github.kylinhunter.commons.jdbc.meta.table.MysqlTableReader;
import io.github.kylinhunter.commons.jdbc.meta.table.OracleTableReader;
import io.github.kylinhunter.commons.jdbc.meta.table.SqlServerTableReader;
import io.github.kylinhunter.commons.jdbc.meta.table.TableReader;
import java.util.Map;
import javax.sql.DataSource;
import lombok.extern.slf4j.Slf4j;

/**
 * @author BiJi'an
 * @description
 * @date 2023-01-10 11:11
 */
@Slf4j
public class DatabaseMetaReader extends AbstractDatabaseManager {

  private static final Map<String, Object> META_SERVICES = MapUtils.newHashMap();

  public DatabaseMetaReader(boolean dbConfigEnabled) {
    this(null, dbConfigEnabled);
  }

  public DatabaseMetaReader(DataSource dataSource, boolean dbConfigEnabled) {
    super(dataSource, dbConfigEnabled);
    DatabaseMeta metaData = this.getMetaData();
    this.dbType = metaData.getDbType();
  }

  /**
   * @return io.github.kylinhunter.commons.jdbc.meta.table.TableReader
   * @title getTableMetaReader
   * @description getTableMetaReader
   * @author BiJi'an
   * @date 2023-12-11 00:44
   */
  public TableReader getTableMetaReader() {
    return this.getTableMetaReader(this.dbType, this.dbConfigEnabled);
  }

  /**
   * @return io.github.kylinhunter.commons.jdbc.meta.column.ColumnReader
   * @title getColumnMetaReader
   * @description getColumnMetaReader
   * @author BiJi'an
   * @date 2023-12-11 00:45
   */
  public ColumnReader getColumnMetaReader() {
    return this.getColumnMetaReader(this.dbType, this.dbConfigEnabled);
  }

  /**
   * @param dbType dbType
   * @return io.github.kylinhunter.commons.jdbc.meta.table.TableReader
   * @title getTableMetaReader
   * @description getTableMetaReader
   * @author BiJi'an
   * @date 2023-11-27 01:05
   */
  private synchronized TableReader getTableMetaReader(DbType dbType, boolean dbConfigEnabled) {
    String key = dbType.toString() + dbConfigEnabled;
    return (TableReader)
        META_SERVICES.compute(
            key,
            (k, v) -> {
              switch (dbType) {
                case MYSQL:
                  {
                    v = new MysqlTableReader(dbConfigEnabled);
                    break;
                  }
                case ORACLE:
                  {
                    v = new OracleTableReader(dbConfigEnabled);
                    break;
                  }
                case SQL_SERVER:
                  {
                    v = new SqlServerTableReader(dbConfigEnabled);
                    break;
                  }
                default:
                  {
                    throw new UnsupportedException("unsupported dbType:" + dbType);
                  }
              }
              return v;
            });
  }

  /**
   * @param dbType dbType
   * @return io.github.kylinhunter.commons.jdbc.meta.column.ColumnReader
   * @title getColumnMetaReader
   * @description getColumnMetaReader
   * @author BiJi'an
   * @date 2023-11-27 01:06
   */
  private synchronized ColumnReader getColumnMetaReader(DbType dbType, boolean dbConfigEnabled) {
    String key = dbType.toString() + dbConfigEnabled;
    return (ColumnReader)
        META_SERVICES.compute(
            key,
            (k, v) -> {
              switch (dbType) {
                case MYSQL:
                  {
                    v = new MysqlColumnReader(dbConfigEnabled);
                    break;
                  }
                case ORACLE:
                  {
                    v = new OracleColumnReader(dbConfigEnabled);
                    break;
                  }
                case SQL_SERVER:
                  {
                    v = new SqlServerColumnReader(dbConfigEnabled);
                    break;
                  }
                default:
                  {
                    throw new UnsupportedException("unsupported dbType:" + dbType);
                  }
              }
              return v;
            });
  }
}

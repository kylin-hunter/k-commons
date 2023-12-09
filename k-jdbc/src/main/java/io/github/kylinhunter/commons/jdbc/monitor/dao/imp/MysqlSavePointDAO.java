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
package io.github.kylinhunter.commons.jdbc.monitor.dao.imp;

import io.github.kylinhunter.commons.component.CF;
import io.github.kylinhunter.commons.jdbc.config.url.JdbcUrl;
import io.github.kylinhunter.commons.jdbc.constant.DbType;
import io.github.kylinhunter.commons.jdbc.execute.SqlExecutor;
import io.github.kylinhunter.commons.jdbc.execute.SqlFileReader;
import io.github.kylinhunter.commons.jdbc.meta.AbstractDatabaseManager;
import io.github.kylinhunter.commons.jdbc.meta.DatabaseMetaReader;
import io.github.kylinhunter.commons.jdbc.meta.MetaReaderFactory;
import io.github.kylinhunter.commons.jdbc.meta.bean.DatabaseMeta;
import io.github.kylinhunter.commons.jdbc.meta.table.TableReader;
import io.github.kylinhunter.commons.jdbc.monitor.dao.SavePointDAO;
import io.github.kylinhunter.commons.jdbc.monitor.dao.entity.SavePoint;
import java.util.List;
import javax.sql.DataSource;
import org.apache.commons.dbutils.handlers.BeanHandler;

/**
 * @author BiJi'an
 * @description
 * @date 2023-11-28 23:36
 */
public class MysqlSavePointDAO extends AbstractDatabaseManager implements SavePointDAO {

  public static final String TABLE_NAME = "k_binlog_progress";
  public static final int DEFAULT_ID = 0;

  private static final String SELECT_SQL =
      "select name,position from " + TABLE_NAME + " where id=" + DEFAULT_ID;
  private static final String INSERT_SQL =
      "insert into " + TABLE_NAME + "(id, name, position) values(" + DEFAULT_ID + ",?,?)";
  private static final String UPDATE_SQL =
      "update   " + TABLE_NAME + "  set name=?, position=? where id=" + DEFAULT_ID;

  private static final String INIT_SQL = "io/github/kylinhunter/commons/jdbc/binlog/binlog.sql";

  private final BeanHandler<SavePoint> beanHandler = new BeanHandler<>(SavePoint.class);
  private final TableReader tableReader;
  private final DatabaseMetaReader databaseMetaReader;

  public MysqlSavePointDAO() {
    this(null);
  }

  public MysqlSavePointDAO(DataSource dataSource) {
    super(dataSource);
    this.dbType = DbType.MYSQL;
    this.tableReader = MetaReaderFactory.getTableMetaReader(this.dbType);
    this.databaseMetaReader = CF.get(DatabaseMetaReader.class);
  }

  @Override
  public void update(SavePoint savePoint) {
    String name = savePoint.getName();
    long position = savePoint.getPosition();
    this.getSqlExecutor().execute(UPDATE_SQL, name, position);
  }

  @Override
  public JdbcUrl getJdbcUrl() {
    return this.databaseMetaReader.getMetaData(this.getDataSource()).getJdbcUrl();
  }

  @Override
  public void ensureTableExists() {

    boolean exist = this.tableReader.exist(this.getDataSource(), "", TABLE_NAME);
    if (!exist) {
      List<String> sqlLines = SqlFileReader.read(INIT_SQL);
      this.getSqlExecutor().execute(sqlLines, true);
    }
  }

  @Override
  public void save(SavePoint savePoint) {
    String name = savePoint.getName();
    long position = savePoint.getPosition();
    this.getSqlExecutor().execute(INSERT_SQL, name, position);
  }

  @Override
  public SavePoint get() {
    return this.getSqlExecutor().query(SELECT_SQL, beanHandler);
  }

  /**
   * @return javax.sql.DataSource
   * @title getDefaultDataSource
   * @description getDefaultDataSource
   * @author BiJi'an
   * @date 2023-12-09 20:10
   */
  @Override
  public DataSource getDefaultDataSource() {
    return dataSourceManager.getByNo(1);
  }

  /**
   * @return io.github.kylinhunter.commons.jdbc.execute.SqlExecutor
   * @title getDefaultDataSource
   * @description getSqlExecutor
   * @author BiJi'an
   * @date 2023-12-03 15:45
   */
  @Override
  public SqlExecutor getDefaultSqlExecutor() {
    return dataSourceManager.getSqlExecutorByNo(1);
  }

  /**
   * @return io.github.kylinhunter.commons.jdbc.meta.bean.DatabaseMeta
   * @title getDBMetaData
   * @description getDBMetaData
   * @author BiJi'an
   * @date 2023-12-09 15:22
   */
  public DatabaseMeta getDBMetaData() {
    return databaseMetaReader.getMetaData(this.getDataSource());
  }
}

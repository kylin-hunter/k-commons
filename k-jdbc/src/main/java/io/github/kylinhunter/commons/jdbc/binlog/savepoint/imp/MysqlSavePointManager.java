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
package io.github.kylinhunter.commons.jdbc.binlog.savepoint.imp;

import io.github.kylinhunter.commons.collections.CollectionUtils;
import io.github.kylinhunter.commons.jdbc.binlog.savepoint.SavePointManager;
import io.github.kylinhunter.commons.jdbc.binlog.savepoint.bean.SavePoint;
import io.github.kylinhunter.commons.jdbc.execute.SqlExecutor;
import io.github.kylinhunter.commons.jdbc.execute.SqlFileReader;
import io.github.kylinhunter.commons.jdbc.meta.AbstractDatabaseManager;
import java.util.List;
import javax.sql.DataSource;
import org.apache.commons.dbutils.handlers.BeanListHandler;

/**
 * @author BiJi'an
 * @description
 * @date 2023-11-28 23:36
 */
public class MysqlSavePointManager extends AbstractDatabaseManager implements SavePointManager {

  public static final String TABLE_NAME = "k_binlog_progress";
  public static final int DEFAULT_ID = 0;

  private static final String SELECT_SQL =
      "select name,position from " + TABLE_NAME + " where id=" + DEFAULT_ID;
  private static final String INSERT_SQL =
      "insert into " + TABLE_NAME + "(id, name, position) values(" + DEFAULT_ID + ",?,?)";
  private static final String UPDATE_SQL =
      "update   " + TABLE_NAME + "  set name=?, position=? where id=" + DEFAULT_ID;

  private static final String INIT_SQL_PATH =
      "io/github/kylinhunter/commons/jdbc/binlog/binlog.sql";

  private final BeanListHandler<SavePoint> beanListHandler = new BeanListHandler<>(SavePoint.class);

  public MysqlSavePointManager() {
    this(null);
  }

  public MysqlSavePointManager(DataSource dataSource) {
    super(dataSource);
  }

  @Override
  public void reset() {
    String name = DEAFULT_SAVEPOINT.getName();
    long position = DEAFULT_SAVEPOINT.getPosition();
    this.getSqlExecutor().execute(UPDATE_SQL, name, position);
  }

  @Override
  public void save(SavePoint savePoint) {
    String name = savePoint.getName();
    long position = savePoint.getPosition();
    this.getSqlExecutor().execute(UPDATE_SQL, name, position);
  }

  @Override
  public SavePoint getLatest() {
    List<SavePoint> savePoints = this.getSqlExecutor().query(SELECT_SQL, beanListHandler);
    if (!CollectionUtils.isEmpty(savePoints)) {
      return savePoints.get(0);
    }
    return null;
  }

  @Override
  public void init() {
    List<String> sqlLines = SqlFileReader.read(INIT_SQL_PATH);
    this.getSqlExecutor().execute(sqlLines, true);

    SavePoint savePoint = this.getLatest();
    if (savePoint == null) {
      this.getSqlExecutor()
          .execute(INSERT_SQL, DEAFULT_SAVEPOINT.getName(), DEAFULT_SAVEPOINT.getPosition());
    }
  }

  protected DataSource getDataSource() {
    if (dataSource != null) {
      return dataSource;
    }

    return dataSourceManager.getByNo(1);
  }

  /**
   * @return io.github.kylinhunter.commons.jdbc.execute.SqlExecutor
   * @title getDefaultDataSource
   * @description getSqlExecutor
   * @author BiJi'an
   * @date 2023-12-03 15:45
   */
  protected SqlExecutor getSqlExecutor() {
    if (sqlExecutor != null) {
      return sqlExecutor;
    }
    return dataSourceManager.getSqlExecutorByNo(1);
  }
}

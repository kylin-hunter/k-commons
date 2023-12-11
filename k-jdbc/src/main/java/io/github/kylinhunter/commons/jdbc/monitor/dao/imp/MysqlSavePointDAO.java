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

import io.github.kylinhunter.commons.jdbc.dao.BasicDAO;
import io.github.kylinhunter.commons.jdbc.execute.SqlReader;
import io.github.kylinhunter.commons.jdbc.monitor.dao.SavePointDAO;
import io.github.kylinhunter.commons.jdbc.monitor.dao.entity.SavePoint;
import io.github.kylinhunter.commons.jdbc.url.JdbcUrl;
import javax.sql.DataSource;
import org.apache.commons.dbutils.handlers.BeanHandler;

/**
 * @author BiJi'an
 * @description
 * @date 2023-11-28 23:36
 */
public class MysqlSavePointDAO extends BasicDAO implements SavePointDAO {

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

  public MysqlSavePointDAO() {

    this(null, true);
  }

  public MysqlSavePointDAO(DataSource dataSource, boolean dbConfigEnabled) {
    super(dataSource, dbConfigEnabled);
  }

  @Override
  public void update(SavePoint savePoint) {
    String name = savePoint.getName();
    long position = savePoint.getPosition();
    this.getSqlExecutor().execute(UPDATE_SQL, name, position);
  }

  @Override
  public JdbcUrl getJdbcUrl() {
    return this.getDBMetaData().getJdbcUrl();
  }

  @Override
  public void ensureTableExists() {
    super.ensureTableExists(TABLE_NAME, SqlReader.readSql(INIT_SQL));
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
}

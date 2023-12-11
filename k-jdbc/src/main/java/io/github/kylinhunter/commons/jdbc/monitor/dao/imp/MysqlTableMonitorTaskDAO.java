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

import io.github.kylinhunter.commons.jdbc.execute.SqlReader;
import io.github.kylinhunter.commons.jdbc.monitor.dao.BasicDAO;
import io.github.kylinhunter.commons.jdbc.monitor.dao.TableMonitorTaskDAO;
import io.github.kylinhunter.commons.jdbc.monitor.dao.entity.TableMonitorTask;
import javax.sql.DataSource;
import org.apache.commons.dbutils.handlers.BeanHandler;

/**
 * @author BiJi'an
 * @description
 * @date 2023-12-03 19:50
 */
public class MysqlTableMonitorTaskDAO extends BasicDAO implements TableMonitorTaskDAO {

  private static final String INIT_SQL =
      "io/github/kylinhunter/commons/jdbc/monitor/table-monitor-task.sql";

  private static final String INSERT_SQL =
      "insert into %s" + " (id, data_id, op,status) values(?,?,?,?)";

  private static final String DELETE_SQL = "delete from %s  where id=?";
  private static final String UPDATE_SQL =
      "update %s" + " set op=?,status=? where id=? and data_id=?";

  private static final String SELECT_SQL =
      "select  id,  data_id as dataId, op ,status from %s" + " where id=? and data_id=?";
  private final BeanHandler<TableMonitorTask> beanHandler =
      new BeanHandler<>(TableMonitorTask.class);

  public MysqlTableMonitorTaskDAO() {
    this(null, true);
  }

  public MysqlTableMonitorTaskDAO(DataSource dataSource, boolean dbConfigEnabled) {
    super(dataSource, dbConfigEnabled);
  }

  /**
   * @param tableName tableName
   * @param tableMonitorTask scanProcessor
   * @title save
   * @description save
   * @author BiJi'an
   * @date 2023-12-09 14:24
   */
  public void save(String tableName, TableMonitorTask tableMonitorTask) {
    String sql = String.format(INSERT_SQL, tableName);
    this.getSqlExecutor()
        .execute(
            sql,
            tableMonitorTask.getId(),
            tableMonitorTask.getDataId(),
            tableMonitorTask.getOp(),
            tableMonitorTask.getStatus());
  }

  /**
   * @param tableName bizTableName
   * @param bizTableName scanProcessor
   * @title clean
   * @description clean
   * @author BiJi'an
   * @date 2023-12-09 14:44
   */
  @Override
  public void clean(String tableName, String bizTableName) {
    String sql = String.format(DELETE_SQL, tableName);
    this.getSqlExecutor().execute(sql, bizTableName);
  }

  /**
   * @param tableMonitorTask scanProcessor
   * @title update
   * @description update
   * @author BiJi'an
   * @date 2023-12-09 14:24
   */
  @Override
  public void update(String tableName, TableMonitorTask tableMonitorTask) {
    String sql = String.format(UPDATE_SQL, tableName);

    this.getSqlExecutor()
        .execute(
            sql,
            tableMonitorTask.getOp(),
            tableMonitorTask.getStatus(),
            tableMonitorTask.getId(),
            tableMonitorTask.getDataId());
  }

  /**
   * @param tableName tableName
   * @param bizTableName bizTableName
   * @param dataId dataId
   * @return io.github.kylinhunter.commons.jdbc.monitor.dao.entity.ScanProcessor
   * @title findById
   * @description findById
   * @author BiJi'an
   * @date 2023-12-09 14:25
   */
  public TableMonitorTask findById(String tableName, String bizTableName, String dataId) {
    String sql = String.format(SELECT_SQL, tableName);

    return this.getSqlExecutor().query(sql, beanHandler, bizTableName, dataId);
  }

  /**
   * @param tableName tableName
   * @title ensureTableExists
   * @description ensureTableExists
   * @author BiJi'an
   * @date 2023-12-09 14:22
   */
  @Override
  public void ensureTableExists(String tableName) {
    String creatTableSql = SqlReader.readSql(INIT_SQL);
    creatTableSql = String.format(creatTableSql, tableName);
    super.ensureTableExists(tableName, creatTableSql);
  }
}

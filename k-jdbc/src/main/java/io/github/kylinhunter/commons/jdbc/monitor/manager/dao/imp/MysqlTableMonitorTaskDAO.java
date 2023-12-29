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
package io.github.kylinhunter.commons.jdbc.monitor.manager.dao.imp;

import io.github.kylinhunter.commons.jdbc.dao.AbsctractBasicDAO;
import io.github.kylinhunter.commons.jdbc.execute.SqlReader;
import io.github.kylinhunter.commons.jdbc.monitor.manager.dao.TableMonitorTaskDAO;
import io.github.kylinhunter.commons.jdbc.monitor.manager.dao.constant.RowStatus;
import io.github.kylinhunter.commons.jdbc.monitor.manager.dao.entity.TableMonitorTask;
import java.time.LocalDateTime;
import java.util.List;
import javax.sql.DataSource;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

/**
 * @author BiJi'an
 * @description
 * @date 2023-12-03 19:50
 */
public class MysqlTableMonitorTaskDAO extends AbsctractBasicDAO implements TableMonitorTaskDAO {

  private static final String INIT_SQL =
      "io/github/kylinhunter/commons/jdbc/monitor/table-monitor-task.sql";

  private static final String INSERT_SQL =
      "insert into %s" + " (db,table_name, data_id, op,status) values(?,?,?,?,?)";

  private static final String DELETE_SQL = "delete from %s  where db=? and table_name=?";

  private static final String SQL_UPDATE =
      "update %s" + " set op=?,retry_times=?,status=? where db=? and table_name=? and data_id=?";

  private static final String SQL_SELECT =
      "select  db ,table_name as tableName,  data_id as dataId, retry_times as retryTimes,op ,"
          + "status from %s  where db=? and table_name=? and data_id=?";

  private static final String SQL_RESET =
      "update  %s  set retry_times=0 ,status="
          + RowStatus.WAIT.getCode()
          + " ,sys_auto_updated=sys_auto_updated"
          + " where db=? and table_name=?";

  private static final String SQL_RESET_ID =
      "update  %s  set status="
          + RowStatus.WAIT.getCode()
          + " where db=?"
          + " and table_name=? and data_id=? ";

  private static final String SQL_FIND_WAIT_DATAS =
      "select  db ,table_name as tableName,  data_id as dataId,retry_times as retryTimes "
          + ",op ,status from %s"
          + " where status="
          + RowStatus.WAIT.getCode()
          + " and   sys_auto_updated <? "
          + " order by sys_auto_updated desc limit ?";

  private static final String SQL_UPDATE_STATUS_BY_STATUS =
      "update  %s  set status=? where db=? and table_name=? and data_id=?  and status=?";

  private static final String SQL_RETRY_BATCH =
      "update  %s  set status="
          + RowStatus.WAIT.getCode()
          + " ,sys_auto_updated=sys_auto_updated "
          + " , retry_times=retry_times+1 "
          + " where status=?  and retry_times<=? and sys_auto_updated <?";

  private static final String SQL_ERROR_BATCH =
      "update  %s  set status="
          + RowStatus.ERROR.getCode()
          + " ,sys_auto_updated=sys_auto_updated "
          + " where  retry_times>? and sys_auto_updated <?";

  private final BeanHandler<TableMonitorTask> beanHandler =
      new BeanHandler<>(TableMonitorTask.class);

  private final BeanListHandler<TableMonitorTask> beanHandlers =
      new BeanListHandler<>(TableMonitorTask.class);

  public MysqlTableMonitorTaskDAO() {
    this(null, true);
  }

  public MysqlTableMonitorTaskDAO(DataSource dataSource) {
    super(dataSource, false);
  }

  public MysqlTableMonitorTaskDAO(DataSource dataSource, boolean dbConfigEnabled) {
    super(dataSource, dbConfigEnabled);
  }

  /**
   * @param destination      destination
   * @param tableMonitorTask scanProcessor
   * @title save
   * @description save
   * @author BiJi'an
   * @date 2023-12-09 14:24
   */
  public void save(String destination, TableMonitorTask tableMonitorTask) {
    String sql = String.format(INSERT_SQL, destination);
    this.getSqlExecutor()
        .execute(
            sql,
            tableMonitorTask.getDb(),
            tableMonitorTask.getTableName(),
            tableMonitorTask.getDataId(),
            tableMonitorTask.getOp(),
            tableMonitorTask.getStatus());
  }

  /**
   * @param destination destination
   * @param tableName   tableName
   * @title clean
   * @description clean
   * @author BiJi'an
   * @date 2023-12-09 14:44
   */
  @Override
  public void clean(String destination, String db, String tableName) {
    String sql = String.format(DELETE_SQL, destination);
    this.getSqlExecutor().execute(sql, db, tableName);
  }

  /**
   * @param tableMonitorTask scanProcessor
   * @title update
   * @description update
   * @author BiJi'an
   * @date 2023-12-09 14:24
   */
  @Override
  public void update(String destination, TableMonitorTask tableMonitorTask) {
    String sql = String.format(SQL_UPDATE, destination);

    this.getSqlExecutor()
        .execute(
            sql,
            tableMonitorTask.getOp(),
            tableMonitorTask.getRetryTimes(),
            tableMonitorTask.getStatus(),
            tableMonitorTask.getDb(),
            tableMonitorTask.getTableName(),
            tableMonitorTask.getDataId());
  }

  /**
   * @param destination  tableName
   * @param bizTableName bizTableName
   * @param dataId       dataId
   * @return io.github.kylinhunter.commons.jdbc.monitor.dao.entity.ScanProcessor
   * @title findById
   * @description findById
   * @author BiJi'an
   * @date 2023-12-09 14:25
   */
  public TableMonitorTask findById(
      String destination, String db, String bizTableName, String dataId) {
    String sql = String.format(SQL_SELECT, destination);

    return this.getSqlExecutor().query(sql, beanHandler, db, bizTableName, dataId);
  }

  /**
   * @param destination tableName
   * @title ensureTableExists
   * @description ensureTableExists
   * @author BiJi'an
   * @date 2023-12-09 14:22
   */
  @Override
  public void ensureDestinationExists(String destination) {
    String creatTableSql = SqlReader.readSql(INIT_SQL);
    creatTableSql = String.format(creatTableSql, destination);
    super.ensureTableExists(destination, creatTableSql);
  }

  /**
   * @param destination destination
   * @param db          db
   * @param tableName   tableName
   * @title reset
   * @description reset
   * @author BiJi'an
   * @date 2023-12-23 21:40
   */
  @Override
  public int reset(String destination, String db, String tableName) {
    String sql = String.format(SQL_RESET, destination);
    return this.getSqlExecutor().execute(sql, db, tableName);
  }

  /**
   * @param destination destination
   * @param db          db
   * @param tableName   tableName
   * @title reset
   * @description reset
   * @author BiJi'an
   * @date 2023-12-23 21:40
   */
  @Override
  public int reset(String destination, String db, String tableName, String dataId) {
    String sql = String.format(SQL_RESET_ID, destination);
    return this.getSqlExecutor().execute(sql, db, tableName, dataId);
  }

  /**
   * @param destination destination
   * @param limit       limit
   * @param time        time
   * @return io.github.kylinhunter.commons.jdbc.monitor.manager.dao.entity.TableMonitorTask
   * @title findWaitDatas
   * @description findWaitDatas
   * @author BiJi'an
   * @date 2023-12-23 21:48
   */
  public List<TableMonitorTask> findWaitDatas(String destination, LocalDateTime time, int limit) {
    String sql = String.format(SQL_FIND_WAIT_DATAS, destination);

    return this.getSqlExecutor().query(sql, beanHandlers, time, limit);
  }

  /**
   * @param destination  destination
   * @param db           db
   * @param tableName    tableName
   * @param dataId       dataId
   * @param newRowStatus newStatus
   * @param oldRowStatus oldStatus
   * @title updateStatusByStatus
   * @description updateStatusByStatus
   * @author BiJi'an
   * @date 2023-12-23 21:55
   */
  @Override
  public int updateStatusByStatus(
      String destination,
      String db,
      String tableName,
      String dataId,
      RowStatus newRowStatus,
      RowStatus oldRowStatus) {
    String sql = String.format(SQL_UPDATE_STATUS_BY_STATUS, destination);
    return this.getSqlExecutor()
        .execute(sql, newRowStatus.getCode(), db, tableName, dataId, oldRowStatus.getCode());
  }

  /**
   * @param destination destination
   * @param startDate   startDate
   * @return int
   * @title batchRetry
   * @description batchRetry
   * @author BiJi'an
   * @date 2023-12-24 00:25
   */
  public int batchRetry(
      String destination, RowStatus status, int maxRetry, LocalDateTime startDate) {
    String sql = String.format(SQL_RETRY_BATCH, destination);
    return this.getSqlExecutor().execute(sql, status.getCode(), maxRetry, startDate);
  }

  /**
   * @param destination destination
   * @param maxRetry    maxRetry
   * @param startDate   startDate
   * @return int
   * @title batchError
   * @description batchError
   * @author BiJi'an
   * @date 2023-12-24 00:51
   */
  public int batchError(String destination, int maxRetry, LocalDateTime startDate) {
    String sql = String.format(SQL_ERROR_BATCH, destination);
    return this.getSqlExecutor().execute(sql, maxRetry, startDate);
  }
}

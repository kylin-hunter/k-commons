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
package io.github.kylinhunter.commons.jdbc.monitor.manager;

import io.github.kylinhunter.commons.exception.embed.UnsupportedException;
import io.github.kylinhunter.commons.jdbc.constant.DbType;
import io.github.kylinhunter.commons.jdbc.dao.AbstractDatabaseVisitor;
import io.github.kylinhunter.commons.jdbc.monitor.bean.Table;
import io.github.kylinhunter.commons.jdbc.monitor.manager.dao.TableMonitorTaskDAO;
import io.github.kylinhunter.commons.jdbc.monitor.manager.dao.constant.RowOP;
import io.github.kylinhunter.commons.jdbc.monitor.manager.dao.constant.RowStatus;
import io.github.kylinhunter.commons.jdbc.monitor.manager.dao.entity.TableMonitorTask;
import io.github.kylinhunter.commons.jdbc.monitor.manager.dao.imp.MysqlTableMonitorTaskDAO;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import javax.sql.DataSource;
import lombok.extern.slf4j.Slf4j;

/**
 * @author BiJi'an
 * @description
 * @date 2023-12-16 16:06
 */
@Slf4j
public class TableTaskManager extends AbstractDatabaseVisitor {

  private final TableMonitorTaskDAO dao;

  public TableTaskManager(DataSource dataSource) {
    super(dataSource, false);
    if (this.dbType == DbType.MYSQL) {
      this.dao = new MysqlTableMonitorTaskDAO(dataSource);
    } else {
      throw new UnsupportedException("unsupported dbType=" + dbType);
    }
  }

  /**
   * @title ensureTableExists
   * @description ensureTableExists
   * @author BiJi'an
   * @date 2023-12-09 00:16
   */
  public void ensureDestinationExists(String destination) {
    dao.ensureDestinationExists(destination);
  }

  /**
   * @param destination destination
   * @param dataId dataId
   * @param rowOP rowOP
   * @title saveOrUpdate
   * @description saveOrUpdate
   * @author BiJi'an
   * @date 2023-12-12 01:43
   */
  private void save(
      String destination, String database, String tableName, String dataId, RowOP rowOP) {
    TableMonitorTask tableMonitorTask = this.dao.findById(destination, database, tableName, dataId);
    if (tableMonitorTask == null) {
      tableMonitorTask = new TableMonitorTask();
      tableMonitorTask.setDb(database);
      tableMonitorTask.setTableName(tableName);
      tableMonitorTask.setDataId(dataId);
      tableMonitorTask.setRetryTimes(0);
      rowOP = rowOP == null ? RowOP.INSERT : rowOP;
      tableMonitorTask.setStatus(RowStatus.WAIT.getCode());
      tableMonitorTask.setOp(rowOP.getCode());
      this.dao.save(destination, tableMonitorTask);
    } else {
      tableMonitorTask.setRetryTimes(0);
      rowOP = rowOP == null ? RowOP.UPDATE : rowOP;
      tableMonitorTask.setStatus(RowStatus.WAIT.getCode());
      tableMonitorTask.setOp(rowOP.getCode());
      this.dao.update(destination, tableMonitorTask);
    }
  }

  /**
   * @param table table
   * @param dataId dataId
   * @title saveOrUpdate
   * @description saveOrUpdate
   * @author BiJi'an
   * @date 2023-12-24 01:19
   */
  public void save(Table table, String dataId) {
    this.save(table.getDestination(), table.getDatabase(), table.getTableName(), dataId, null);
  }

  /**
   * @param table table
   * @param dataId dataId
   * @param rowOp rowOp
   * @title save
   * @description save
   * @author BiJi'an
   * @date 2023-12-30 10:47
   */
  public void save(Table table, String dataId, RowOP rowOp) {
    this.save(table.getDestination(), table.getDatabase(), table.getTableName(), dataId, rowOp);
  }

  /**
   * @param destination destination
   * @param tableName tableName
   * @title clean
   * @description clean
   * @author BiJi'an
   * @date 2023-12-16 16:15
   */
  public void clean(String destination, String database, String tableName) {
    dao.clean(destination, database, tableName);
  }

  /**
   * @param table table
   * @title reset
   * @description reset
   * @author BiJi'an
   * @date 2023-12-23 23:49
   */
  public void reset(Table table) {
    String destination = table.getDestination();
    String database = table.getDatabase();
    String tableName = table.getTableName();
    dao.reset(destination, database, tableName);
    log.info("reset status {}/{}{}/{}", destination, database, tableName, RowStatus.WAIT);
  }

  /**
   * @param dest dest
   * @param endTime endTime
   * @param maxRetry maxRetry
   * @param limit limit
   * @return io.github.kylinhunter.commons.jdbc.monitor.manager.dao.entity.TableMonitorTask
   * @title findWaitDatas
   * @description findWaitDatas
   * @author BiJi'an
   * @date 2023-12-23 22:10
   */
  public List<TableMonitorTask> findWaitDatas(
      String dest, LocalDateTime endTime, int maxRetry, int limit) {

    List<TableMonitorTask> datas = dao.findWaitDatas(dest, endTime, maxRetry, limit);

    if (datas.size() > 0) {
      return datas.stream()
          .filter(
              e ->
                  dao.updateStatusByStatus(
                          dest,
                          e.getDb(),
                          e.getTableName(),
                          e.getDataId(),
                          RowStatus.PROCESSING,
                          RowStatus.WAIT)
                      > 0)
          .collect(Collectors.toList());
    }
    return Collections.emptyList();
  }

  /**
   * @param destination destination
   * @param task task
   * @title setSuccess
   * @description setSuccess
   * @author BiJi'an
   * @date 2023-12-23 23:36
   */
  public boolean setSuccess(String destination, TableMonitorTask task) {
    return dao.updateStatusByStatus(
            destination,
            task.getDb(),
            task.getTableName(),
            task.getDataId(),
            RowStatus.SUCCESS,
            RowStatus.PROCESSING)
        > 0;
  }

  /**
   * @param destination destination
   * @param task task
   * @title setError
   * @description setError
   * @author BiJi'an
   * @date 2023-12-23 23:37
   */
  public boolean setError(String destination, TableMonitorTask task) {
    return dao.updateStatusByStatus(
            destination,
            task.getDb(),
            task.getTableName(),
            task.getDataId(),
            RowStatus.ERROR,
            RowStatus.PROCESSING)
        > 0;
  }

  /**
   * @param destination destination
   * @param task task
   * @title setRetry
   * @description setRetry
   * @author BiJi'an
   * @date 2023-12-24 01:35
   */
  public boolean setRetry(String destination, TableMonitorTask task) {
    return dao.setRetry(destination, task.getDb(), task.getTableName(), task.getDataId()) > 0;
  }

  /**
   * @param destination destination
   * @param rowStatus rowStatus
   * @param startDate startDate
   * @return int
   * @title batchRetry
   * @description batchRetry
   * @author BiJi'an
   * @date 2023-12-24 01:36
   */
  public int batchRetry(String destination, RowStatus rowStatus, LocalDateTime startDate) {
    return dao.batchRetry(destination, rowStatus, startDate);
  }

  /**
   * @param destination destination
   * @param maxRetry maxRetry
   * @param startDate startDate
   * @return int
   * @title batchError
   * @description batchError
   * @author BiJi'an
   * @date 2023-12-24 01:36
   */
  public int batchError(String destination, int maxRetry, LocalDateTime startDate) {
    return dao.batchError(destination, maxRetry, startDate);
  }

  public boolean isDeleted(Table table, String dataId) {
    return dao.isDeleted(table.getTableName(), table.getDelColName(), table.getPkColName(), dataId);
  }
}

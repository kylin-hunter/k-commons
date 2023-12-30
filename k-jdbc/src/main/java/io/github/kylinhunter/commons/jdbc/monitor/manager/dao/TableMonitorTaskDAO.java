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
package io.github.kylinhunter.commons.jdbc.monitor.manager.dao;

import io.github.kylinhunter.commons.jdbc.execute.SqlExecutor;
import io.github.kylinhunter.commons.jdbc.monitor.manager.dao.constant.RowStatus;
import io.github.kylinhunter.commons.jdbc.monitor.manager.dao.entity.TableMonitorTask;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author BiJi'an
 * @description
 * @date 2023-12-09 22:50
 */
public interface TableMonitorTaskDAO {

  /**
   * @param destination destination
   * @return io.github.kylinhunter.commons.jdbc.monitor.dao.entity.ScanProcessor
   * @title findById
   * @description findById
   * @author BiJi'an
   * @date 2023-12-09 00:13
   */
  TableMonitorTask findById(String destination, String database, String bizTable, String dataId);

  /**
   * @param destination destination
   * @param tableMonitorTask tableMonitorTask
   * @title save
   * @description save
   * @author BiJi'an
   * @date 2023-12-17 10:23
   */
  void save(String destination, TableMonitorTask tableMonitorTask);

  /**
   * @param destination destination
   * @param tableMonitorTask tableMonitorTask
   * @title update
   * @description update
   * @author BiJi'an
   * @date 2023-12-17 10:25
   */
  void update(String destination, TableMonitorTask tableMonitorTask);

  /**
   * @return io.github.kylinhunter.commons.jdbc.execute.SqlExecutor
   * @title getSqlExecutor
   * @description getSqlExecutor
   * @author BiJi'an
   * @date 2023-12-09 00:14
   */
  SqlExecutor getSqlExecutor();

  /**
   * @title ensureTableExists
   * @description ensureTableExists
   * @author BiJi'an
   * @date 2023-12-09 00:16
   */
  void ensureDestinationExists(String destination);

  /**
   * @param destination destination
   * @param database database
   * @param tableName tableName
   * @title clean
   * @description clean
   * @author BiJi'an
   * @date 2023-12-23 17:02
   */
  void clean(String destination, String database, String tableName);

  /**
   * @param destination destination
   * @param database database
   * @param tableName tableName
   * @title reset
   * @description reset
   * @author BiJi'an
   * @date 2023-12-23 21:38
   */
  int reset(String destination, String database, String tableName);

  /**
   * @param destination destination
   * @param database database
   * @param tableName tableName
   * @param dataId dataId
   * @title reset
   * @description reset
   * @author BiJi'an
   * @date 2023-12-23 21:44
   */
  int reset(String destination, String database, String tableName, String dataId);

  /**
   * @param destination destination
   * @param time time
   * @param maxRetry maxRetry
   * @param limit limit
   * @return io.github.kylinhunter.commons.jdbc.monitor.manager.dao.entity.TableMonitorTask
   * @title findWaitDatas
   * @description findWaitDatas
   * @author BiJi'an
   * @date 2023-12-23 22:04
   */
  List<TableMonitorTask> findWaitDatas(
      String destination, LocalDateTime time, int maxRetry, int limit);

  /**
   * @param destination destination
   * @param db db
   * @param tableName tableName
   * @param dataId dataId
   * @param newRowStatus newRowStatus
   * @param oldRowStatus oldRowStatus
   * @return int
   * @throws
   * @title updateStatusByStatus
   * @description updateStatusByStatus
   * @author BiJi'an
   * @date 2023-12-31 00:46
   */
  int updateStatusByStatus(
      String destination,
      String db,
      String tableName,
      String dataId,
      RowStatus newRowStatus,
      RowStatus oldRowStatus);

  /**
   * @param destination destination
   * @param db db
   * @param tableName tableName
   * @param dataId dataId
   * @return int
   * @title setRetry
   * @description setRetry
   * @author BiJi'an
   * @date 2023-12-31 00:43
   */
  int setRetry(String destination, String db, String tableName, String dataId);

  /**
   * @param destination destination
   * @param rowStatus rowStatus
   * @param startDate startDate
   * @title batchRetry
   * @description batchRetry
   * @author BiJi'an
   * @date 2023-12-30 22:24
   */
  int batchRetry(String destination, RowStatus rowStatus, LocalDateTime startDate);

  /**
   * @param destination destination
   * @param maxRetry maxRetry
   * @param startDate startDate
   * @title batchError
   * @description batchError
   * @author BiJi'an
   * @date 2023-12-30 22:24
   */
  int batchError(String destination, int maxRetry, LocalDateTime startDate);

  /**
   * @param tableName tableName
   * @param pkColName pkColName
   * @param dataId dataId
   * @param delColName delColName
   * @title isDeleted
   * @description isDeleted
   * @author BiJi'an
   * @date 2023-12-30 22:24
   */
  boolean isDeleted(String tableName, String delColName, String pkColName, String dataId);
}

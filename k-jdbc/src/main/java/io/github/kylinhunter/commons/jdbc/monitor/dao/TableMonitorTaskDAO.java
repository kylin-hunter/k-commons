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
package io.github.kylinhunter.commons.jdbc.monitor.dao;

import io.github.kylinhunter.commons.jdbc.execute.SqlExecutor;
import io.github.kylinhunter.commons.jdbc.monitor.dao.entity.TableMonitorTask;

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
   * @param bizTableName bizTableName
   * @title clean
   * @description clean
   * @author BiJi'an
   * @date 2023-12-23 17:02
   */
  void clean(String destination, String database, String bizTableName);
}

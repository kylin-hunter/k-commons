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
import io.github.kylinhunter.commons.jdbc.monitor.dao.TableMonitorTaskDAO;
import io.github.kylinhunter.commons.jdbc.monitor.dao.constant.MonitorStatus;
import io.github.kylinhunter.commons.jdbc.monitor.dao.constant.RowOP;
import io.github.kylinhunter.commons.jdbc.monitor.dao.entity.ScanRecord;
import io.github.kylinhunter.commons.jdbc.monitor.dao.entity.TableMonitorTask;
import io.github.kylinhunter.commons.jdbc.monitor.dao.imp.MysqlTableMonitorTaskDAO;
import io.github.kylinhunter.commons.jdbc.monitor.scan.TableScanConfig;
import javax.sql.DataSource;

/**
 * @author BiJi'an
 * @description
 * @date 2023-12-13 16:06
 */
public class TableMonitorTaskManager extends AbstractDatabaseVisitor {

  private final TableMonitorTaskDAO tableMonitorTaskDAO;

  public TableMonitorTaskManager(DataSource dataSource) {
    super(dataSource, false);
    if (this.dbType == DbType.MYSQL) {
      this.tableMonitorTaskDAO = new MysqlTableMonitorTaskDAO(dataSource);
    } else {
      throw new UnsupportedException("unsupported dbType=" + dbType);
    }
  }

  /**
   * @param destination destination
   * @param tableName tableName
   * @title clean
   * @description clean
   * @author BiJi'an
   * @date 2023-12-13 16:15
   */
  public void clean(String destination, String tableName) {
    tableMonitorTaskDAO.clean(destination, tableName);
  }

  /**
   * @title ensureTableExists
   * @description ensureTableExists
   * @author BiJi'an
   * @date 2023-12-09 00:16
   */
  public void ensureDestinationExists(String destination) {
    tableMonitorTaskDAO.ensureDestinationExists(destination);
  }

  /**
   * @param destination destination
   * @param id id
   * @param rowOP rowOP
   * @title saveOrUpdate
   * @description saveOrUpdate
   * @author BiJi'an
   * @date 2023-12-12 01:43
   */
  public void saveOrUpdate(String destination, String targetTable, String id, RowOP rowOP) {
    TableMonitorTask tableMonitorTask =
        this.tableMonitorTaskDAO.findById(destination, targetTable, id);
    if (tableMonitorTask == null) {
      tableMonitorTask = new TableMonitorTask();
      tableMonitorTask.setTableName(targetTable);
      tableMonitorTask.setDataId(id);
      tableMonitorTask.setStatus(MonitorStatus.WAIT.getCode());
      tableMonitorTask.setOp(rowOP.getCode());
      this.tableMonitorTaskDAO.save(destination, tableMonitorTask);
    } else {
      tableMonitorTask.setStatus(MonitorStatus.WAIT.getCode());
      tableMonitorTask.setOp(rowOP.getCode());
      this.tableMonitorTaskDAO.update(destination, tableMonitorTask);
    }
  }

  /**
   * @param tableScanConfig tableScanConfig
   * @param scanRecord scanRecord
   * @title saveOrUpdate
   * @description saveOrUpdate
   * @author BiJi'an
   * @date 2023-12-13 23:44
   */
  public void saveOrUpdate(TableScanConfig tableScanConfig, ScanRecord scanRecord) {
    this.saveOrUpdate(
        tableScanConfig.getDestination(),
        tableScanConfig.getTableName(),
        scanRecord.getId(),
        RowOP.UPDATE);
  }
}

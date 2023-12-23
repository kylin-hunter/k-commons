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
import io.github.kylinhunter.commons.jdbc.monitor.manager.dao.TableMonitorTaskDAO;
import io.github.kylinhunter.commons.jdbc.monitor.manager.dao.constant.MonitorStatus;
import io.github.kylinhunter.commons.jdbc.monitor.manager.dao.constant.RowOP;
import io.github.kylinhunter.commons.jdbc.monitor.manager.dao.entity.ScanRecord;
import io.github.kylinhunter.commons.jdbc.monitor.manager.dao.entity.TableMonitorTask;
import io.github.kylinhunter.commons.jdbc.monitor.manager.dao.imp.MysqlTableMonitorTaskDAO;
import io.github.kylinhunter.commons.jdbc.monitor.scan.bean.ScanTable;
import javax.sql.DataSource;

/**
 * @author BiJi'an
 * @description
 * @date 2023-12-16 16:06
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
   * @param tableName   tableName
   * @title clean
   * @description clean
   * @author BiJi'an
   * @date 2023-12-16 16:15
   */
  public void clean(String destination, String database, String tableName) {
    tableMonitorTaskDAO.clean(destination, database, tableName);
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
   * @param dataId      dataId
   * @param rowOP       rowOP
   * @title saveOrUpdate
   * @description saveOrUpdate
   * @author BiJi'an
   * @date 2023-12-12 01:43
   */
  public void saveOrUpdate(
      String destination, String database, String targetTable, String dataId, RowOP rowOP) {
    TableMonitorTask tableMonitorTask =
        this.tableMonitorTaskDAO.findById(destination, database, targetTable, dataId);
    if (tableMonitorTask == null) {
      tableMonitorTask = new TableMonitorTask();
      tableMonitorTask.setDb(database);
      tableMonitorTask.setTableName(targetTable);
      tableMonitorTask.setDataId(dataId);
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
   * @param scanTable  scanTable
   * @param scanRecord scanRecord
   * @title saveOrUpdate
   * @description saveOrUpdate
   * @author BiJi'an
   * @date 2023-12-16 23:44
   */
  public void saveOrUpdate(ScanTable scanTable, ScanRecord scanRecord) {
    this.saveOrUpdate(
        scanTable.getDestination(),
        scanTable.getDatabase(),
        scanTable.getTableName(),
        scanRecord.getId(),
        RowOP.UPDATE);
  }
}

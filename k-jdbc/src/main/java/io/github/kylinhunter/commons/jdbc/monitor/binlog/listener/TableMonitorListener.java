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
package io.github.kylinhunter.commons.jdbc.monitor.binlog.listener;

import com.github.shyiko.mysql.binlog.event.DeleteRowsEventData;
import com.github.shyiko.mysql.binlog.event.UpdateRowsEventData;
import com.github.shyiko.mysql.binlog.event.WriteRowsEventData;
import io.github.kylinhunter.commons.jdbc.meta.bean.ColumnMeta;
import io.github.kylinhunter.commons.jdbc.meta.bean.ColumnMetas;
import io.github.kylinhunter.commons.jdbc.meta.bean.TableId;
import io.github.kylinhunter.commons.jdbc.monitor.dao.TableMonitorTaskDAO;
import io.github.kylinhunter.commons.jdbc.monitor.dao.entity.TableMonitorTask;
import io.github.kylinhunter.commons.jdbc.monitor.dao.imp.MysqlTableMonitorTaskDAO;
import java.io.Serializable;
import java.util.List;
import java.util.Map.Entry;
import javax.sql.DataSource;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 * @author BiJi'an
 * @description EventListener
 * @date 2023-11-25 02:56
 */
@Slf4j
public class TableMonitorListener extends AbstractBinLogEventListener {

  private TableMonitorTaskDAO tableMonitorTaskDAO;
  @Setter private String destination = "k_table_monitor_binlog_task";
  @Setter private TableId tableId;
  @Setter private String tablePrimaryKey;

  @Override
  public void init(DataSource dataSource) {
    super.init(dataSource);
    this.tableMonitorTaskDAO = new MysqlTableMonitorTaskDAO(dataSource, false);
    this.tableMonitorTaskDAO.ensureTableExists(destination);
  }

  @Override
  protected void insertDataRecord(TableId tableId, WriteRowsEventData eventData) {
    if (this.tableId.equals(tableId)) {
      ColumnMetas columnMetas = this.columnMetas.get(tableId);
      if (columnMetas != null) {
        ColumnMeta columnMeta = columnMetas.getByName(tablePrimaryKey);

        List<Serializable[]> rows = eventData.getRows();
        for (Serializable[] row : rows) {

          if (columnMeta != null && columnMeta.getPos() < rows.get(0).length) {

            processScanRecord((String) row[columnMeta.getPos()], 1);
          }
        }
      }
    }
  }

  @Override
  protected void updateDataRecord(TableId tableId, UpdateRowsEventData eventData) {

    if (this.tableId.equals(tableId)) {
      ColumnMetas columnMetas = this.columnMetas.get(tableId);
      if (columnMetas != null) {
        ColumnMeta columnMeta = columnMetas.getByName(tablePrimaryKey);
        List<Entry<Serializable[], Serializable[]>> rows = eventData.getRows();
        for (Entry<Serializable[], Serializable[]> row : rows) {
          if (columnMeta != null && columnMeta.getPos() < row.getValue().length) {
            processScanRecord((String) row.getValue()[columnMeta.getPos()], 2);
          }
        }
      }
    }
  }

  @Override
  protected void deleteDataRecord(TableId tableId, DeleteRowsEventData eventData) {
    if (this.tableId.equals(tableId)) {
      ColumnMetas columnMetas = this.columnMetas.get(tableId);
      if (columnMetas != null) {
        ColumnMeta columnMeta = columnMetas.getByName(tablePrimaryKey);

        List<Serializable[]> rows = eventData.getRows();
        for (Serializable[] row : rows) {

          if (columnMeta != null && columnMeta.getPos() < rows.get(0).length) {

            processScanRecord((String) row[columnMeta.getPos()], 3);
          }
        }
      }
    }
  }

  /**
   * @param id id
   * @param op op
   * @title processScanRecord
   * @description processScanRecord
   * @author BiJi'an
   * @date 2023-12-12 00:23
   */
  private void processScanRecord(String id, int op) {
    TableMonitorTask tableMonitorTask =
        this.tableMonitorTaskDAO.findById(destination, tableId.getName(), id);
    if (tableMonitorTask == null) {
      tableMonitorTask = new TableMonitorTask();
      tableMonitorTask.setId(tableId.getName());
      tableMonitorTask.setDataId(id);
      tableMonitorTask.setStatus(0);
      tableMonitorTask.setOp(op);
      this.tableMonitorTaskDAO.save(destination, tableMonitorTask);
    } else {
      tableMonitorTask.setStatus(0);
      tableMonitorTask.setOp(op);
      this.tableMonitorTaskDAO.update(destination, tableMonitorTask);
    }
  }
}

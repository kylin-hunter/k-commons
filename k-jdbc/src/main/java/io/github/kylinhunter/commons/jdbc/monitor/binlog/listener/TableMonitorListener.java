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
import io.github.kylinhunter.commons.jdbc.monitor.dao.constant.MonitorStatus;
import io.github.kylinhunter.commons.jdbc.monitor.dao.constant.RowOP;
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
  @Setter private TableId targetTableId;
  @Setter private String targetTablePK;

  @Override
  public void init(DataSource dataSource) {
    super.init(dataSource);
    this.tableMonitorTaskDAO = new MysqlTableMonitorTaskDAO(dataSource, false);
    this.tableMonitorTaskDAO.ensureTableExists(destination);
  }

  @Override
  protected void insertDataRecord(TableId tableId, WriteRowsEventData eventData) {
    if (!this.targetTableId.equals(tableId)) {
      return;
    }

    ColumnMeta pkColumnMeta = getPkColumnMeta();
    if (pkColumnMeta == null) {
      return;
    }
    List<Serializable[]> rows = eventData.getRows();
    for (Serializable[] row : rows) {
      processScanRecord(row, pkColumnMeta, RowOP.INSERT);
    }
  }

  @Override
  protected void updateDataRecord(TableId tableId, UpdateRowsEventData eventData) {

    if (!this.targetTableId.equals(tableId)) {
      return;
    }

    ColumnMeta pkColumnMeta = getPkColumnMeta();
    if (pkColumnMeta == null) {
      return;
    }
    List<Entry<Serializable[], Serializable[]>> rows = eventData.getRows();
    for (Entry<Serializable[], Serializable[]> row : rows) {
      processScanRecord(row.getValue(), pkColumnMeta, RowOP.UPDATE);
    }
  }

  @Override
  protected void deleteDataRecord(TableId tableId, DeleteRowsEventData eventData) {
    if (!this.targetTableId.equals(tableId)) {
      return;
    }

    ColumnMeta pkColumnMeta = getPkColumnMeta();
    if (pkColumnMeta == null) {
      return;
    }
    List<Serializable[]> rows = eventData.getRows();
    for (Serializable[] row : rows) {
      processScanRecord(row, pkColumnMeta, RowOP.DELETE);
    }
  }

  /**
   * @return io.github.kylinhunter.commons.jdbc.meta.bean.ColumnMeta
   * @title getPkColumnMeta
   * @description getPkColumnMeta
   * @author BiJi'an
   * @date 2023-12-12 01:18
   */
  private ColumnMeta getPkColumnMeta() {
    ColumnMetas columnMetas = this.columnMetas.get(targetTableId);
    if (columnMetas != null) {
      return columnMetas.getByName(targetTablePK);
    }
    return null;
  }

  /**
   * @param row row
   * @param pkColumnMeta pkColumnMeta
   * @param rowOP rowOP
   * @title processScanRecord
   * @description processScanRecord
   * @author BiJi'an
   * @date 2023-12-12 01:42
   */
  private void processScanRecord(Serializable[] row, ColumnMeta pkColumnMeta, RowOP rowOP) {
    if (pkColumnMeta.getPos() < row.length) {
      processScanRecord(String.valueOf(row[pkColumnMeta.getPos()]), rowOP);
    }
  }

  /**
   * @param id id
   * @param rowOP rowOP
   * @title processScanRecord
   * @description processScanRecord
   * @author BiJi'an
   * @date 2023-12-12 01:43
   */
  private void processScanRecord(String id, RowOP rowOP) {
    TableMonitorTask tableMonitorTask =
        this.tableMonitorTaskDAO.findById(destination, targetTableId.getName(), id);
    if (tableMonitorTask == null) {
      tableMonitorTask = new TableMonitorTask();
      tableMonitorTask.setId(targetTableId.getName());
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
}

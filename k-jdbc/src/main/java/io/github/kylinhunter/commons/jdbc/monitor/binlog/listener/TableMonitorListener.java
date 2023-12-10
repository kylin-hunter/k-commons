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
import io.github.kylinhunter.commons.jdbc.meta.bean.TableMeta;
import io.github.kylinhunter.commons.jdbc.monitor.dao.ScanProcessorDAO;
import io.github.kylinhunter.commons.jdbc.monitor.dao.entity.ScanProcessor;
import io.github.kylinhunter.commons.jdbc.monitor.dao.imp.MysqlScanProcessorDAO;
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

  private ScanProcessorDAO scanProcessorDAO;
  @Setter
  private String destination = "k_table_monitor_binlog_task";
  @Setter
  private String tableName;

  @Override
  public void init(DataSource dataSource) {
    this.dataSource = dataSource;
    this.scanProcessorDAO = new MysqlScanProcessorDAO(dataSource, false);
    this.scanProcessorDAO.ensureTableExists(destination);
  }


  @Override
  protected void insertDataRecord(String tableName, WriteRowsEventData eventData,
      TableMeta tableMeta, List<ColumnMeta> columnMetas) {
    if (this.tableName.equals(tableName)) {
      List<Serializable[]> rows = eventData.getRows();
      for (Serializable[] row : rows) {
        processScanRecord((String) row[0], 1);
      }
    }
  }

  @Override
  protected void updateDataRecord(String tableName, UpdateRowsEventData eventData,
      TableMeta tableMeta,
      List<ColumnMeta> columnMetas) {
    if (this.tableName.equals(tableName)) {
      List<Entry<Serializable[], Serializable[]>> rows = eventData.getRows();
      for (Entry<Serializable[], Serializable[]> row : rows) {
        processScanRecord((String) row.getValue()[0], 2);
      }
    }
  }


  @Override
  protected void deleteDataRecord(String tableName, DeleteRowsEventData eventData,
      TableMeta tableMeta,
      List<ColumnMeta> columnMetas) {
    if (this.tableName.equals(tableName)) {
      List<Serializable[]> rows = eventData.getRows();
      for (Serializable[] row : rows) {
        processScanRecord((String) row[0], 3);
      }
    }
  }


  private void processScanRecord(String id, int op) {
    ScanProcessor scanProcessor = this.scanProcessorDAO.findById(destination, tableName, id);
    if (scanProcessor == null) {
      scanProcessor = new ScanProcessor();
      scanProcessor.setId(tableName);
      scanProcessor.setDataId(id);
      scanProcessor.setStatus(0);
      scanProcessor.setOp(op);
      this.scanProcessorDAO.save(destination, scanProcessor);
    } else {
      scanProcessor.setStatus(0);
      scanProcessor.setOp(op);
      this.scanProcessorDAO.update(destination, scanProcessor);
    }
  }
}
